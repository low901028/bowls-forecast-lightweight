package com.jdd.rpc.test.demo.prop;

import com.jdd.rpc.main.Client;
import com.jdd.rpc.test.gen.EchoService.Iface;

/**
 * The demo for client by properties file.
 * <p>
 *
 */
public class ClientDemo {
    /** 配置文件路径，配置说明参考 {@link Client#Client(String[])} */
    private static final String CONFIG_FILE_PATH = "classpath:demo/demo-client.properties";

    /**
     * @param args
     */
    public static void main(String[] args) {
        String[] configs = new String[] { CONFIG_FILE_PATH };

        try {
            Client<Iface> client = new Client<Iface>(configs);
            // 注意:代理内部已经使用连接池，所以这里只需要创建一个实例，多线程共享；特殊情况下，可以允许创建多个实例，
            // 但严禁每次调用前都创建一个实例。
            Iface echoIface = client.createProxy();

            for (int i = 0; i < 1000; i++) {
                try {
                    System.out.println(echoIface.echo("world"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
