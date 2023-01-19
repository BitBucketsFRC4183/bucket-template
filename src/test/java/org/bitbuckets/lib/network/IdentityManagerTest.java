package org.bitbuckets.lib.network;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class IdentityManagerTest {

    @Test
    public void shouldHaveRoot() {
        IIdentityManager manager = new IdentityManager(); //why was this mocked lmao

        Assertions.assertEquals("root/", manager.fullPath(0));
    }

    @Test
    public void shouldDoParents() {
        IdentityManager identityManager = new IdentityManager();

        int id = identityManager.childProcess(0, "hello");
        Assertions.assertEquals("root/hello/", identityManager.fullPath(id));
    }


    //TODO DONT LET people register loggables with the same namespace as an actual process
    @Test
    public void shouldWorkWithSetup() {

    }

}
