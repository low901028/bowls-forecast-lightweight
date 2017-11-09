package com.jdd.rpc.server.thrift;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TTransport;

import com.jdd.rpc.common.ServerNode;
import com.jdd.rpc.monitor.RpcMonitor;

/**
 * Thrift事件处理器
 * <p>
 *
 */
public class ThriftEventHandler implements TServerEventHandler {
    //
    // /** {@link RpcMonitor} */
    // private final RpcMonitor monitor;
    //
    // /** {@link ServerNode} */
    // private final ServerNode serverNode;

    /**
     * @param monitor
     *            {@link RpcMonitor}
     * @param serverNode
     *            {@link ServerNode}
     */
    public ThriftEventHandler(RpcMonitor monitor, ServerNode serverNode) {
        // this.monitor = monitor;
        // this.serverNode = serverNode;
    }

    @Override
    public ServerContext createContext(TProtocol arg0, TProtocol arg1) {
        return null;
    }

    @Override
    public void deleteContext(ServerContext arg0, TProtocol arg1, TProtocol arg2) {

    }

    @Override
    public void preServe() {

    }

    @Override
    public void processContext(ServerContext arg0, TTransport arg1, TTransport arg2) {
        // TODO:进行ip白名单相关的校验
    }

}
