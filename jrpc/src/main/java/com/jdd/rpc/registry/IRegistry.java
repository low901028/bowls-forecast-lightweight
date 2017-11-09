package com.jdd.rpc.registry;

import com.jdd.rpc.RpcException;
import com.jdd.rpc.loadbalance.common.DynamicHostSet;

/**
 * 注册中心
 * <p>
 *
 */
public interface IRegistry {

    /**
     * 注册<br>
     * 包括：provider和client
     * <p>
     * 
     * @param config
     *            配置信息
     * @throws RpcException
     */
    void register(String config) throws RpcException;

    /**
     * 获取所以服务
     * <p>
     * 
     * @return
     */
    DynamicHostSet findAllService();

    /**
     * 服务注销
     * <p>
     */
    void unregister();
}
