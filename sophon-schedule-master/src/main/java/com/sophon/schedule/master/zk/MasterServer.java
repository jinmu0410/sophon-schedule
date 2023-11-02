package com.sophon.schedule.master.zk;

import com.sophon.schedule.master.zk.register.MasterSubscribeListener;
import com.sophon.schedule.register.zookeeper.ZookeeperClientAdapter;
import com.sophon.schedule.register.zookeeper.ZookeeperProperties;
import com.sophon.schedule.register.zookeeper.ZookeeperRegister;
import org.apache.curator.framework.CuratorFramework;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/2 17:03
 */
public class MasterServer {

    public static void main(String[] args) {
        ZookeeperClientAdapter zookeeperClientAdapter = buildZkConnector();

        String path = "/jinmu/test/123";

        register(path, zookeeperClientAdapter.getClient());

        try {
            Thread.sleep(2000);
            zookeeperClientAdapter.createZNode(path,"测试123456",true);

            Thread.sleep(500);
            zookeeperClientAdapter.setValue(path,"测试999999");

            Thread.sleep(500);
            zookeeperClientAdapter.deleteZNode(path);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public static void register(String path, CuratorFramework client){
        ZookeeperRegister zookeeperRegister = new ZookeeperRegister(client);
        new Thread(()->{
            boolean flag = zookeeperRegister.subscribe(path,new MasterSubscribeListener());
            try {
                if(flag){
                    System.out.println("订阅路径监听成功");
                }
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static ZookeeperClientAdapter buildZkConnector(){
        ZookeeperProperties zookeeperProperties = new ZookeeperProperties();
        zookeeperProperties.setConnectString("localhost:2181");
        zookeeperProperties.setNamespace("jinmu-master-service");

        ZookeeperClientAdapter zookeeperClientAdapter = new ZookeeperClientAdapter(zookeeperProperties);

        return zookeeperClientAdapter;
    }
}
