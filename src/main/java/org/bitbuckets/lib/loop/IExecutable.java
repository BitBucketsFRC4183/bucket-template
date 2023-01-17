package org.bitbuckets.lib.loop;

/**
 * Represents a piece of code that needs to run in order for a {@link org.bitbuckets.lib.ISetup}'s process
 * to be valid at teleop-time
 */
public interface IExecutable {

    class LoopException extends Exception {

    }

    void run() throws LoopException;

}
