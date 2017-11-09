package com.jdd.rpc.config.spring;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jdd.rpc.RpcException;
import com.jdd.rpc.common.ServerNode;
import com.jdd.rpc.config.RegistryConfig;
import com.jdd.rpc.config.ServerConfig;
import com.jdd.rpc.monitor.RpcMonitor;
import com.jdd.rpc.registry.IRegistry;
import com.jdd.rpc.registry.ZkServerRegistry;
import com.jdd.rpc.server.IServer;

/**
 * 服务提供者javabean
 * <p>
 *
 */
public class ServerBean extends ServerConfig implements ApplicationContextAware, InitializingBean {

    /** LOGGER */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /** javabean的ID */
    private String id;

    /** {@link ApplicationContext} */
    private ApplicationContext applicationContext;

    /**
     * getter method
     * 
     * @see ServerConfig#id
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * setter method
     * 
     * @see ServerConfig#id
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        export();

    }

    /**
     * 暴露服务
     * <p>
     * 
     * @throws ClassNotFoundException
     *             ,RpcException
     */
    @SuppressWarnings({ "unchecked" })
    public void export() throws ClassNotFoundException, RpcException {
        // 参数检查
        check();

        // 获取zkClient
        CuratorFramework zkClient = null;
        String auth = null;
        if (applicationContext != null) {
            Map<String, RegistryConfig> regMap = applicationContext.getBeansOfType(RegistryConfig.class);
            if (regMap != null && regMap.size() > 0) {
                for (String key : regMap.keySet()) {
                    if (regMap.get(key) != null) {
                        try {
                            zkClient = regMap.get(key).obtainZkClient();
                            auth = regMap.get(key).getAuth();
                            if (StringUtils.isEmpty(auth)) {
                                throw new RpcException(RpcException.CONFIG_EXCEPTION, "The params 'auth' cannot empty!");
                            }
                        } catch (Exception e) {
                            throw new RpcException("Registry error!", e);
                        }
                        break;
                    }
                }
            }
        }

        // 服务注册
        IRegistry registry = null;
        ServerNode serverNode = genServerNode();
        if (zkClient != null) {
            registry = new ZkServerRegistry(zkClient, getService(), serverNode.genAddress(), auth);
        }

        // 创建监控
        RpcMonitor rpcMonitor = null;
        if (isMonitor()) {
            rpcMonitor = new RpcMonitor(getInterval(), zkClient, getService(), false);
        }

        // 创建服务
        IServer server = createServer(serverNode, rpcMonitor);
        server.start();

        if (server.isStarted()) {
            try {
                // 服务注册
                registry.register(genConfigJson());
                // 添加关闭钩子
                addShutdownHook(registry, server);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                server.stop();
            }
        } else {
            server.stop();
        }
    }

}
