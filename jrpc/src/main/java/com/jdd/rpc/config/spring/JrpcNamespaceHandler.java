package com.jdd.rpc.config.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.jdd.rpc.config.RegistryConfig;

/**
 * jrpc命名空间处理类
 * <p>
 *
 */
public class JrpcNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("registry", new JrpcBeanDefinitionParser(RegistryConfig.class));
        registerBeanDefinitionParser("server", new JrpcBeanDefinitionParser(ServerBean.class));
        registerBeanDefinitionParser("client", new JrpcBeanDefinitionParser(ClientBean.class));
    }

}
