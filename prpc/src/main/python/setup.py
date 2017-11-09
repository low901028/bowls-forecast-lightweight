# -*- coding: utf-8 -*-
from setuptools import setup

with open('requirements.txt') as f:
    required = f.read().splitlines()

setup(
    name="prpc",
    version="1.0.2",
    author="kola.wang",
    author_email="wangmiaomiao@jiangduoduo.com",
    description="prpc-python",
    install_requires=required,
    packages=["jdd.prpc", "jdd.prpc.common", "jdd.prpc.loadbalancing_strategy", "jdd.prpc.thrift_server"]
)
