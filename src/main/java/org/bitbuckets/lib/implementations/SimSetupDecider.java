package org.bitbuckets.lib.implementations;

import org.bitbuckets.SimLevel;
import org.bitbuckets.lib.IProcessPath;
import org.bitbuckets.lib.abstractions.ISetup;

/**
 * simple junction for sim stuff
 * @param <T>
 */
public class SimSetupDecider<T> implements ISetup<T> {

    final ISetup<T> useWhenReal;
    final ISetup<T> useWhenSimulated;

    public SimSetupDecider(ISetup<T> useWhenReal, ISetup<T> useWhenSimulated) {
        this.useWhenReal = useWhenReal;
        this.useWhenSimulated = useWhenSimulated;
    }

    @Override
    public T build(IProcessPath path) {

        if (path.getSimLevel() == SimLevel.SIM_CLASSES) {
            return useWhenSimulated.build(path);
        }

        return useWhenReal.build(path);
    }
}
