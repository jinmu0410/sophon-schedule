package com.sophon.schedule.system.raft;

import com.sophon.schedule.raft.jraft.election.ElectionBootstrap;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/8 10:20
 */
public class server2 {

    public static void main(String[] args) {
        String[] aa = {"/Users/jinmu/Downloads/Jraft/dataPath/server2","test-elaction","127.0.0.1:8181","127.0.0.1:8180,127.0.0.1:8182,127.0.0.1:8181"};

        ElectionBootstrap.main(aa);
    }
}
