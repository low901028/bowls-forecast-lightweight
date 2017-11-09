package com.jdd.rpc.test.demo.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jdd.rpc.test.gen.EchoService.Iface;

/**
 * The demo of client by xml file.
 * <p>
 *
 */
public class ClientDemo {

    /** spring配置文件 */
    private static final String SPRING_FILE_PATH = "demo/demo-client.xml";

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext(SPRING_FILE_PATH);
            Iface echoService = (Iface) context.getBean("echoService");
            for (int i = 0; i < 100; i++) {
                System.out.println(echoService.echo("world!"));
                Thread.sleep(100);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
