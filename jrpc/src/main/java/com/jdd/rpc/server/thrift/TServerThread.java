package com.jdd.rpc.server.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdd.rpc.RpcException;
import com.jdd.rpc.common.ServerNode;
import com.jdd.rpc.monitor.RpcMonitor;

/**
 * 服务线程
 * <p>
 *
 */
public class TServerThread extends Thread {

    /** LOGGER */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /** {@link TServer} */
    private final TServer server;

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
    public TServerThread(TProcessor processor, ServerNode serverNode, int maxWorkerThreads, int minWorkerThreads, RpcMonitor monitor) throws RpcException {
        TServerSocket serverTransport;
        try {
            serverTransport = new TServerSocket(serverNode.getPort());
        } catch (TTransportException e) {
            throw new RpcException(RpcException.NETWORK_EXCEPTION, e);
        }
        Factory portFactory = new Factory(true, true);
        Args args = new Args(serverTransport);
        args.processor(processor);
        args.protocolFactory(portFactory);
        args.maxWorkerThreads(maxWorkerThreads);
        args.minWorkerThreads(minWorkerThreads);
        server = new TThreadPoolServer(args);
        server.setServerEventHandler(new ThriftEventHandler(monitor, serverNode));
        setName("Jrpc-Thrift-Server");
    }

    /**
     * @param tServer
     *            {@link TServer}
     */
    public TServerThread(TServer tServer) {
        this.server = tServer;
    }

    @Override
    public void run() {
        try {
            System.out.println("Server is start!");
            LOGGER.info("Server is start!");
            server.serve();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 停止服务
     * <p>
     */
    public void stopServer() {
        server.stop();
        System.out.println("Server is stop!");
        LOGGER.info("Server is stop!");
    }

    /**
     * TServer是否启动
     * <p>
     * 
     * @return
     */
    public boolean isStarted() {
        return server.isServing();
    }
}
