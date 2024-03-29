package org.bitbuckets;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.hal.HAL;
import org.bitbuckets.lib.CTREPhysicsSim;
import org.bitbuckets.lib.util.CTRETestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WPITalonFXTest {

    @Test
    public void talonShouldReportMovement() {
        assert HAL.initialize(500,0);

        WPI_TalonFX fx = new WPI_TalonFX(0);
        CTREPhysicsSim.getInstance().addFX(fx, .75, 5100, false);
        fx.set(ControlMode.PercentOutput, 1);

        CTRETestUtil.waitForCTREUpdate();

        Assertions.assertEquals(1, fx.getMotorOutputPercent());
        Assertions.assertEquals(12.0, fx.getBusVoltage());
    }

}
