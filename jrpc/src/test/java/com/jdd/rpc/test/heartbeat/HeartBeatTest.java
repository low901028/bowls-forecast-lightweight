package com.jdd.rpc.test.heartbeat;

import com.jdd.rpc.common.ServerNode;
import com.jdd.rpc.heartbeat.HeartBeatManager;
import com.jdd.rpc.loadbalance.common.DynamicHostSet;

/**
 * <p>
 *
 */
public class HeartBeatTest {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) {
        DynamicHostSet dynamicHostSet = new DynamicHostSet();
        dynamicHostSet.addLiveInstance(new ServerNode("127.0.0.1", 80));
        dynamicHostSet.addLiveInstance(new ServerNode("127.0.0.1", 90));
        dynamicHostSet.addLiveInstance(new ServerNode("127.0.0.1", 2181));
        HeartBeatManager manager = new HeartBeatManager(dynamicHostSet, 1000, 2000, 3, 1000, null);
        manager.startHeatbeatTimer();

        while (true) {
            System.out.println("lives:" + dynamicHostSet.getLives());
            System.out.println("deads:" + dynamicHostSet.getDeads());
            // System.out.println(manager.getDeads());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
