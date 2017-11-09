package com.jdd.rpc.test.demo;

import org.apache.thrift.TException;

import com.jdd.rpc.test.gen.EchoService.Iface;

/**
 * The implementation of {@link com.jdd.rpc.test.gen.EchoService.Iface}
 * <p>
 *
 */
public class DemoServiceImpl implements Iface {

    @Override
    public String echo(String msg) throws TException {
        return "hello " + msg;
    }

}
