package org.bitbuckets.lib.abstractions;

/**
 * Represents a piece of code that needs to run in order for a {@link ISetup}'s process
 * to be valid at teleop-time
 */
public interface IExecutable {

    class LoopException extends Exception {

    }

    void run() throws LoopException;

}
