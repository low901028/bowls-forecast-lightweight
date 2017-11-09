package com.jdd.rpc.loadbalance;

import java.util.Set;

import com.jdd.rpc.loadbalance.common.ResourceExhaustedException;

/**
 * 源码参考：<a href=
 * "https://github.com/twitter/commons/tree/master/src/java/com/twitter/common/net/loadbalancing"
 * >Twitter jdd.prpc.common-net-loadbalance</a>
 * <p>
 *
 */
public interface LoadBalancer<K> extends RequestTracker<K> {

    void offerBackends(Set<K> offeredBackends);

    K nextBackend() throws ResourceExhaustedException;
}
