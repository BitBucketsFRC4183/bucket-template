package org.bitbuckets.lib.motor;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import org.bitbuckets.lib.motor.stages.InitPreBuild;
import org.bitbuckets.lib.network.INetworking;

import static java.lang.String.format;

/**
 * Represents the absolute essential config values that need to be configured for a talon
 * also checks if the talon exists, etc, basic stuff so i dont have to care
 */
public class FXMotorFactory implements IMotorFactory {

    final int processID;
    final INetworking console;

    public FXMotorFactory(int processID, INetworking console) {
        this.processID = processID;
        this.console = console;
    }

    @Override
    public InitPreBuild buildNewMotor(int canId, boolean brushless, boolean inverted, boolean neutralMode, double currentLimit) {
        TalonFX preInitMotor = new TalonFX(canId);
        if (ErrorCode.OK != preInitMotor.configFactoryDefault()) {
            console.flagError(processID, "something went horribly wrong");
        }


        if (preInitMotor.getLastError() != ErrorCode.OK) {
            console.flagError(processID, format("No Talon FX with can ID [%s]", canId));
        }

        if (ErrorCode.OK != preInitMotor.configVoltageCompSaturation(12, 100)) {
            console.flagError(processID, format("Failed to config voltage saturation on [%s]", canId));
        }
        preInitMotor.enableVoltageCompensation(true);
        preInitMotor.setInverted(inverted);

        if (neutralMode) {
            preInitMotor.setNeutralMode(NeutralMode.Coast);
        } else {
            preInitMotor.setNeutralMode(NeutralMode.Brake);
        }

        SupplyCurrentLimitConfiguration config = new SupplyCurrentLimitConfiguration(!Double.isNaN(currentLimit), currentLimit, 0,0);

        if (preInitMotor.configSupplyCurrentLimit(config, 100) != ErrorCode.OK) {
            console.flagError(processID, format("Failed to config supply current limit on [%s]", canId));
        }

        return new TalonStages(processID, console, preInitMotor);
    }
}
