package org.bitbuckets.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.drive.DriveInput;
import org.bitbuckets.drive.IDriveControl;
import org.bitbuckets.lib.IRobotContainer;

import java.util.Arrays;

/**
 * This class represents your robot's periodic behavior
 */
public class RobotContainer implements IRobotContainer {


    final DriveInput input; //TODO make this mockable (needs log playback support)
    final IDriveControl driveController;

    public RobotContainer(DriveInput input, IDriveControl driveController) {
        this.input = input;
        this.driveController = driveController;
    }

    @Override
    public void autoPeriodic() {

    }

    public void teleopPeriodic() {
        ChassisSpeeds desired = new ChassisSpeeds(input.getInputX(), input.getInputY(), input.getInputRot());
        SwerveModuleState[] states = RobotConstants.KINEMATICS.toSwerveModuleStates(desired);
        SwerveModuleState[] optimized = Arrays.stream(states).map(s -> SwerveModuleState.optimize(s, new Rotation2d())).toArray(SwerveModuleState[]::new);


        driveController.doDriveWithStates(optimized);
    }

}
