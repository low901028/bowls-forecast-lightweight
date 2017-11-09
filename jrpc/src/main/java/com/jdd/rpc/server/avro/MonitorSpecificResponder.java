package com.jdd.rpc.server.avro;

import org.apache.avro.Protocol.Message;
import org.apache.avro.ipc.specific.SpecificResponder;

import com.jdd.rpc.common.ServerNode;
import com.jdd.rpc.monitor.RpcMonitor;
import com.jdd.rpc.monitor.StatisticsInfo;

/**
 * 增加监控功能的{@link SpecificResponder} (暂时废弃)
 * <p>
 *
 */
@Deprecated
public class MonitorSpecificResponder extends SpecificResponder {

    /** {@link RpcMonitor} */
    private final RpcMonitor monitor;

    /** {@link ServerNode} */
    private final ServerNode serverNode;

    /**
     * @param iface
     * @param impl
     * @param monitor
     * @param serverNode
     */
    @SuppressWarnings("rawtypes")
    public MonitorSpecificResponder(Class iface, Object impl, RpcMonitor monitor, ServerNode serverNode) {
        super(iface, impl);

        this.monitor = monitor;
        this.serverNode = serverNode;
    }

    @Override
    public Object respond(Message message, Object request) throws Exception {
        boolean isSuccess = true;
        StatisticsInfo info = new StatisticsInfo();
        long startTime = System.currentTimeMillis();
        try {
            Object result = super.respond(message, request);
            return result;
        } catch (Exception e) {
            isSuccess = false;
            throw e;
        } finally {
            if (monitor != null) {
                long usetime = System.currentTimeMillis() - startTime;
                System.out.println(usetime);
                info.setAvgtime(usetime);
                info.setMaxtime(usetime);
                info.setMintime(usetime);
                if (isSuccess) {
                    info.setSuccess(1L);
                } else {
                    info.setFailure(1L);
                }

                monitor.collect(serverNode, info);
            }
        }
    }

}
