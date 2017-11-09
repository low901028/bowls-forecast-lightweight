package com.jdd.rpc.test;

import com.jdd.rpc.config.ClientConfig;
import com.jdd.rpc.config.RegistryConfig;
import com.jdd.rpc.test.gen.EchoService;
import com.jdd.rpc.test.gen.MessageProtocol;

/**
 * <p>
 *
 */
public class ClientMain {
    public static void main(String[] args) throws Exception {
        RegistryConfig registryConfig = new RegistryConfig();
        // registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setConnectstr("192.168.137.111:2181");

        String iface = EchoService.Iface.class.getName();
        // String iface = MessageProtocol.class.getName();
        ClientConfig<MessageProtocol> clientConfig = new ClientConfig<MessageProtocol>();
        clientConfig.setService("com.jdd.rpc.test$EchoService");
        clientConfig.setIface(iface);
        clientConfig.setProtocol("thrift");
        // clientConfig.setProtocol("avro");
        clientConfig.setHeartbeat(2000);
        clientConfig.setMonitor(true);
        clientConfig.setInterval(60);
        clientConfig.setRetry(0);

        final EchoService.Iface echo = (EchoService.Iface) clientConfig.createProxy(registryConfig);
        // final MessageProtocol protocol =
        // clientConfig.createProxy(registryConfig);
        for (int i = 0; i < 1; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i <= 300; i++) {
                        try {
                            System.out.println(echo.echo("world!"));
                            // System.out.println(protocol.sendMessage("world!"));
                            Thread.sleep(1000);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

    }
}
