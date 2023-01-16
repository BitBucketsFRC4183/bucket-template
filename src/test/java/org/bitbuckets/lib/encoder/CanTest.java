package org.bitbuckets.lib.encoder;

import edu.wpi.first.hal.HAL;
import org.bitbuckets.lib.BucketLib;
import org.bitbuckets.lib.encoder.can.CANRotationEncoder;
import org.bitbuckets.lib.encoder.can.CANRotationSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CanTest {

    @Test
    public void canInputsAtRest_shouldBeZero() {
        assert HAL.initialize(500,0);
        BucketLib lib = new BucketLib();

        CANRotationEncoder rotationEncoder = new CANRotationSetup(0,Math.toRadians(0),1).build(lib.rootProcessPath());

        //sanity check
        Assertions.assertEquals(0,rotationEncoder.getEncoderPositionAccumulated_radians());
        Assertions.assertEquals(0,rotationEncoder.getEncoderPositionBounded_radians());
        Assertions.assertEquals(0,rotationEncoder.getMechanismPositionBounded_radians());

    }

    @Test
    public void canInputsWithOffsetAtRest_shouldBeSane() {
        assert HAL.initialize(500,0);
        BucketLib lib = new BucketLib();

        CANRotationEncoder rotationEncoder = new CANRotationSetup(0,-250,1).build(lib.rootProcessPath());

        waitForCTREUpdate();

        Assertions.assertEquals(Math.toRadians(360 - 250), rotationEncoder.getEncoderPositionBounded_radians(), 0.01);
        Assertions.assertEquals(Math.toRadians(360 - 250), rotationEncoder.getMechanismPositionBounded_radians(), 0.01); //the encoder is located on the mechanism
        Assertions.assertEquals(Math.toRadians(360 - 250), rotationEncoder.getEncoderPositionAccumulated_radians(), 0.01);
    }

    public static void waitForCTREUpdate() {
        try {
            com.ctre.phoenix.unmanaged.Unmanaged.feedEnable(500);
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
