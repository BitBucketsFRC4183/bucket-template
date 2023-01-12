package org.bitbuckets.shared;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

public interface RobotConstants {

    double WIDTH = 0.6096 / 2; //width of bot ?
    double BASE = 0.7712 / 2; //base of bot ?
    double WHEEL_DIAMETER_METERS = 0.10033;
    double WHEEL_CIRCUMFERENCE_METERS = WHEEL_DIAMETER_METERS * Math.PI;

    Translation2d[] KINEMATICS_TRANSLATIONS = new Translation2d[] {
            new Translation2d(WIDTH, BASE),
            new Translation2d(WIDTH, -BASE),
            new Translation2d(-WIDTH, BASE),
            new Translation2d(-WIDTH, -BASE)
    };

    SwerveDriveKinematics KINEMATICS = new SwerveDriveKinematics(KINEMATICS_TRANSLATIONS);


}
