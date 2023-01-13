package org.bitbuckets.drive.module;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;
import edu.wpi.first.math.geometry.Rotation2d;
import org.bitbuckets.drive.DriveConstants;

public class TalonSwerveModule implements ISwerveModule {

    final TalonFX drive;
    final TalonFX turn;
    final CANCoder absolute;

    public TalonSwerveModule(TalonFX drive, TalonFX turn, CANCoder absolute) {
        this.drive = drive;
        this.turn = turn;
        this.absolute = absolute;
    }

    @Override
    public void outputSensorReadings(SwerveModuleSensor mutableSensorObject) {

    }

    @Override
    public double queryCurrentAccumulatedPositionMeters() {
        return drive.getSelectedSensorPosition() * DriveConstants.TURN_FACTOR_FX_POS;
    }

    @Override
    public Rotation2d queryCurrentRotation() {
        return null;
    }

    int cachedReset = 0;
    @Override
    public void commandSetpointValues(double velocitySetpoint_meters, double turnSetpoint_radians) {

        //no need to do stupid conversions here! wait...

        double currentAngleRadians = turn.getSelectedSensorPosition() * DriveConstants.TURN_FACTOR_FX_POS;
        if (turn.getSelectedSensorVelocity() * DriveConstants.TURN_FACTOR_FX_VEL < Math.toRadians(0.5)) {
            if (++cachedReset >= 500) {
                cachedReset = 0;
                double absoluteAngle = absolute.getAbsolutePosition() % DriveConstants.REVOLUTION; //to radians
                turn.setSelectedSensorPosition(absoluteAngle / DriveConstants.TURN_FACTOR_FX_POS);
                currentAngleRadians = absoluteAngle;
            }
        } else {
            cachedReset = 0;
        }

        double currentAngleRadiansMod = currentAngleRadians % (2.0 * Math.PI); if (currentAngleRadiansMod < 0.0) {currentAngleRadiansMod += 2.0 * Math.PI;}
        double adjustedSetpoint = turnSetpoint_radians + currentAngleRadians - currentAngleRadiansMod;

        if (turnSetpoint_radians - currentAngleRadiansMod > Math.PI) {
            adjustedSetpoint -= 2.0 * Math.PI;
        } else if (turnSetpoint_radians - currentAngleRadiansMod < -Math.PI) {
            adjustedSetpoint += 2.0 * Math.PI;
        }

        System.out.println("Adjusted" + adjustedSetpoint);

        turn.set(TalonFXControlMode.Position, adjustedSetpoint);


    }

    public static TalonSwerveModule acquire(TalonFX drive, TalonFX turn, CANCoder encoder, Rotation2d offset) {



        TalonFXConfiguration driveConfig = new TalonFXConfiguration();

        driveConfig.voltageCompSaturation = 12.0;
        driveConfig.supplyCurrLimit.currentLimit = 80.0;
        driveConfig.supplyCurrLimit.enable = true;

        drive.configAllSettings(driveConfig);
        drive.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 250);
        drive.setSensorPhase(true);
        drive.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 250, 250);
        drive.enableVoltageCompensation(true);
        drive.setNeutralMode(NeutralMode.Brake);


        TalonFXConfiguration turnConfig = new TalonFXConfiguration();

        turnConfig.voltageCompSaturation = 12.0;
        turnConfig.supplyCurrLimit.currentLimit = 20.0;
        turnConfig.supplyCurrLimit.enable = true;

        turn.configAllSettings(turnConfig);
        turn.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 250);
        turn.setSensorPhase(true);
        turn.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 250, 250);
        turn.enableVoltageCompensation(true);
        turn.setNeutralMode(NeutralMode.Brake);

        //too tired to error check
        CANCoderConfiguration config = new CANCoderConfiguration();
        config.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        config.magnetOffsetDegrees = offset.getDegrees();
        config.sensorDirection = true;
        encoder.configAllSettings(config);
        encoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 10, 250);

        return new TalonSwerveModule(drive, turn, encoder);
    }
}