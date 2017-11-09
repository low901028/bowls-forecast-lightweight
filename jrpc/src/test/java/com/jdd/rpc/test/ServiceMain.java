package com.jdd.rpc.test;

import java.io.IOException;

import com.jdd.rpc.RpcException;
import com.jdd.rpc.config.RegistryConfig;
import com.jdd.rpc.config.ServerConfig;

/**
 * <p>
 *
 */
public class ServiceMain {
    public static void main(String[] args) {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAuth("admin:admin123");

        // registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setConnectstr("192.168.137.111:2181");

        EchoServiceImpl echoServiceImpl = new EchoServiceImpl();
        // MessageProtocolImpl protocolImpl = new MessageProtocolImpl();

        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setPort(19091);
        // serverConfig.setProtocol("avro");
        serverConfig.setName("demo");
        serverConfig.setRef(echoServiceImpl);
        // serverConfig.setRef(protocolImpl);
        // serverConfig.setMonitor(true);
        serverConfig.setMaxWorkerThreads(100);
        serverConfig.setInterval(60);
        serverConfig.setMonitor(true);
        serverConfig.setService("com.jdd.rpc.test$EchoService");
        // serverConfig.setService("com.bfd.rpc.avro.test$EchoService");

        try {
            serverConfig.export(registryConfig);
            System.in.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
