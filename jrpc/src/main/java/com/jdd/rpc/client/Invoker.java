package com.jdd.rpc.client;

import java.lang.reflect.Method;

import com.jdd.rpc.RpcException;

/**
 * 调用者（用于实际发起请求）
 * <p>
 *
 */
public interface Invoker {
    /**
     * 调用
     * <p>
     * 
     * @param method
     * @param args
     * @return result
     * @throws RpcException
     */
    Object invoke(Method method, Object[] args) throws RpcException;
}
