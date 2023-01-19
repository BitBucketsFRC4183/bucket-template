package org.bitbuckets.lib.implementations.log;

import org.bitbuckets.lib.abstractions.core.ILogDriver;
import org.bitbuckets.lib.abstractions.log.IDataLogger;
import org.bitbuckets.lib.logging.DiffableData;
import org.littletonrobotics.junction.Logger;

import java.util.function.Consumer;

public class DataLogger<T extends DiffableData<T>> implements IDataLogger<T> {

    final int parentId;
    final ILogDriver driver;

    T cachedData;

    public DataLogger(int parentId, ILogDriver driver, T cachedData) {
        this.parentId = parentId;
        this.driver = driver;
        this.cachedData = cachedData;
    }

    @Override
    public void process(Consumer<T> dataProcessor) {
        System.out.println("A");

        Logger.getInstance().recordOutput("testingvalue",20.0);

        cachedData.startDiff();

        System.out.println(cachedData);
        dataProcessor.accept(cachedData);
        cachedData.completeDiff(parentId, driver);
    }
}
