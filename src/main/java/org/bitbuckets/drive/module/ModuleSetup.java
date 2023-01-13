package org.bitbuckets.drive.module;

import org.bitbuckets.lib.IProcessFactory;
import org.bitbuckets.lib.Tools;
import org.bitbuckets.lib.motor.EncoderType;
import org.bitbuckets.lib.motor.stages.EncoderMotorPreBuild;
import org.littletonrobotics.junction.networktables.LoggedDashboardNumber;

public class ModuleSetup implements IProcessFactory<GenericSwerveModule> {

    final int driveId;
    final int turnId;

    final double[] driveConstants;
    final double[] turnConstants;

    public ModuleSetup(int driveId, int turnId, double[] driveConstants, double[] turnConstants) {
        this.driveId = driveId;
        this.turnId = turnId;
        this.driveConstants = driveConstants;
        this.turnConstants = turnConstants;
    }


    @Override
    public GenericSwerveModule build(Tools tools) { //This assumes we've already got an ID lmao
        EncoderMotorPreBuild drive = tools.talonFXFactory()
                .buildNewMotor(driveId,false,false,false, 80.0)
                .usePID(false, driveConstants)
                .withEncoder(EncoderType.INTEGRATED);

        EncoderMotorPreBuild turnStage = tools.talonFXFactory()
                .buildNewMotor(turnId, false, false, false, 20.0)
                .usePID(true, turnConstants)
                .withEncoder(EncoderType.INTEGRATED);


        GenericSwerveModule mod = new GenericSwerveModule( drive.build(false), turnStage.build(false), drive.acquireEncoder(), turnStage.acquireEncoder());


        return mod;


    }
}
