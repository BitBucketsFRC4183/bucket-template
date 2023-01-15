package org.bitbuckets.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.drive.DriveInput;
import org.bitbuckets.drive.control.IDriveControl;
import org.bitbuckets.lib.IRobotContainer;
import org.bitbuckets.lib.network.Loggable;

import java.util.Arrays;

/**
 * This class represents your robot's periodic behavior
 */
public class RobotContainer implements IRobotContainer {

    final DriveInput input;
    final IDriveControl driveController;

    public RobotContainer(DriveInput input, IDriveControl driveController) {
        this.input = input;
        this.driveController = driveController;
    }

    @Override
    public void autoPeriodic() {

    }

    public void teleopPeriodic() {
        ChassisSpeeds desired = new ChassisSpeeds(input.getInputX() * 4, input.getInputY() * 4, input.getInputRot() * DriveConstants.MAX_ANG_VELOCITY);

        SwerveModuleState[] states = RobotConstants.KINEMATICS.toSwerveModuleStates(desired);
        SwerveDriveKinematics.desaturateWheelSpeeds(states, DriveConstants.MAX_DRIVE_VELOCITY);


        driveController.doDriveWithStates(states);
    }

}
