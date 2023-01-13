package org.bitbuckets.drive.module;

import edu.wpi.first.math.geometry.Rotation2d;
import org.littletonrobotics.junction.AutoLog;

/**
 * Represents a single swerve module
 */
public interface ISwerveModule {


    @AutoLog
    class SensorData {
        double somePieceofData;
    }

    void generateSensorData(SensorData data); //run now
    void consumeSensorData(SensorData data); //run later

    /**
     * Queries the swerve module implementation
     * @return the amount of meters accumulated by the relative encoder on the drive motor
     */
    double queryCurrentAccumulatedPositionMeters();
    /**
     * Queries the swerve module implementation
     * @return the current heading in radians, bounded [0-2pi]
     */
    Rotation2d queryCurrentRotation();
    /**
     * Commands the swerve module to obtain the specified velocity and heading
     * @param velocitySetpoint_metersPerSecond
     * @param turnSetpoint_radians
     */
    void commandSetpointValues(double velocitySetpoint_metersPerSecond, double turnSetpoint_radians);


}