package org.bitbuckets.drive.module;

import org.bitbuckets.lib.ISetup;
import org.bitbuckets.lib.UserBucketLib;
import org.bitbuckets.lib.motor.EncoderType;
import org.bitbuckets.lib.motor.stages.PreEncoderMotor;

public class SwerveModuleSetup implements ISetup<SwerveModule> {

    final int driveId;
    final int turnId;

    final double[] driveConstants;
    final double[] turnConstants;

    public SwerveModuleSetup(int driveId, int turnId, double[] driveConstants, double[] turnConstants) {
        this.driveId = driveId;
        this.turnId = turnId;
        this.driveConstants = driveConstants;
        this.turnConstants = turnConstants;
    }


    @Override
    public SwerveModule build(UserBucketLib userBucketLib) { //This assumes we've already got an ID lmao
        PreEncoderMotor drive = userBucketLib.talonFXFactory()
                .buildNewMotor(driveId,false,false,false, 80.0) //always supply side, like we in econ >:)
                .usePID(false, driveConstants)
                .withEncoder(EncoderType.INTEGRATED);

        PreEncoderMotor turnStage = userBucketLib.talonFXFactory()
                .buildNewMotor(turnId, false, false, false, 20.0)
                .usePID(true, turnConstants)
                .withEncoder(EncoderType.INTEGRATED);


        SwerveModule mod = new SwerveModule( drive.build(false), turnStage.build(false), drive.acquireEncoder(), turnStage.acquireEncoder());

        return mod;


    }
}
