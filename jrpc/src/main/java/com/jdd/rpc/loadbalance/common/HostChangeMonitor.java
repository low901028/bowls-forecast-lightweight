package com.jdd.rpc.loadbalance.common;

import com.google.common.collect.ImmutableSet;

public interface HostChangeMonitor<T> {
    void onChange(ImmutableSet<T> hostAndPorts);
}
