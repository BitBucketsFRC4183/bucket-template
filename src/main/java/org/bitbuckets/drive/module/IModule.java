package org.bitbuckets.drive.module;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public interface IModule {

    SwerveModuleState reportState();
    SwerveModulePosition reportPosition();

    //replaced by consumeData
    void commandSetpointValues(double velocitySetpoint_metersPerSecond, double turnSetpoint_radians);

}
