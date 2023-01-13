package org.bitbuckets.lib.behavior;

//TODO tomorrow
public interface IBehaviorRoutine {

    Status processTick(int iteration);

    enum Status {
        NEEDS_PROCESSING,
        SUCCESS,
        FAILED
    }

}