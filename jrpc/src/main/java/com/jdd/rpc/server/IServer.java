package com.jdd.rpc.server;

/**
 * 服务接口
 * <p>
 *
 */
public interface IServer {

    /**
     * 服务启动（后台线程启动） <br>
     * <br>
     * <b>注意：</b><br>
     * 1.start()的实现必须是幂等的，也就是调用一次，和调用两次没有区别。 <br>
     * 2.start()的实现不必是线程安全的，调用者不应该在高并发下多次调用，通常可以随主线程启动
     * <p>
     */
    void start();

    /**
     * 服务停止 <br>
     * <br>
     * <b>注意：</b><br>
     * 1.服务在停止使用时，需要显式调用关闭 <br>
     * 2.关闭时会释放相关资源
     * <p>
     */
    void stop();

    /**
     * 判断服务是否已经启动(3次重试判断)
     * <p>
     * 
     * @return
     */
    boolean isStarted();

}
