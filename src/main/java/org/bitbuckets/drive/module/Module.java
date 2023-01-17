package org.bitbuckets.drive.module;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.lib.encoder.IEncoder;
import org.bitbuckets.lib.motor.IMotor;
import org.bitbuckets.lib.logging.Receptor;

public class Module implements IModule {

    final IMotor drive;
    final IMotor turn;
    final IEncoder driveEncoder;
    final EncoderAngleScope encoderAngleScope;

    final Receptor<ModuleData> logger;

    public Module(IMotor drive, IMotor turn, IEncoder driveEncoder, EncoderAngleScope encoderAngleScope, Receptor<ModuleData> logger) {
        this.drive = drive;
        this.turn = turn;
        this.driveEncoder = driveEncoder;
        this.encoderAngleScope = encoderAngleScope;
        this.logger = logger;
    }

    @Override
    public SwerveModuleState reportState() {
        double velocity_metersPerSecond = driveEncoder.getVelocityMechanism_metersPerSecond();
        double rotation_radians = encoderAngleScope.relative.getMechanismPositionBounded_radians();

        return new SwerveModuleState(velocity_metersPerSecond, Rotation2d.fromRadians(rotation_radians));
    }

    @Override
    public SwerveModulePosition reportPosition() {
        double position_meters = driveEncoder.getPositionMechanism_meters();
        double rotation_radians = encoderAngleScope.relative.getMechanismPositionBounded_radians();

        return new SwerveModulePosition(position_meters, Rotation2d.fromRadians(rotation_radians));
    }

    @Override
    public void commandSetpointValues(double velocitySetpoint_metersPerSecond, double turnSetpoint_radians) {


        double optimizedMechanismRads = encoderAngleScope.optimizeSetpointWithMechanismRads_encoderRads(turnSetpoint_radians);
        double optimizedMechanismSU = optimizedMechanismRads / Math.PI / 2.0 * 2048.0; //TODO test if this is safe

        drive.moveAt(velocitySetpoint_metersPerSecond);
        turn.moveAt(optimizedMechanismSU);

        //deal with all this new data!
        logger.process(data -> {
            data.rotationSetpoint_radians = turnSetpoint_radians;
            data.velocitySetpoint_metersPerSecond = velocitySetpoint_metersPerSecond;
            data.mechanismSetpoint_radians = optimizedMechanismRads;
            data.mechanismSetpoint_SU = optimizedMechanismSU;
        });
    }


}
