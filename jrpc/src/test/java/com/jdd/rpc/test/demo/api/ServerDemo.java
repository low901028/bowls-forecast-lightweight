package com.jdd.rpc.test.demo.api;

import com.jdd.rpc.config.RegistryConfig;
import com.jdd.rpc.config.ServerConfig;
import com.jdd.rpc.test.demo.DemoServiceImpl;

/**
 * The demo of server by api.
 * <p>
 *
 */
public class ServerDemo {
    /**
     * @param args
     */
    public static void main(String[] args) {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("192.168.137.111:2181");
        registryConfig.setAuth("admin:admin123");
        registryConfig.setTimeout(5000);

        DemoServiceImpl serviceImpl = new DemoServiceImpl();

        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setPort(19090);
        serverConfig.setRef(serviceImpl);
        serverConfig.setService("com.jdd.rpc.test.demo$EchoService");

        try {
            serverConfig.export(registryConfig); // 暴露服务
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
