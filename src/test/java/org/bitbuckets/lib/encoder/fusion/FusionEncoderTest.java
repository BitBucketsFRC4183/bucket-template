package org.bitbuckets.lib.encoder.fusion;

import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.encoder.can.MockAbsoluteEncoder;
import org.bitbuckets.lib.encoder.talon.TalonRotationEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FusionEncoderTest {

    @Test
    public void testSomeMethod() {

        IRotationEncoder encoderMock = new TalonRotationEncoder(DriveConstants.ROTATION_REDUCTION, () -> 2048.0, UnsupportedOperationException::new);
        IRotationEncoder absoluteEncoderMock = new MockAbsoluteEncoder(() -> 360.0);


        FusionEncoder encoder = new FusionEncoder(encoderMock, absoluteEncoderMock);

        Assertions.assertEquals(2048, encoder.calculateAlignSU());
    }


}