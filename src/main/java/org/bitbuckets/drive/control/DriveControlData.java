package org.bitbuckets.drive.control;

import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.lib.logging.Loggable;

@Loggable
public class DriveControlData {

    SwerveModuleState[] targetStates;
    SwerveModuleState[] realStates;

}
