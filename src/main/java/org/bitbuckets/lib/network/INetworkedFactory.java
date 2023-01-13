package org.bitbuckets.lib.network;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface INetworkedFactory {

    <T> Supplier<T> persistent(int processId, String id, T defaultValue);
    <T> Consumer<T> logger(int processId, String id, T restingValue);

}
