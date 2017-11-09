package com.jdd.rpc.test.avro;

import org.apache.avro.AvroRemoteException;

import com.jdd.rpc.test.gen.MessageProtocol;

/**
 * <p>
 *
 */
public class MessageProtocolImpl implements MessageProtocol {

    @Override
    public CharSequence sendMessage(CharSequence message) throws AvroRemoteException {
        return "hello " + message;
    }
}
