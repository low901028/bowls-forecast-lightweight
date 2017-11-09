package com.jdd.rpc.test.demo.prop;

import com.jdd.rpc.main.Server;
import com.jdd.rpc.test.EchoServiceImpl;

/**
 * The demo for server by properties file.
 * <p>
 *
 */
public class ServerDemo {
    /** 配置文件路径，配置说明参考 {@link Server#Server(String[] , Object )} */
    private static final String CONFIG_FILE_PATH = "classpath:demo/demo-server.properties";

    /** 是否保持启动 */
    private static boolean running = true;

    /**
     * @param args
     */
    public static void main(String[] args) {
        String[] configs = new String[] { CONFIG_FILE_PATH };
        EchoServiceImpl impl = new EchoServiceImpl();

        try {
            Server server = new Server(configs, impl);
            server.start(); // 启动服务，非阻塞

            synchronized (ServerDemo.class) {
                while (running) {
                    try {
                        ServerDemo.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
