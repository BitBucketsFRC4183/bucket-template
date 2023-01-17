package org.bitbuckets.lib.logging;

import org.bitbuckets.lib.Incubating;

import java.util.function.Consumer;

@Incubating
public class LogReceptor<T extends DiffableData<T>> implements Receptor<T> {

    final int parentId;
    final ILogDriver driver;

    T cachedData;

    public LogReceptor(int parentId, ILogDriver driver, T cachedData) {
        this.parentId = parentId;
        this.driver = driver;
        this.cachedData = cachedData;
    }

    @Override
    public void process(Consumer<T> dataProcessor) {
        cachedData.startDiff();
        dataProcessor.accept(cachedData);
        cachedData.completeDiff(parentId, driver);
    }
}
