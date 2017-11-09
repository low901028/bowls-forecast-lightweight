package com.jdd.rpc.test.avro;

import java.net.InetSocketAddress;

import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.Responder;
import org.apache.avro.ipc.specific.SpecificResponder;

import com.jdd.rpc.common.ServerNode;
import com.jdd.rpc.server.avro.AvroRpcPlugin;
import com.jdd.rpc.test.gen.MessageProtocol;

/**
 * <p>
 *
 */
public class Server1 {

    public static void main(String[] args) {
        int port = 9090;

        try {
            Responder responder = new SpecificResponder(MessageProtocol.class, new MessageProtocolImpl());
            responder.addRPCPlugin(new AvroRpcPlugin(null, new ServerNode("", port)));

            NettyServer server = new NettyServer(responder, new InetSocketAddress(port));
            server.start();

            // server.join();
            // server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
