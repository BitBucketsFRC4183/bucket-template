package org.bitbuckets.drive.control;

import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.drive.module.GenericModule;

/**
 * Represents a real drive controller that implements control of the drivetrain using a list of SwerveModule interfaces
 */
public class DriveControl implements IDriveControl {

    final GenericModule[] modules;

    public DriveControl(GenericModule... modules) {
        this.modules = modules;
    }

    SwerveModuleState[] cached;

    @Override
    public void doDriveWithStates(SwerveModuleState[] states) {


        for (int i = 0; i < states.length; i++) {
            modules[i].commandSetpointValues(states[i].speedMetersPerSecond, states[i].angle.getRadians());
        }


        cached = states;
    }


}