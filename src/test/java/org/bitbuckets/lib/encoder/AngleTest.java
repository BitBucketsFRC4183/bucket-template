package org.bitbuckets.lib.encoder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AngleTest {

    @Test
    public void testSanity() {
        Assertions.assertEquals(Math.toRadians(30), Angle.wrap(Math.toRadians(30)), 0.0001);
        Assertions.assertEquals(Math.toRadians(30),Angle.wrap(Math.toRadians(390)),0.0001);
        Assertions.assertEquals(Math.toRadians(330),Angle.wrap(Math.toRadians(-390)),0.0001);
    }

}
