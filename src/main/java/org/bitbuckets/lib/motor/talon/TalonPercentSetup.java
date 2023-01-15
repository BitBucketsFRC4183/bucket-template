package org.bitbuckets.lib.motor.talon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import org.bitbuckets.lib.IHandle;
import org.bitbuckets.lib.ISetup;
import org.bitbuckets.lib.constants.LibConstants;
import org.bitbuckets.lib.ctre.Talon;
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
    public TalonMotor build(IHandle userBucketLib) {
        TalonFX talon = Talon.init(id);
        talon.configFactoryDefault();
        talon.configVoltageCompSaturation(12);
        talon.enableVoltageCompensation(true);
        SupplyCurrentLimitConfiguration config = new SupplyCurrentLimitConfiguration(!Double.isNaN(currentLimit), currentLimit, 0,0);
        talon.configSupplyCurrentLimit(config, 250);
        talon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, LibConstants.TIMEOUT_MS);

        talon.setSensorPhase(true);
        talon.setInverted(inverted);
        talon.setNeutralMode(NeutralMode.Brake);

        return new TalonMotor(talon, ControlMode.PercentOutput);
    }
}