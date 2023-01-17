package org.bitbuckets.lib.motor.talon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import org.bitbuckets.lib.CTREPhysicsSim;
import org.bitbuckets.lib.IProcessPath;
import org.bitbuckets.lib.ISetup;
import org.bitbuckets.lib.constants.LibConstants;
import org.bitbuckets.lib.util.TalonInitUtil;
import org.bitbuckets.lib.motor.IMotor;

public class TalonPercentSetup implements ISetup<IMotor> {

    final int id;
    final double currentLimit;
    final boolean inverted;

    public TalonPercentSetup(int id, double currentLimit, boolean inverted) {
        this.id = id;
        this.currentLimit = currentLimit;
        this.inverted = inverted;
    }

    @Override
    public TalonMotor build(IProcessPath path) {
        TalonFX talon = TalonInitUtil.init(id);
        talon.configFactoryDefault();
        talon.configVoltageCompSaturation(12);
        talon.enableVoltageCompensation(true);
        SupplyCurrentLimitConfiguration config = new SupplyCurrentLimitConfiguration(!Double.isNaN(currentLimit), currentLimit, 0,0);
        talon.configSupplyCurrentLimit(config, 250);
        talon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, LibConstants.TIMEOUT_MS);

        talon.setSensorPhase(true);
        talon.setInverted(inverted);
        talon.setNeutralMode(NeutralMode.Brake);

        //TODO get oudda here :(
        CTREPhysicsSim.getInstance().addFX(talon, .75, 5100, true);

        return new TalonMotor(talon, ControlMode.PercentOutput);
    }
}
