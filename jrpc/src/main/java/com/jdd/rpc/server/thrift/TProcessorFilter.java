package com.jdd.rpc.server.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocol;

import com.jdd.rpc.common.ServerNode;
import com.jdd.rpc.monitor.RpcMonitor;
import com.jdd.rpc.monitor.StatisticsInfo;

/**
 * TProcessor过滤器(此类暂时作废)
 * <p>
 *
 */
@Deprecated
public class TProcessorFilter implements TProcessor {

    /** {@link TProcessor} */
    private final TProcessor processor;

    /** {@link RpcMonitor} */
    private final RpcMonitor monitor;

    /** {@link ServerNode} */
    private final ServerNode serverNode;

    /**
     * @param processor
     * @param monitor
     */
    public TProcessorFilter(TProcessor processor, RpcMonitor monitor, ServerNode serverNode) {
        this.processor = processor;
        this.monitor = monitor;
        this.serverNode = serverNode;
    }

    @Override
    public boolean process(TProtocol in, TProtocol out) throws TException {
        boolean isSuccess = false;
        boolean isError = false;
        StatisticsInfo info = new StatisticsInfo();
        long startTime = System.currentTimeMillis();
        try {
            isSuccess = processor.process(in, out);
        } catch (TException e) {
            isError = true;
            throw e;
        } finally {
            if (monitor != null) {
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
        return isSuccess;
    }
}
