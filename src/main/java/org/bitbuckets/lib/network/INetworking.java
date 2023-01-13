package org.bitbuckets.lib.network;

public interface INetworking {

    /**
     * THIS WILL ALWAYS THROW AN UNCHECKED EXCEPTION
     * @param process
     * @param error
     */
    void flagError(int process, String error); //This will always throw an
    int flagTimingStart(int process, String taskName);
    void flagTimingStop(int timingID);

}
