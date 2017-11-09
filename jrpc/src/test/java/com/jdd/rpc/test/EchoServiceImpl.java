package com.jdd.rpc.test;

import org.apache.thrift.TException;

import com.jdd.rpc.test.gen.EchoService;

/**
 * <p>
 *
 */
public class EchoServiceImpl implements EchoService.Iface {

    @Override
    public String echo(String msg) throws TException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "hello " + msg;
    }

}
