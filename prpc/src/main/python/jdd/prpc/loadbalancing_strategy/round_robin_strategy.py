# -*- coding: utf-8 -*-

import itertools
import random

from jdd.prpc.loadbalancing_strategy import LoadBalanceStrategyBase


class RoundRobinStrategy(LoadBalanceStrategyBase):
    def __init__(self):
        pass

    def offer_backends(self, backend):
        self._backend = backend[:]
        random.shuffle(self._backend)
        self._iterator = itertools.cycle(self._backend)

    def get_backend(self):
        if len(self._backend) == 0:
            raise Exception("no backends")
        return self._iterator.next()
