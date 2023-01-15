package org.bitbuckets.lib.motor;

import edu.wpi.first.hal.HAL;
import org.junit.jupiter.api.Test;

public class MotorUtilTest {

    @Test
    public void motorUtilsShouldWork() {
        assert HAL.initialize(500, 0);

    }

}
