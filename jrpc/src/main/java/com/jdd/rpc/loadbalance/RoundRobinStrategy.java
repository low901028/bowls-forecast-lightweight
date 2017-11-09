package com.jdd.rpc.loadbalance;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.jdd.rpc.loadbalance.common.ResourceExhaustedException;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 * A load balancer that distributes load by randomizing the list of available
 * backends, and then rotating through them evenly.
 */
public class RoundRobinStrategy<S> extends StaticLoadBalancingStrategy<S> {
    //private volatile Iterator<S> iterator = Iterators.emptyIterator();
    private volatile Iterator<S> iterator = Collections.emptyIterator();

    @Override
    protected Collection<S> onBackendsOffered(Set<S> targets) {
        List<S> newTargets = Lists.newArrayList(targets);
        Collections.shuffle(newTargets);
        iterator = Iterators.cycle(newTargets);
        return newTargets;
    }

    @Override
    public S nextBackend() throws ResourceExhaustedException {
        if (!iterator.hasNext()) {
            throw new ResourceExhaustedException("No backends available!");
        }
        return iterator.next();
    }

}
