package org.bitbuckets.drive.control;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.drive.module.IModule;

/**
 * Represents a real drive controller that implements control of the drivetrain using a list of SwerveModule interfaces
 */
public class DriveControl implements IDriveControl {

    final IModule[] modules;

    public DriveControl(IModule... modules) {
        this.modules = modules;
    }

    SwerveModuleState[] cachedSetpoint = DriveConstants.LOCK;

    @Override
    public SwerveModuleState[] reportSetpointStates() {
        return cachedSetpoint;
    }

    @Override
    public SwerveModuleState[] reportActualStates() {
        SwerveModuleState[] states = new SwerveModuleState[4];

        for (int i = 0; i < modules.length; i++) {
            states[i] = modules[i].reportState();
        }

        return states;
    }

    @Override
    public SwerveModulePosition[] reportActualPositions() {
        return new SwerveModulePosition[0];
    }

    @Override
    public void doDriveWithStates(SwerveModuleState[] states) {


        for (int i = 0; i < states.length; i++) {
            modules[i].commandSetpointValues(states[i].speedMetersPerSecond, states[i].angle.getRadians());
        }


        cachedSetpoint = states;
    }


}