package org.bitbuckets.lib.encoder.talon;

import org.bitbuckets.drive.DriveConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TalonRotationEncoderTest {

    @Test
    void zeroShouldReturnZero() {

        TalonRotationEncoder rotationEncoder = new TalonRotationEncoder(DriveConstants.ROTATION_REDUCTION, () -> 0.0, () -> null);

        assertEquals(Math.toRadians(0), rotationEncoder.getMechanismPositionAccumulated_radians());


    }

    @Test
    void getMechanismPositionAccumulated_radians() {

        var steerReduction = (15.0 / 32.0) * (10.0 / 60.0);
        var steerCoefficient = 2.0 * Math.PI / 2048 * steerReduction;

        TalonRotationEncoder rotationEncoder = new TalonRotationEncoder(DriveConstants.ROTATION_REDUCTION, () -> Math.toRadians(540) / steerCoefficient, () -> null);

        assertEquals(Math.toRadians(540), rotationEncoder.getMechanismPositionAccumulated_radians());

        assertEquals(Math.toRadians(540) / steerCoefficient, 0);

    }
}