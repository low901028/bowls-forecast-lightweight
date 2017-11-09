package com.jdd.rpc.server.avro;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.Responder;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdd.rpc.RpcException;
import com.jdd.rpc.common.ServerNode;
import com.jdd.rpc.monitor.RpcMonitor;
import com.jdd.rpc.server.IServer;

/**
 * Avro服务
 * <p>
 *
 */
public class AvroServer implements IServer {

    /** LOGGER */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /** 服务线程 */
    private final NettyServer nettyServer;

    /** 是否已经启动 */
    private boolean isStarted = false;

    /**
     * @param responder
     *            {@link Responder}
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
    public AvroServer(Responder responder, ServerNode serverNode, int maxWorkerThreads, int minWorkerThreads, RpcMonitor monitor) throws RpcException {
        ChannelFactory channelFactory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        responder.addRPCPlugin(new AvroRpcPlugin(monitor, serverNode));
        this.nettyServer = new NettyServer(responder, new InetSocketAddress(serverNode.getPort()), channelFactory);
    }

    @Override
    public void start() {
        if (!isStarted) {
            nettyServer.start();// 非阻塞
            isStarted = true;
            System.out.println("Server is start!");
            LOGGER.info("Server is start!");
        }
    }

    @Override
    public void stop() {
        if (isStarted) {
            nettyServer.close();
            isStarted = false;
            System.out.println("Server is closed!");
            LOGGER.info("Server is closed!");
        }
    }

    @Override
    public boolean isStarted() {
        return isStarted;
    }

}
