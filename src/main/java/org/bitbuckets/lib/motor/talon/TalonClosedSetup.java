package org.bitbuckets.lib.motor.talon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import org.bitbuckets.lib.IProcessPath;
import org.bitbuckets.lib.ISetup;
import org.bitbuckets.lib.constants.LibConstants;
import org.bitbuckets.lib.ctre.Talon;
import org.bitbuckets.lib.index.PIDIndex;
import org.bitbuckets.lib.motor.IMotor;

/**
 * PidTalon
 */
public class TalonClosedSetup implements ISetup<IMotor> {

    final int id;
    final double currentLimit;
    final boolean inverted;
    final double[] pidConstants;

    public TalonClosedSetup(int id, double currentLimit, boolean inverted, double[] pidConstants) {
        this.id = id;
        this.currentLimit = currentLimit;
        this.inverted = inverted;
        this.pidConstants = pidConstants;
    }

    @Override
    public TalonMotor build(IProcessPath userBucketLib) {

        //IMPORTANT: IMOTOR setup should happen BEFORE ENCODER setup if ENCODER is integrated!
        TalonFX talon = Talon.init(id);
        talon.configFactoryDefault();
        talon.configVoltageCompSaturation(12);
        talon.enableVoltageCompensation(true);
        SupplyCurrentLimitConfiguration config = new SupplyCurrentLimitConfiguration(!Double.isNaN(currentLimit), currentLimit, 0,0);
        talon.configSupplyCurrentLimit(config, 250);
        talon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, LibConstants.TIMEOUT_MS);

        talon.config_kP(0, pidConstants[PIDIndex.P]);
        talon.config_kI(0, pidConstants[PIDIndex.I]);
        talon.config_kD(0, pidConstants[PIDIndex.D]);

        talon.setSensorPhase(true);
        talon.setInverted(inverted);
        talon.setNeutralMode(NeutralMode.Brake);


        return new TalonMotor(talon, ControlMode.Position);
    }
}