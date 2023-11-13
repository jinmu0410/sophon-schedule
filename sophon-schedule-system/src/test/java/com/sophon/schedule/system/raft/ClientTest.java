package com.sophon.schedule.system.raft;

import com.alipay.sofa.jraft.JRaftUtils;
import com.alipay.sofa.jraft.RouteTable;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.CliOptions;
import com.alipay.sofa.jraft.rpc.CliClientService;
import com.alipay.sofa.jraft.rpc.impl.cli.CliClientServiceImpl;

import java.util.concurrent.TimeoutException;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/7 10:23
 */
public class ClientTest {

    public static void main(String[] args) {

        test1();
    }

    public static void test1(){
        CliClientService cliClientService = new CliClientServiceImpl();
        // 初始化 RPC 服务
        //CliClientService cliClientService = new BoltCliClientService();
        cliClientService.init(new CliOptions());

        // 获取路由表
        RouteTable rt = RouteTable.getInstance();
        // raft group 集群节点配置
        Configuration conf = JRaftUtils.getConfiguration("127.0.0.1:8181,127.0.0.1:8182,127.0.0.1:8180");
        // 更新路由表配置
        rt.updateConfiguration("test-elaction", conf);
        // 刷新 leader 信息，超时 10 秒，返回成功或者失败
        boolean success = false;
        try {

            success = rt.refreshLeader(cliClientService, "test-elaction", 10000).isOk();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }

        if (success) {
            // 获取集群 leader 节点，未知则为 null
            PeerId leader = rt.selectLeader("test-elaction");
            System.out.println(leader.toString());
        }
    }

    public static void test2(){

    }
}
