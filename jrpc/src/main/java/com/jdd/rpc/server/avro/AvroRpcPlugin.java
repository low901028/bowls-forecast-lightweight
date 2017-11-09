package com.jdd.rpc.server.avro;

import org.apache.avro.ipc.RPCContext;
import org.apache.avro.ipc.RPCPlugin;

import com.jdd.rpc.common.ServerNode;
import com.jdd.rpc.monitor.RpcMonitor;

/**
 * Avro的rpc服务插件
 * <p>
 *
 */
public class AvroRpcPlugin extends RPCPlugin {

    /** {@link RpcMonitor} */
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
    public AvroRpcPlugin(RpcMonitor monitor, ServerNode serverNode) {
        // this.monitor = monitor;
        // this.serverNode = serverNode;
    }

    @Override
    public void serverSendResponse(RPCContext context) {
        // if (monitor != null) {
        // StatisticsInfo info = new StatisticsInfo();
        // info.setSuccess(1L);
        // monitor.collect(serverNode, info);
        // }
    }
}
