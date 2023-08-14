package com.sophon.schedule.master.zk;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/8/9 14:29
 */
public class MasterServer {


    public static void main(String[] args) throws Exception {
        start();

        ZkCuratorHelper zkCuratorHelper = new ZkCuratorHelper("localhost:2181","test");

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    zkCuratorHelper.addWatchWithCuratorCache("/jinmu/7");
//                    Thread.sleep(50000);
//                    System.out.println("关闭了");
//
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        };
//        Callable callable = new Callable() {
//            @Override
//            public Object call() throws Exception {
//                zkCuratorHelper.addWatchWithCuratorCache("/jinmu/7");
//                Thread.sleep(50000);
//                return "结束了";
//            }
//        };
//        FutureTask futureTask = new FutureTask(callable);
//
//        Thread thread = new Thread(futureTask, "test");
//        thread.setDaemon(true);
//        thread.start();
//
//
//        zkCuratorHelper.createZNode("/jinmu/7","2023-08-09 12:00:00",false);
//
//        //zkCuratorHelper.setValue("/jinmu/6","哈哈哈");
//        zkCuratorHelper.deleteZNode("/jinmu/7");
//
//        zkCuratorHelper.closeZkCuratorConnection();
//
//
//        System.out.println("结果 = " + futureTask.get());


        testLock(zkCuratorHelper,"/jinmu/lock");
    }

    public static void start() throws Exception{

    }

    public static void testLock(ZkCuratorHelper zkCuratorHelper,String path){
        ThreadLocal<Map<String, InterProcessMutex>> threadLocalLockMap = new ThreadLocal<>();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                CuratorFramework curatorFramework = zkCuratorHelper.getCuratorFramework();
                InterProcessMutex lock = new InterProcessMutex(curatorFramework, path);
                try {
                    lock.acquire();
                    System.out.println(Thread.currentThread().getName() + "获取到锁");
//                    Thread.sleep(5000);

                    if (null == threadLocalLockMap.get()) {
                        threadLocalLockMap.set(new HashMap<>(3));
                    }
                    threadLocalLockMap.get().put(path, lock);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        lock.release();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }
}
