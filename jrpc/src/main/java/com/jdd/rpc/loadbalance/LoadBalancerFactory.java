package com.jdd.rpc.loadbalance;

import org.apache.commons.lang.StringUtils;

import com.jdd.rpc.heartbeat.HeartBeatManager;
import com.jdd.rpc.loadbalance.common.DynamicSet;

/**
 * 负载均衡器简单工厂
 * <p>
 *
 */
public class LoadBalancerFactory {

    /**
     * private constructor
     */
    private LoadBalancerFactory() {
    }

    /**
     * 创建负载均衡器
     * <p>
     * 
     * @param backends
     * @param strategyName
     * @return {@link LoadBalancer}
     */
    public static <K, T> LoadBalancer<K> createLoadBalancer(DynamicSet<K> backends, String strategyName, HeartBeatManager<T> heartBeatManager) {
        LoadBalancingStrategy<K> loadBalancingStrategy = createLoadBalancingStrategy(strategyName, heartBeatManager);

        return LoadBalancerImpl.create(loadBalancingStrategy, backends);
    }

    /**
     * 创建负载均衡策略
     * <p>
     * 
     * @param strategyName
     * @return {@link LoadBalancingStrategy}
     */
    private static <K, T> LoadBalancingStrategy<K> createLoadBalancingStrategy(String strategyName, HeartBeatManager<T> heartBeatManager) {
        LoadBalancingStrategy<K> strategy = null;
        if (StringUtils.isEmpty(strategyName) || "round".equals(strategyName)) {
            strategy = new RoundRobinStrategy<K>();
        } else {
            strategy = new RandomStrategy<K>();
        }

        return strategy;
    }

}
