package com.jdd.rpc.loadbalance;

import java.util.Collection;
import java.util.Set;

import com.jdd.rpc.loadbalance.common.Closure;
import com.jdd.rpc.loadbalance.common.ResourceExhaustedException;

/**
 * A strategy for balancing request load among backends. Strategies should be
 * externally synchronized, and therefore do not have to worry about reentrant
 * access.
 */
public interface LoadBalancingStrategy<K> {

    /**
     * Offers a set of backends that the load balancer should choose from to
     * distribute load amongst.
     * 
     * @param offeredBackends
     *            Backends to choose from.
     * @param onBackendsChosen
     *            A callback that should be notified when the offered backends
     *            have been (re)chosen from.
     */
    public void offerBackends(Set<K> offeredBackends, Closure<Collection<K>> onBackendsChosen);

    /**
     * Gets the next backend that a request should be sent to.
     * 
     * @return Next backend to send a request.
     * @throws com.lakeside.thrift.ResourceExhaustedException
     *             If there are no available backends.
     */
    public K nextBackend() throws ResourceExhaustedException;

    /**
     * Offers information about a connection result.
     * 
     * @param key
     *            Backend key.
     * @param result
     *            Connection result.
     * @param connectTimeNanos
     *            Time spent waiting for connection to be established.
     */
    public void addConnectResult(K key, RequestTracker.RequestResult result, long connectTimeNanos);

}
