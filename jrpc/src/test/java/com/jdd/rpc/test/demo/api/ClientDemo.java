package com.jdd.rpc.test.demo.api;

import com.jdd.rpc.config.ClientConfig;
import com.jdd.rpc.config.RegistryConfig;
import com.jdd.rpc.test.gen.EchoService.Iface;

/**
 * The demo of client by api.
 * <p>
 *
 */
public class ClientDemo {
    /**
     * @param args
     */
    public static void main(String[] args) {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("192.168.137.111:2181");
        registryConfig.setAuth("admin:admin123");

        String iface = Iface.class.getName();
        ClientConfig<Iface> clientConfig = new ClientConfig<Iface>();
        clientConfig.setService("com.jdd.rpc.test.demo$EchoService");
        clientConfig.setIface(iface);
        // clientConfig.setAddress("172.18.1.23:19090;172.18.1.24:19090");

        try {
            // 注意:代理内部已经使用连接池，所以这里只需要创建一个实例，多线程共享；特殊情况下，可以允许创建多个实例，
            // 但严禁每次调用前都创建一个实例。
            Iface echoService = clientConfig.createProxy(registryConfig);

            for (int i = 0; i < 10; i++) {
                System.out.println(echoService.echo("world!"));
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
