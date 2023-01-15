package org.bitbuckets.lib.encoder.fusion;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.lib.IHandle;
import org.bitbuckets.lib.ISetup;
import org.bitbuckets.lib.encoder.IEncoder;
import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.encoder.can.CANRotationEncoder;
import org.bitbuckets.lib.encoder.talon.TalonRotationEncoder;
import org.bitbuckets.lib.network.Loggable;

@Deprecated
public class FusionRotationSetup implements ISetup<IRotationEncoder> {

    final ISetup<IRotationEncoder> talonEncoder;
    final ISetup<IRotationEncoder> canEncoder;

    public FusionRotationSetup(ISetup<IRotationEncoder> talonEncoder, ISetup<IRotationEncoder> canEncoder) {
        this.talonEncoder = talonEncoder;
        this.canEncoder = canEncoder;
    }

    @Override
    public FusionRotationEncoder build(IHandle handle) {
        IRotationEncoder tal = talonEncoder.build(handle.child("talon"));
        IRotationEncoder absolute = canEncoder.build(handle.child("can"));

        double actualPosition_sensorUnits = absolute.getEncoderPosition_radians() / (2 * Math.PI) * 2048.0 / DriveConstants.ROTATION_REDUCTION;
        //convert to drive encoder sensor unit position

        TalonFX raw = tal.rawAccess(TalonFX.class);
        if (ErrorCode.OK != raw.setSelectedSensorPosition(actualPosition_sensorUnits)) {
            throw new IllegalStateException();
        }

        Loggable<Double> actualPosNow = handle.logFactory().doubleLogger("actualPosNow_SU");

        handle.loopFactory().registerLoop(new Runnable() {

            int counter = 0;

            @Override
            public void run() {

                if (++counter >= 500) {
                    counter = 0;
                    System.out.println("go crazy go stupid");
                    //set the current sensorpoint
                    double actualPosNow_sensorUnits = absolute.getEncoderPosition_radians(); // (2 * Math.PI) * 2048.0 / DriveConstants.ROTATION_REDUCTION;
                    actualPosNow.log(actualPosNow_sensorUnits);
                    raw.setSelectedSensorPosition(actualPosNow_sensorUnits);
                }

            }
        });

        return new FusionRotationEncoder(tal, absolute);
    }
}
