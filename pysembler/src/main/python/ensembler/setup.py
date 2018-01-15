#!/usr/bin/env python
# -*- coding: utf-8 -*-

from setuptools import setup

requirements = [
    # TODO: put package requirements here
]

test_requirements = [
    # TODO: put package test requirements here
]

setup(
    name='pysembler',
    version='0.1.0',
    description="python中的机器学习模型的自动集成",
    author="kolin",
    #author_email='',
    #url='',
    packages=[
        'ensembler',
    ],
    package_dir={'ensembler':
                     'ensembler'},
    include_package_data=True,
    install_requires=requirements,
    #license="MIT",
    zip_safe=False,
    keywords='pysembler',
    classifiers=[
        'Programming Language :: Python :: 3.6',
    ],
    test_suite='tests',
    tests_require=test_requirements
)