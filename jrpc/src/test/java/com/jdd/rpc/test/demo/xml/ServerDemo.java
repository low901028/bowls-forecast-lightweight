package com.jdd.rpc.test.demo.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The demo of server by xml file.
 * <p>
 *
 */
public class ServerDemo {

    /** spring配置文件 */
    private static final String SPRING_FILE_PATH = "demo/demo-server.xml";

    /** 是否保持启动 */
    private static boolean running = true;

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            new ClassPathXmlApplicationContext(SPRING_FILE_PATH);

            synchronized (ServerDemo.class) {
                while (running) {
                    try {
                        ServerDemo.class.wait();
                    } catch (Throwable e) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
