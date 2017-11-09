package com.jdd.rpc.server.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdd.rpc.RpcException;
import com.jdd.rpc.common.ServerNode;
import com.jdd.rpc.monitor.RpcMonitor;
import com.jdd.rpc.server.IServer;

/**
 * Thrift服务
 * <p>
 *
 */
public class ThriftServer implements IServer {

    /** LOGGER */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /** 服务线程 */
    private final TServerThread serverThread;

    /** 是否已经启动 */
    private boolean isStarted = false;

    /**
     * @param server
     *            {@link TServer}
     */
    public ThriftServer(TServer server) {
        this.serverThread = new TServerThread(server);
    }

    /**
     * @param processor
     *            {@link TProcessor}
     * @param serverNode
     *            {@link ServerNode}
     * @param maxWorkerThreads
     *            最大工作线程数
     * @param minWorkerThreads
     *            最小工作线程数
     * @param monitor
     *            {@link RpcMonitor}
     * @throws RpcException
     */
    public ThriftServer(TProcessor processor, ServerNode serverNode, int maxWorkerThreads, int minWorkerThreads, RpcMonitor monitor) throws RpcException {
        this.serverThread = new TServerThread(processor, serverNode, maxWorkerThreads, minWorkerThreads, monitor);
    }

    @Override
    public void start() {
        if (!isStarted) {
            serverThread.start();
            isStarted = serverThread.isStarted();
        }
    }

    @Override
    public void stop() {
        if (isStarted) {
            serverThread.stopServer();
            isStarted = false;
        }
    }

    @Override
    public boolean isStarted() {
        int i = 0;
        while (!isStarted && i < 3) {
            isStarted = serverThread.isStarted();
            try {
                Thread.sleep(100 * i++);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return isStarted;
    }

}
