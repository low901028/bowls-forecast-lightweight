package com.jdd.rpc.test.avro;

import java.net.InetSocketAddress;

import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import com.jdd.rpc.test.gen.MessageProtocol;

/**
 * <p>
 *
 */
public class Client1 {

    private String host = null;

    private int port = 0;

    private int count = 0;

    public Client1(String host, int port, int count) {
        this.host = host;
        this.port = port;
        this.count = count;
    }

    public long sendMessage() throws Exception {
        NettyTransceiver client = new NettyTransceiver(new InetSocketAddress(host, port));
        final MessageProtocol proxy = SpecificRequestor.getClient(MessageProtocol.class, client);

        long start = System.currentTimeMillis();
        for (int i = 1; i <= count; i++) {
            proxy.sendMessage("world");
            if (i % 10000 == 0) {
                System.out.println("Tps:" + (int) (1000 / ((System.currentTimeMillis() - start) / (double) i)));
            }
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + " ms");

        return end - start;
    }

    public long run() {
        long res = 0;
        try {
            res = sendMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        new Client1("127.0.0.1", 9090, 1000000).run();
    }
}
