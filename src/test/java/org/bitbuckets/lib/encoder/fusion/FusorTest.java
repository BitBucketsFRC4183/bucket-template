package org.bitbuckets.lib.encoder.fusion;

import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.encoder.talon.TalonRotationEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FusorTest {


    @Test
    void calculateOptimalSetpointRadiansForward() {

        IRotationEncoder mock = new TalonRotationEncoder(1, () -> 2048.0, null);
        Fusor fusor = new Fusor(mock);
        double setpoint = fusor.optimizeSetpointWithMechanismRads_encoderRads(0.5 * Math.PI);

        Assertions.assertEquals(2.5 * Math.PI, setpoint);

    }

    @Test
    void calculateOptimalSetpointRadiansBackwards() {

        IRotationEncoder mock = new TalonRotationEncoder(1, () -> 4096.0, null);
        Fusor fusor = new Fusor(mock);
        double setpoint = fusor.optimizeSetpointWithMechanismRads_encoderRads(1.5 * Math.PI);

        Assertions.assertEquals(3.5 * Math.PI, setpoint, 0.0001);

    }


    @Test
    void calculateOptimalSetpoint_shouldBeIntelligent() {
        double gearRatio = 0.5;
        double encoderRadians = 2.0 * Math.PI;
        double encoderModified = gearRatio * 2048.0;

        //2pi encoder radian is pi motor radians
        IRotationEncoder mock = new TalonRotationEncoder(gearRatio, () -> encoderModified, null);
        Fusor fusor = new Fusor(mock);


        double setpoint = fusor.optimizeSetpointWithMechanismRads_encoderRads(0.5 * Math.PI);
        Assertions.assertEquals(3.0 * Math.PI, setpoint, 0.0001);
        //if we are at 2pi axis, we want to move 0.5pi axis, and 1pi encoder translates to 0.5 axis, the most optimal move
        //is to move to 3pi minimizing rotations.

    }

    @Test
    void calculateOptimalSetpoint_shouldGoBackwards() {
        double gearRatio = 0.5;
        double encoderRadians = Math.PI; //we're actually at 1/2 pi mechanism
        double encoderModified = (encoderRadians / Math.PI / 2.0) * 2048.0 * gearRatio;

        IRotationEncoder mock = new TalonRotationEncoder(gearRatio, () -> encoderModified, null);
        Fusor fusor = new Fusor(mock);

        double setpoint = fusor.optimizeSetpointWithMechanismRads_encoderRads(0.25 * Math.PI); //we want to move 1/2 pi encoder radians
        //This should move the motor backwards
        Assertions.assertEquals(0.5 * Math.PI, setpoint, 0.001);
    }
}