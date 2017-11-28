#!/usr/bin/env python
# -*- coding: utf-8 -*-
# coding=utf-8

class NotEnoughData(Exception):

    def __init__(self, *args, **kwargs):
        Exception.__init__(self, *args, **kwargs)


class UnknownFeature(Exception):

    def __init__(self, *args, **kwargs):
        Exception.__init__(self, *args, **kwargs)
