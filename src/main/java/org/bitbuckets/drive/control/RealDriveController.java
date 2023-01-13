package org.bitbuckets.drive.control;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.drive.module.ISwerveModule;

/**
 * Represents a real drive controller that implements control of the drivetrain using a list of SwerveModule interfaces
 */
public class RealDriveController implements IDriveController {

    final ISwerveModule[] swerveModules;

    public RealDriveController(ISwerveModule... swerveModules) {
        this.swerveModules = swerveModules;
    }

    @Override
    public SwerveModulePosition[] queryCurrentStates() {

        SwerveModulePosition[] array = new SwerveModulePosition[swerveModules.length];

        for (int i = 0; i < swerveModules.length; i++) {
            array[i] = new SwerveModulePosition(swerveModules[i].queryCurrentAccumulatedPositionMeters(), swerveModules[i].queryCurrentRotation());
        }

        return array;
    }

    @Override
    public void doDriveWithStates(SwerveModuleState[] states) {

        //TODO do we need to run modulestate.optimize here?

        for (int i = 0; i < states.length; i++) {
            swerveModules[i].commandSetpointValues(states[i].speedMetersPerSecond, states[i].angle.getRadians());
        }



    }


}