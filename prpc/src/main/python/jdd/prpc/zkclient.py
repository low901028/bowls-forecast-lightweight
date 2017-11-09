# -*- coding: utf-8 -*-

import threading

from kazoo.client import KazooClient
from kazoo.retry import KazooRetry

from jdd.prpc.common import utils
from jdd.prpc import settings


class prpcZKClientManager(KazooClient):
    __client_dict = {}
    __lock = threading.RLock()

    @classmethod
    def make(cls, hosts, config, tag):
        with cls.__lock:
            key = "%s_%s" % (hosts, tag)
            if hosts in cls.__client_dict:
                return cls.__client_dict.get(key)
            else:
                client = cls(hosts, config)
                client.start()
                cls.__client_dict[key] = client
                return client

    def __init__(self, hosts, config):
        self._section_name = utils.get_module(__name__)
        self._max_delay = config.getint(self._section_name, "max_retry_delay",
                                        default=settings.DEFAULT_ZK_RETRY_MAX_DELAY)

        self._timeout = config.getint(self._section_name, "time_out", default=settings.DEFAULT_ZK_CONNECTION_TIMEOUT)
        connection_retry = KazooRetry(max_tries=-1, max_delay=self._max_delay)
        super(prpcZKClientManager, self).__init__(hosts=hosts, timeout=self._timeout,
                                                   connection_retry=connection_retry)
