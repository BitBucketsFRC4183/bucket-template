package org.bitbuckets.lib.loop;

public interface ILoopDriver {


    int registerRunnable(int parentId, IExecutable exe, int delay_ms);

    int registerLoopSimulation(int parentId, IExecutable loop, int period_ms);
    int registerLoopTeleop(int parentId, IExecutable loop, int period_ms);
    int registerLoopPeriodic(int parentId, IExecutable loop, int period_ms);

}
