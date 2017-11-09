# -*- coding: utf-8 -*-

from abc import abstractmethod


class LoadBalanceStrategyBase(object):
    """loadbalance strategy base class"""

    @abstractmethod
    def offer_backends(self, backend):
        pass

    @abstractmethod
    def get_backend(self):
        pass
