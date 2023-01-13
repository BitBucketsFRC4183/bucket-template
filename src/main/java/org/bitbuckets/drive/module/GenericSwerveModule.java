package org.bitbuckets.drive.module;

import edu.wpi.first.math.geometry.Rotation2d;
import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.lib.motor.IControllerEncoder;
import org.bitbuckets.lib.motor.setpoint.IMotor;

public class GenericSwerveModule implements ISwerveModule {

    final IMotor drive;
    final IMotor turn;
    final IControllerEncoder driveEncoder;
    final IControllerEncoder turnEncoder;

    public GenericSwerveModule(IMotor drive, IMotor turn, IControllerEncoder driveEncoder, IControllerEncoder turnEncoder) {
        this.drive = drive;
        this.turn = turn;
        this.driveEncoder = driveEncoder;
        this.turnEncoder = turnEncoder;
    }

    @Override
    public void outputSensorReadings(SwerveModuleSensor mutableSensorObject) {

    }

    @Override
    public double queryCurrentAccumulatedPositionMeters() {
        return driveEncoder.getPosition();
    }

    @Override
    public Rotation2d queryCurrentRotation() {
        return Rotation2d.fromRadians(turnEncoder.getPosition() % DriveConstants.REVOLUTION);
    }

    @Override
    public void commandSetpointValues(double velocitySetpoint_metersPerSecond, double turnSetpoint_radians) {
        drive.moveAt(velocitySetpoint_metersPerSecond);
        turn.moveAt(turnSetpoint_radians); //TODO optimize
    }
}
