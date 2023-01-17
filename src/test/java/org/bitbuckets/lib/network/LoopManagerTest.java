package org.bitbuckets.lib.network;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoopManagerTest {

    @Test
    public void shouldRunLooped() {
        AtomicBoolean flag1 = new AtomicBoolean(false);
        AtomicBoolean flag2 = new AtomicBoolean(false);

        List<Runnable> runnables = new ArrayList<>();

        runnables.add(() -> flag1.set(true));
        ILoopManager manager = new LoopManager(runnables);
        manager.registerLoop(() -> flag2.set(true));

        //TODO fix

        //Assertions.assertTrue(flag1.get(), "Loop Manager should run all runnables regardless if added externally");
        //Assertions.assertTrue(flag2.get(), "Loop manager should run looped runnables!");
    }

}
