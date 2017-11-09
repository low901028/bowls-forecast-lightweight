package com.jdd.rpc.config;

import com.jdd.rpc.RpcException;

/**
 * 配置有效性检查
 * <p>
 *
 */
public interface IConfigCheck {
    /**
     * 检查配置<br>
     * 配置非法时，抛出异常{@link RpcException}
     * <p>
     * 
     * @throws RpcException
     */
    void check() throws RpcException;
}
