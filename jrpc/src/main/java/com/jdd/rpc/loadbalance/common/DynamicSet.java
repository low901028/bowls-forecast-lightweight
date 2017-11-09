package com.jdd.rpc.loadbalance.common;

public interface DynamicSet<K> {

    void monitor(HostChangeMonitor<K> monitor);
}
