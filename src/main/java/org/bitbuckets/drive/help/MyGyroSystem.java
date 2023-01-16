package org.bitbuckets.drive.help;

import org.bitbuckets.lib.encoder.IRotationEncoder;

public class MyGyroSystem implements GyroConversionSystem {

    final IRotationEncoder absoluteEncoder;
    final IRotationEncoder notAbsoluteEncoder;

    public MyGyroSystem(IRotationEncoder absoluteEncoder, IRotationEncoder notAbsoluteEncoder) {
        this.absoluteEncoder = absoluteEncoder;
        this.notAbsoluteEncoder = notAbsoluteEncoder;
    }

    @Override
    public double optimizedSetpoint(double inputRadians) {

        absoluteEncoder.getEncoderPositionBounded_radians();



        return 0;
    }
}
