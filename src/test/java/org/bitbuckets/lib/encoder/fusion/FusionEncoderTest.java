package org.bitbuckets.lib.encoder.fusion;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import edu.wpi.first.hal.HAL;
import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.drive.module.GenericModule;
import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.encoder.can.MockAbsoluteEncoder;
import org.bitbuckets.lib.encoder.talon.MockEncoder;
import org.bitbuckets.lib.motor.talon.TalonMotor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FusionEncoderTest {

    @Test
    public void testSomeMethod() {

        IRotationEncoder encoderMock = new MockEncoder(DriveConstants.ROTATION_REDUCTION, () -> 2048.0, UnsupportedOperationException::new);
        IRotationEncoder absoluteEncoderMock = new MockAbsoluteEncoder(() -> 360.0);


        FusionEncoder encoder = new FusionEncoder(encoderMock, absoluteEncoderMock);

        Assertions.assertEquals(2048, encoder.calculateAlignSU());
    }


}