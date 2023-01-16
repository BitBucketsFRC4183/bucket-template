package org.bitbuckets.lib.encoder;

import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.lib.encoder.talon.TalonRotationEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class SanityTest {


    @Test
    public void shouldGiveMeCorrectAngles() {
        Assertions.assertEquals(1 * Math.PI, Angle.wrap(3 * Math.PI));
        Assertions.assertEquals(Math.PI, Angle.wrap(-3 * Math.PI));
        Assertions.assertEquals(Math.toRadians(359),Angle.wrap(Math.toRadians(-1)));
    }

    @RepeatedTest(10)
    @Test
    public void shouldBeSane() {
        TalonRotationEncoder talonRotationEncoder = new TalonRotationEncoder(DriveConstants.ROTATION_REDUCTION, () -> 2048.0, () ->  { return null; });

        Assertions.assertEquals(2048.0, talonRotationEncoder.getPositionRaw());
        Assertions.assertEquals(0, talonRotationEncoder.getEncoderPositionBounded_radians());
        //Assertions.assertEquals(Math.PI * 2.0, mockEncoder.getEncoderPositionBounded_radians());
    }

}