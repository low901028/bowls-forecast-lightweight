package com.jdd.rpc.pool;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdd.rpc.RpcException;
import com.jdd.rpc.common.ServerNode;

/**
 * AvroClient链接池工厂(非单例，可重载，建议使用时单例)
 * <p>
 *
 */
public class AvroClientPoolFactory<T> extends BaseKeyedPoolableObjectFactory<ServerNode, T> {

    /** LOGGER */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /** 超时时间 */
    private final int timeout;

    /** 通信的接口 */
    private final Class<?> iface;

    /** {@link ConcurrentMap}< {@link ServerNode} , {@link T}> */
    private final ConcurrentMap<ServerNode, T> transceiverMap = new ConcurrentHashMap<ServerNode, T>();

    /**
     * @param timeout
     * @param iface
     */
    public AvroClientPoolFactory(int timeout, Class<?> iface) {
        this.timeout = timeout;
        this.iface = iface;
    }

    /**
     * 生成对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public T makeObject(ServerNode key) throws Exception {
        // 生成client对象
        if (key != null) {
            T t = null;
            if (transceiverMap.containsKey(key)) {
                t = transceiverMap.get(key);
            } else {
                NettyTransceiver nettyTransceiver = new NettyTransceiver(new InetSocketAddress(key.getIp(), key.getPort()), (long) timeout);
                t = (T) SpecificRequestor.getClient(iface, nettyTransceiver);
            }
            return t;
        }
        LOGGER.error("Not find a vilid server!");
        throw new RpcException("Not find a vilid server!");
    }

}
