package org.bitbuckets.drive.module;

import com.revrobotics.*;
import edu.wpi.first.math.geometry.Rotation2d;
import org.bitbuckets.drive.DriveConstants;

/**
 * Dirty implementation of a spark swerve module, needs testing
 *
 * TODO don't use this
 */
@Deprecated
class SparkSwerveModule implements ISwerveModule {

    final CANSparkMax drive;
    final SparkMaxPIDController turnPID;
    final SparkMaxAnalogSensor absoluteEncoder;
    final RelativeEncoder driveEncoder;
    final RelativeEncoder turnEncoder;
    final Rotation2d offset;

    SparkSwerveModule(CANSparkMax drive, SparkMaxPIDController turnPID, SparkMaxAnalogSensor absoluteEncoder, RelativeEncoder turnEncoder, Rotation2d offset) {
        this.drive = drive;
        this.turnPID = turnPID;
        this.absoluteEncoder = absoluteEncoder;
        this.turnEncoder = turnEncoder;
        this.offset = offset;
        this.driveEncoder = drive.getEncoder();
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
        double angle =  turnEncoder.getPosition() % DriveConstants.REVOLUTION;
        if (angle < 0.0) {angle += 2.0 * Math.PI;}

        return Rotation2d.fromRadians(angle);
    }


    double wouldSetpointAngleRadiansCache = 0.0;

    //TODO implement caching of calls to the spark max
    @Override
    public void commandSetpointValues(double velocitySetpoint_metersPerSecond, double turnSetpoint_radians) { //[0, 2pi]

        double currentAngleRadians = turnEncoder.getPosition();
        double currentAngleRadiansMod = currentAngleRadians % DriveConstants.REVOLUTION; if (currentAngleRadiansMod < 0.0) {currentAngleRadiansMod += DriveConstants.REVOLUTION;} //safety code to make sure it doesn't become negative so we dont add.. a negative
        double woundSetpointAngleRadiansMod = turnSetpoint_radians + currentAngleRadians - currentAngleRadiansMod; //hall encoder's built up sensor units + our setpoint

        //deal with angle wraparound
        if (turnSetpoint_radians - currentAngleRadiansMod > Math.PI) {
            woundSetpointAngleRadiansMod -= DriveConstants.REVOLUTION;
        } else if (turnSetpoint_radians - currentAngleRadiansMod < -Math.PI) {
            woundSetpointAngleRadiansMod += DriveConstants.REVOLUTION;
        }

        wouldSetpointAngleRadiansCache = woundSetpointAngleRadiansMod;
        System.out.println(turnSetpoint_radians);

        turnPID.setReference(woundSetpointAngleRadiansMod, CANSparkMax.ControlType.kPosition);

        //drive.getPIDController().setReference(velocitySetpoint_metersPerSecond);
        drive.setVoltage(DriveConstants.FF.calculate(velocitySetpoint_metersPerSecond)); //TODO replace this with velocity pid?


    }

    //WARNING WARNING WARNING          SHITCODE ALERT           WARNING WARNING WARNING

    /**
     * Creates and initialzies a spark swerve module
     * @param drive drive numbers
     * @param turn turn
     * @param offset ass
     * @return aaaaaa
     */
    public static SparkSwerveModule acquire(CANSparkMax drive, CANSparkMax turn, Rotation2d offset) {

        drive.enableVoltageCompensation(12);
        drive.setSmartCurrentLimit(80);
        drive.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus0, 100); //don't need to report status faults and applied output every half a tick
        drive.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, 20); //should already be 20
        drive.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, 20); //should already be 20
        RelativeEncoder driveEncoder = drive.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
        driveEncoder.setPositionConversionFactor(DriveConstants.DRIVE_FACTOR_POS);
        driveEncoder.setVelocityConversionFactor(DriveConstants.DRIVE_FACTOR_VEL);

        SparkMaxAnalogSensor absoluteEncoder = turn.getAnalog(SparkMaxAnalogSensor.Mode.kAbsolute); //has an offset
        absoluteEncoder.setPositionConversionFactor(DriveConstants.TURN_FACTOR_ABS_POS); //needs to scale from volts to radians, so divide cur. voltage by 3.3 (max) to get ratio then multiply by 2pi for radians
        double absoluteAngleUncompensatedRadians = absoluteEncoder.getPosition();
        double absoluteAngleCompensatedRadians = Rotation2d.fromRadians(absoluteAngleUncompensatedRadians).minus(offset).getRadians();
        double absoluteAngleDomainFilteredRadians = absoluteAngleCompensatedRadians % DriveConstants.REVOLUTION;
        //by now this represents the absolute position of our turn

        turn.enableVoltageCompensation(12);
        turn.setSmartCurrentLimit(20);
        turn.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus0, 100); //don't need to report status faults and applied output every half a tick
        turn.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, 20); //should already be 20
        turn.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, 20); //should already be 20
        RelativeEncoder turnEncoder = turn.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);

        turnEncoder.setPositionConversionFactor(DriveConstants.TURN_FACTOR_HALL_POS);
        turnEncoder.setPosition(absoluteAngleDomainFilteredRadians); //now that you, turnEncoder, are in radians, here is our current position, in radians!

        return new SparkSwerveModule(drive, turn.getPIDController(), absoluteEncoder, turnEncoder, offset);
    }


}