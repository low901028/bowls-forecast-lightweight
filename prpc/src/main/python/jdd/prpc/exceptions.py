# -*- coding: utf-8 -*-

class RpcException(Exception):
    """rpc exception"""

    def __init__(self, msg):
        self.message = msg
