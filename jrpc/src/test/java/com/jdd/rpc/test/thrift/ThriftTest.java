package com.jdd.rpc.test.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import com.jdd.rpc.RpcException;
import com.jdd.rpc.server.thrift.TServerThread;
import com.jdd.rpc.test.EchoServiceImpl;
import com.jdd.rpc.test.gen.EchoService;
import com.jdd.rpc.test.gen.EchoService.Iface;

/**
 * <p>
 *
 */
public class ThriftTest {
    public static void main(String[] args) throws InterruptedException {
        TServerSocket serverTransport;
        try {
            serverTransport = new TServerSocket(19091);
        } catch (TTransportException e) {
            throw new RpcException(RpcException.NETWORK_EXCEPTION, e);
        }
        Factory portFactory = new Factory(true, true);
        Args arg = new Args(serverTransport);
        Iface echoService = new EchoServiceImpl();
        TProcessor processor = new EchoService.Processor<Iface>(echoService);
        arg.processor(processor);
        arg.protocolFactory(portFactory);
        arg.maxWorkerThreads(100); // 实际中需要配置
        arg.minWorkerThreads(10);
        TServer server = new TThreadPoolServer(arg);
        new TServerThread(server).start();
        while (true) {
            System.out.println(server.isServing());
            Thread.sleep(1000);

        }
    }
}
