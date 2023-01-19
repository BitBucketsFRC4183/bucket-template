package org.bitbuckets.drive.control;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.drive.module.IModule;
import org.bitbuckets.lib.abstractions.log.IDataLogger;

/**
 * Represents a real drive controller that implements control of the drivetrain using a list of SwerveModule interfaces
 */
public class DriveControl implements IDriveControl {

    final IDataLogger<DriveControlDataAutoGen> logger;
    final IModule[] modules;

    public DriveControl(IDataLogger<DriveControlDataAutoGen> logger, IModule... modules) {
        this.logger = logger;
        this.modules = modules;
    }

    SwerveModuleState[] cachedSetpoint = DriveConstants.LOCK;

    void guaranteedLoggingLoop() {
        logger.process(data -> {
            data.targetStates = reportSetpointStates();
            data.realStates = reportActualStates();
        });
    }

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
        SwerveModulePosition[] states = new SwerveModulePosition[4];

        for (int i = 0; i < modules.length; i++) {
            states[i] = modules[i].reportPosition();
        }

        return states;
    }

    @Override
    public void doDriveWithStates(SwerveModuleState[] states) {

        for (int i = 0; i < states.length; i++) {
            modules[i].commandSetpointValues(states[i].speedMetersPerSecond, states[i].angle.getRadians());
        }

        cachedSetpoint = states;
    }


}