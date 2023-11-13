package com.sophon.schedule.raft.jraft.election;

import com.alipay.sofa.jraft.entity.PeerId;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/7 14:52
 */
public class ElectionBootstrap {

    public static void main(final String[] args) {
        if (args.length < 4) {
            System.out
                    .println("Usage : java com.alipay.sofa.jraft.example.election.ElectionBootstrap {dataPath} {groupId} {serverId} {initConf}");
            System.out
                    .println("Example: java com.alipay.sofa.jraft.example.election.ElectionBootstrap /tmp/server1 election_test 127.0.0.1:8081 127.0.0.1:8081,127.0.0.1:8082,127.0.0.1:8083");
            System.exit(1);
        }
        final String dataPath = args[0];
        final String groupId = args[1];
        final String serverIdStr = args[2];
        final String initialConfStr = args[3];

        final ElectionNodeOptions electionOpts = new ElectionNodeOptions();
        electionOpts.setDataPath(dataPath);
        electionOpts.setGroupId(groupId);
        electionOpts.setServerAddress(serverIdStr);
        electionOpts.setInitialServerAddressList(initialConfStr);

        final ElectionNode node = new ElectionNode();
        node.addLeaderStateListener(new LeaderStateListener() {

            @Override
            public void onLeaderStart(long leaderTerm) {
                PeerId serverId = node.getNode().getLeaderId();
                String ip = serverId.getIp();
                int port = serverId.getPort();
                System.out.println("[ElectionBootstrap] Leader's ip is: " + ip + ", port: " + port);
                System.out.println("[ElectionBootstrap] Leader start on term: " + leaderTerm);
            }

            @Override
            public void onLeaderStop(long leaderTerm) {
                System.out.println("[ElectionBootstrap] Leader stop on term: " + leaderTerm);
            }
        });
        node.init(electionOpts);
    }
}
