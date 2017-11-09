# -*- coding: utf-8 -*-

import os
import gevent
import gevent.monkey

__all__ = ['patch_all',
           'patch_thrift',
           'patch_loadbalancer',
           'patch_gevent']

saved = {}

def is_module_patched(modname):
    """Check if a module has been replaced with a cooperative version."""
    return modname in saved


def patch_thrift():
    import gevent.socket
    from thrift.transport import TSocket
    import importlib
    os.environ['GEVENT_RESOLVER'] = 'ares'
    importlib.reload(gevent.hub)
    # trans thrift socket to gevent socket
    TSocket.socket = gevent.socket


def patch_item(module, attr, newitem):
    NONE = object()
    olditem = getattr(module, attr, NONE)
    if olditem is not NONE:
        saved.setdefault(module.__name__, {}).setdefault(attr, olditem)
    setattr(module, attr, newitem)


def patch_loadbalancer():
    """Replace :func: spawn with :func:`gevent.spawn`."""
    from gevent import spawn
    from jdd.prpc.loadbalancer import LoadBalancer
    patch_item(LoadBalancer, 'spawn', spawn)
    gevent.monkey.patch_time()


def patch_gevent():
    from jdd.prpc import connection_pool
    connection_pool.ASYNC_TAG = True
    from jdd.prpc import dynamic_host_set
    dynamic_host_set.ASYNC_TAG = True


def patch_all():
    patch_thrift()
    patch_loadbalancer()
    patch_gevent()
