package com.jdd.rpc.heartbeat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdd.rpc.common.ServerNode;
import com.jdd.rpc.loadbalance.common.DynamicHostSet;

/**
 * 心跳检测任务线程
 * <p>
 *
 */
public class HeartBeatTask<T> implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatTask.class);

    /** {@link DynamicHostSet} */
    private final DynamicHostSet dynamicHostSet;

    /** heartbeat频率 */
    private final int heartbeat;

    /** 超时时间 */
    private final int heartbeatTimeout;

    /** 重试次数 */
    private final int times;

    /** 连接池 */
    private final GenericKeyedObjectPool<ServerNode, T> pool;

    /**
     * @param dynamicHostSet
     * @param times
     * @param heartbeat
     * @param heartbeatTimeout
     * @param pool
     */
    HeartBeatTask(DynamicHostSet dynamicHostSet, int times, int heartbeat, int heartbeatTimeout, GenericKeyedObjectPool<ServerNode, T> pool) {
        this.dynamicHostSet = dynamicHostSet;
        this.times = times;
        this.heartbeat = heartbeat;
        this.heartbeatTimeout = heartbeatTimeout;
        this.pool = pool;
    }

    @Override
    public void run() {
        for (ServerNode serverNode : dynamicHostSet.getAll()) {
            try {
                boolean isValid = false;
                for (int i = 0; i < times; i++) {
                    isValid = checkSocket(serverNode);
                    if (isValid) {
                        break;
                    }
                    Thread.sleep(heartbeat);
                }

                if (!isValid) {
                    // 如果不在deads set中，则加入deads中并从lives中删除
                    if (!dynamicHostSet.getDeads().contains(serverNode)) {
                        dynamicHostSet.addDeadInstance(serverNode);
                    }
                    if (pool != null) {
                        pool.clear(serverNode); // 清空连接池中的连接
                    }
                } else {
                    // 如果在deads set中，则删除并加入lives中
                    if (dynamicHostSet.getDeads().contains(serverNode)) {
                        dynamicHostSet.addLiveInstance(serverNode);
                    }
                }

            } catch (Exception t) {
                LOGGER.warn("Exception when heartbeat to remote " + serverNode.genAddress(), t);
            }
        }

    }

    /**
     * 检查socket是否正常
     * <p>
     * 
     * @param serverNode
     */
    private boolean checkSocket(ServerNode serverNode) {
        boolean isValid = false;
        Socket socket = null;
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(serverNode.getIp(), serverNode.getPort());
            socket = new Socket();
            socket.connect(socketAddress, heartbeatTimeout);
            isValid = true;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Heartbeat to remote " + serverNode.genAddress() + " success!");
            }
        } catch (IOException e) {
            LOGGER.warn("Heartbeat to remote " + serverNode.genAddress() + " failure!", e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    LOGGER.warn(e.getMessage(), e);
                }
            }
        }
        return isValid;
    }

}
