package com.sophon.schedule.raft.jraft.election;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/7 14:52
 */
public interface LeaderStateListener {

    /**
     * Called when current node becomes leader
     */
    void onLeaderStart(final long leaderTerm);

    /**
     * Called when current node loses leadership.
     */
    void onLeaderStop(final long leaderTerm);
}
