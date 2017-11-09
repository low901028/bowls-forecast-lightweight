package com.jdd.rpc.test.gen;

@SuppressWarnings("all")
/** This is a message. */
@org.apache.avro.specific.AvroGenerated
public interface MessageProtocol {
  public static final org.apache.avro.Protocol PROTOCOL = org.apache.avro.Protocol.parse("{\"protocol\":\"MessageProtocol\",\"namespace\":\"com.jdd.rpc.test.gen\",\"doc\":\"This is a message.\",\"name\":\"Message\",\"types\":[],\"messages\":{\"sendMessage\":{\"doc\":\"test\",\"request\":[{\"name\":\"message\",\"type\":\"string\"}],\"response\":\"string\"}}}");
  /** test */
  CharSequence sendMessage(CharSequence message) throws org.apache.avro.AvroRemoteException;

  @SuppressWarnings("all")
  /** This is a message. */
  public interface Callback extends MessageProtocol {
    public static final org.apache.avro.Protocol PROTOCOL = com.jdd.rpc.test.gen.MessageProtocol.PROTOCOL;
    /** test */
    void sendMessage(CharSequence message, org.apache.avro.ipc.Callback<CharSequence> callback) throws java.io.IOException;
  }
}