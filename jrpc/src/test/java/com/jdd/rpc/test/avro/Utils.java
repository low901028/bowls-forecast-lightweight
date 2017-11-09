package com.jdd.rpc.test.avro;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.avro.Protocol;

/**
 * <p>
 *
 */
public class Utils {
    public static Protocol getProtocol() {
        Protocol protocol = null;
        try {
            URL url = Utils.class.getClassLoader().getResource("message.avpr");
            protocol = Protocol.parse(new File(url.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return protocol;
    }
}
