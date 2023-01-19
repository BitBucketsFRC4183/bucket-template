package org.bitbuckets.lib.encoder;

import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.lib.abstractions.ILowLevelEncoder;
import org.bitbuckets.lib.implementations.MotorController;
import org.bitbuckets.lib.vendor.ctre.TalonConstants;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MotorControllerTest {

    double steerReduction = (15.0 / 32.0) * (10.0 / 60.0);
    double steerCoefficient = 2.0 * Math.PI / 2048 * steerReduction;

    @Test
    void zeroShouldReturnZero() {
        ILowLevelEncoder encoder = Mockito.mock();
        Mockito.when(encoder.getRawPosition_baseUnits()).thenReturn(0.0);
        MotorController motorController = new MotorController(
                Mockito.mock(),
                encoder,
                Mockito.mock(),
                TalonConstants.TALON_UNITS_TO_ROTATIONS,
                DriveConstants.TURN_REDUCTION,
                1,
                TalonConstants.TALON_TIME_TO_SECONDS
        );

        assertEquals(Math.toRadians(0), motorController.getMechanismPositionAccumulated_radians());


    }

    @Test
    void getMechanismPositionAccumulated_radians() {
        ILowLevelEncoder encoder = Mockito.mock();
        Mockito.when(encoder.getRawPosition_baseUnits()).thenReturn(Math.toRadians(540) / steerCoefficient);
        MotorController motorController = new MotorController(
                Mockito.mock(),
                encoder,
                Mockito.mock(),
                TalonConstants.TALON_UNITS_TO_ROTATIONS,
                DriveConstants.TURN_REDUCTION,
                steerReduction,
                TalonConstants.TALON_TIME_TO_SECONDS
        );

        assertEquals(Math.toRadians(540), motorController.getMechanismPositionAccumulated_radians());
    }

    @Test
    void getMechanismPositionBounded_radians() {
        ILowLevelEncoder encoder = Mockito.mock();
        Mockito.when(encoder.getRawPosition_baseUnits()).thenReturn(Math.toRadians(540) / steerCoefficient);
        MotorController motorController = new MotorController(
                Mockito.mock(),
                encoder,
                Mockito.mock(),
                TalonConstants.TALON_UNITS_TO_ROTATIONS,
                DriveConstants.TURN_REDUCTION,
                steerReduction,
                TalonConstants.TALON_TIME_TO_SECONDS
        );

        assertEquals(Math.toRadians(180), motorController.getMechanismPositionBounded_radians());
    }

    @Test
    void getMechanismPositionAccumulated_shouldWorkWithRatio() {
        ILowLevelEncoder encoder = Mockito.mock();
        Mockito.when(encoder.getRawPosition_baseUnits()).thenReturn(2048.0);
        MotorController motorController = new MotorController(
                Mockito.mock(),
                encoder,
                Mockito.mock(),
                TalonConstants.TALON_UNITS_TO_ROTATIONS,
                1,
                0.5,
                TalonConstants.TALON_TIME_TO_SECONDS
        );


        assertEquals(Math.PI, motorController.getMechanismPositionAccumulated_radians());
    }
}