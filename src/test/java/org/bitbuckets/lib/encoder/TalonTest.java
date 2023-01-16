package org.bitbuckets.lib.encoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.hal.HAL;
import org.bitbuckets.lib.CTREPhysicsSim;
import org.junit.jupiter.api.*;

public class TalonTest {

    static WPI_TalonFX motor;

    @BeforeAll
    public static void init() {
        assert HAL.initialize(500,0);
        motor = new WPI_TalonFX(0);
        CTREPhysicsSim.getInstance().addFX(motor, .75, 5100, true);
    }





    @Test
    void baseStuff() {

        motor.config_kP(0, 1);
        motor.config_kI(0,0);
        motor.config_kD(0,0.1);
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

        //TalonMotor motor = new TalonClosedSetup(0,20, true, PIDIndex.CONSTANTS(1,0,0.1,0,0)).build(lib.rootHandle());
        //IRotationEncoder e = new TalonRotationSetup(0, DriveConstants.DRIVE_REDUCTION).build(lib.rootHandle());

        motor.set(ControlMode.Position, Math.toRadians(20));

        CanTest.waitForCTREUpdate();


        Assertions.assertEquals(Math.toRadians(20), motor.getSelectedSensorPosition() / 2048 * 2 * Math.PI);
    }

}
