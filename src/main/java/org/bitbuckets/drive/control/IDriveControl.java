package org.bitbuckets.drive.control;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

/**
 * Represents a high level controller of the drivetrain
 */
public interface IDriveControl {


    /**
     * @return the desired velocities and angles that the robot wants the modules to be at
     */
    SwerveModuleState[] reportSetpointStates();

    /**
     * @return the actual velocities and angles that the robot modules are at
     */
    SwerveModuleState[] reportActualStates();

    /**
     * @return the actual accumulated encoder positions and angles that the robot modules are at
     */
    SwerveModulePosition[] reportActualPositions();

    /**
     * Commands every swerve module on the robot to obtain the swerve module state described
     * @param states states, indexed in the same order that the swerve modules are indexed in
     */
    void doDriveWithStates(SwerveModuleState[] states);

}

