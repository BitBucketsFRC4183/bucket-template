package org.bitbuckets.lib.encoder.fusion;

import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.encoder.can.MockAbsoluteEncoder;
import org.bitbuckets.lib.encoder.talon.TalonRotationEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.function.DoubleSupplier;

class FusionEncoderTest {

    @ParameterizedTest
    @CsvSource({"" +
            "0, 0, 0",
            "180, 180, 180",
            "361, 1, 361"
    })
    public void testCalculateAlignSU(double talonEncoderDegrees, double absoluteEncoderDegrees, double expectedDegrees) {

        var steerReduction = (15.0 / 32.0) * (10.0 / 60.0);
        var steerCoefficient = 2.0 * Math.PI / 2048 * steerReduction;

        IRotationEncoder talonRotationEncoder = new TalonRotationEncoder(DriveConstants.ROTATION_REDUCTION, () -> Math.toRadians(talonEncoderDegrees) / steerCoefficient, UnsupportedOperationException::new);
        IRotationEncoder absoluteEncoderMock = new MockAbsoluteEncoder(() -> absoluteEncoderDegrees);

        FusionEncoder encoder = new FusionEncoder(talonRotationEncoder, absoluteEncoderMock);

        Assertions.assertEquals(Math.toRadians(360 + 10), encoder.calculateAlignSU());
        Assertions.assertEquals(expectedDegrees, Math.toDegrees(encoder.calculateAlignSU() * steerCoefficient));
    }


}