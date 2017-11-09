# -*- coding: utf-8 -*-

import random

from jdd.prpc.loadbalancing_strategy import LoadBalanceStrategyBase


class RandomStrategy(LoadBalanceStrategyBase):
    def __init__(self):
        pass

    def offer_backends(self, backend):
        self._backend = backend[:]

    def get_backend(self):
        if len(self._backend) == 0:
            raise Exception("no backends")
        return random.choice(self._backend)
