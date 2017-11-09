package com.jdd.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.jdd.rpc.common.ServerNode;
import com.jdd.rpc.monitor.RpcMonitor;
import com.jdd.rpc.monitor.StatisticsInfo;

/**
 * 服务实现的动态代理
 * <p>
 *
 */
public class DynamicServiceHandler implements InvocationHandler {

    /** 实际处理实例 */
    private Object target;

    /** {@link RpcMonitor} */
    private RpcMonitor monitor;

    /** {@link ServerNode} */
    private ServerNode serverNode;

    /**
     * 动态代理绑定实例
     * <p>
     * 
     * @param classLoader
     * @param serviceClass
     * @param target
     * @param rpcMonitor
     * @param serverNode
     * @return 服务处理类代理
     * @throws ClassNotFoundException
     */
    public Object bind(ClassLoader classLoader, Class<?> serviceClass, Object target, RpcMonitor rpcMonitor, ServerNode serverNode) throws ClassNotFoundException {
        this.target = target;
        this.monitor = rpcMonitor;
        this.serverNode = serverNode;
        return Proxy.newProxyInstance(classLoader, new Class[] { serviceClass }, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean isError = false;
        StatisticsInfo info = new StatisticsInfo();
        long startTime = System.currentTimeMillis();

        try {
            Object result = method.invoke(target, args);
            return result;
        } catch (Exception e) {
            isError = true;
            throw e;
        } finally {
            if (monitor != null) { // 统计信息
                long usetime = System.currentTimeMillis() - startTime;
                info.setAvgtime(usetime);
                info.setMaxtime(usetime);
                info.setMintime(usetime);
                if (!isError) {
                    info.setSuccess(1L);
                } else {
                    info.setFailure(1L);
                }
                monitor.collect(serverNode, info);
            }
        }
    }
}
