package com.jdd.rpc.test.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;

import com.jdd.rpc.test.gen.EchoService.Client;

/**
 * <p>
 *
 */
public class ThriftMain {
    public static void main(String[] args) {
        TSocket transport = new TSocket("127.0.0.1", 19091);
        TProtocol protocol = new TBinaryProtocol(transport);
        Client client = new Client(protocol);

        try {
            transport.open();
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 100000; i++) {
                client.echo("hello world!");
            }
            System.out.println(System.currentTimeMillis() - startTime);
        } catch (TException e) {
            System.err.println(e.getMessage());
        }
    }
}
