package org.bitbuckets.lib.encoder;

import org.bitbuckets.lib.implementations.encoder.Angle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AngleTest {

    @Test
    public void wrap_shouldWorkWithConvDegrees() {
        Assertions.assertEquals(Math.toRadians(30), Angle.wrap(Math.toRadians(30)), 0.0001);
        Assertions.assertEquals(Math.toRadians(30),Angle.wrap(Math.toRadians(390)),0.0001);
        Assertions.assertEquals(Math.toRadians(330),Angle.wrap(Math.toRadians(-390)),0.0001);
    }

    @Test
    public void wrap_shouldWorkWithPureRadians() {
        Assertions.assertEquals(1 * Math.PI, Angle.wrap(3 * Math.PI));
        Assertions.assertEquals(Math.PI, Angle.wrap(-3 * Math.PI));
        Assertions.assertEquals(Math.toRadians(359),Angle.wrap(Math.toRadians(-1)));
    }


}
