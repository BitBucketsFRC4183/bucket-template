package org.bitbuckets.drive.control;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

/**
 * Represents a high level controller of the drivetrain
 */
public interface IDriveControl {


    /**
     * Commands every swerve module on the robot to obtain the swerve module state described
     * @param states states, indexed in the same order that the swerve modules are indexed in
     */
    void doDriveWithStates(SwerveModuleState[] states);

}

