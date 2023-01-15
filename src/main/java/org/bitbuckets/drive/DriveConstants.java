package org.bitbuckets.drive;


import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.robot.RobotConstants;

public interface DriveConstants {

    //TODO divide by 2
    double SENSOR_UNITS_PER_REVOLUTION = 2048.0;

    double ROTATION_REDUCTION = (15.0 / 32.0) * (10.0 / 60.0);

    double MAX_DRIVE_VELOCITY = 6380.0;
    double SLOW_DRIVE_VELOCITY = MAX_DRIVE_VELOCITY * 0.75;
    double MAX_ANG_VELOCITY = Math.hypot(RobotConstants.WIDTH / 2.0, RobotConstants.BASE / 2.0);

    //TODO get rid of this shit
    SimpleMotorFeedforward FF = new SimpleMotorFeedforward(0.65292, 2.3053, 0.37626); //converts velocity to voltage

    SwerveModuleState[] LOCK = new SwerveModuleState[] {
            new SwerveModuleState(0, Rotation2d.fromDegrees(45)),
            new SwerveModuleState(0, Rotation2d.fromDegrees(-45)),
            new SwerveModuleState(0, Rotation2d.fromDegrees(-45)),
            new SwerveModuleState(0, Rotation2d.fromDegrees(45))
    };



}