package org.bitbuckets;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.drive.DriveInput;
import org.bitbuckets.drive.control.DriveControl;
import org.bitbuckets.robot.RobotConstants;

/**
 * subsystemize!
 */
public class DriveSubsystem {

    final DriveInput input;
    final DriveControl control;

    public DriveSubsystem(DriveInput input, DriveControl control) {
        this.input = input;
        this.control = control;
    }

    public void teleopPeriodic() {
        ChassisSpeeds desired = new ChassisSpeeds(input.getInputX() * 4, input.getInputY() * 4, input.getInputRot() * DriveConstants.MAX_ANG_VELOCITY);

        SwerveModuleState[] states = RobotConstants.KINEMATICS.toSwerveModuleStates(desired);
        SwerveDriveKinematics.desaturateWheelSpeeds(states, DriveConstants.MAX_DRIVE_VELOCITY);

    }

}
