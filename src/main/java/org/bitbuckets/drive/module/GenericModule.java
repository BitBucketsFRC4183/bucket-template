package org.bitbuckets.drive.module;

import org.bitbuckets.lib.encoder.Angle;
import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.motor.IMotor;
import org.bitbuckets.lib.network.Loggable;

public class GenericModule {

    final IMotor drive;
    final IMotor turn;
    final IRotationEncoder wheelAxisEncoder;
    final Loggable<Double> filteredSetpoint;
    final Loggable<Double> preFilteredSetpoint;

    public GenericModule(IMotor drive, IMotor turn, IRotationEncoder wheelAxisEncoder, Loggable<Double> filteredSetpoint, Loggable<Double> preFilteredSetpoint) {
        this.drive = drive;
        this.turn = turn;
        this.wheelAxisEncoder = wheelAxisEncoder;
        this.filteredSetpoint = filteredSetpoint;
        this.preFilteredSetpoint = preFilteredSetpoint;
    }

    /**
     * Commands the swerve module to obtain the specified velocity and heading
     * @param velocitySetpoint_metersPerSecond
     * @param turnSetpoint_radians
     */
    public void commandSetpointValues(double velocitySetpoint_metersPerSecond, double turnSetpoint_radians) {

        double realSetpoint_radians = Angle.bind2pi_radians(turnSetpoint_radians); //bind
        double difference_radians = realSetpoint_radians - wheelAxisEncoder.getMechanismPosition_radians();
        double velocitySetpoint = velocitySetpoint_metersPerSecond;

        preFilteredSetpoint.log(realSetpoint_radians);

        if (difference_radians >= Math.PI) {
            realSetpoint_radians -= 2.0 * Math.PI;
        } else if (difference_radians < -Math.PI) {
            realSetpoint_radians += 2.0 * Math.PI;
        }
        difference_radians = realSetpoint_radians - wheelAxisEncoder.getMechanismPosition_radians();

        if (difference_radians > Math.PI / 2.0 || difference_radians < -Math.PI / 2.0) {
            // Only need to add 180 deg here because the target angle will be put back into the range [0, 2pi)
            realSetpoint_radians += Math.PI;
            velocitySetpoint *= -1.0;
        }

        realSetpoint_radians = Angle.bind2pi_radians(turnSetpoint_radians);

        filteredSetpoint.log(realSetpoint_radians);

        drive.moveAt(velocitySetpoint_metersPerSecond);
        turn.moveAt(realSetpoint_radians);
    }
}
