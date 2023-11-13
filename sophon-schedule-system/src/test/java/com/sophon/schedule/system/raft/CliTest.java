package com.sophon.schedule.system.raft;

import com.alipay.sofa.jraft.CliService;
import com.alipay.sofa.jraft.JRaftUtils;
import com.alipay.sofa.jraft.RaftServiceFactory;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.CliOptions;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/7 10:24
 */
public class CliTest {

    public static void main(String[] args) {

        addServer();
    }

    public static void addServer(){
        // 使用CliService
        CliService cliService = RaftServiceFactory.createAndInitCliService(new CliOptions());
        Configuration conf = JRaftUtils.getConfiguration("127.0.0.1:8181,127.0.0.1:8182,127.0.0.1:8180");
        Status status = cliService.addPeer("test-elaction", conf, new PeerId("127.0.0.1", 8183));
        if(status.isOk()){
            System.out.println("添加节点成功");
        }
    }

    public static void removeServer(CliService cliService){
        // 使用CliService
        Configuration conf = JRaftUtils.getConfiguration("127.0.0.1:8181,127.0.0.1:8182,127.0.0.1:8180");
        Status status = cliService.removePeer("jraft_group", conf, new PeerId("127.0.0.1", 8184));
        if(status.isOk()){
            System.out.println("移除节点成功");
        }
    }
}
