package org.bitbuckets.lib.motor;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import org.bitbuckets.lib.LibConstants;
import org.bitbuckets.lib.index.PIDIndex;
import org.bitbuckets.lib.motor.setpoint.IMotor;
import org.bitbuckets.lib.motor.setpoint.TalonMotor;
import org.bitbuckets.lib.motor.stages.*;
import org.bitbuckets.lib.network.INetworking;

/**
 * Implements the stages in a way that guaruntees the talon is configured
 * i know this is a sloppy way of doing things but it makes it so that there are chained, ordered method calls
 * that force the user to configure their talon before actually acquiring it
 *
 * this makes things overall safer because you know everything that should be configured is
 *
 * this is like motorutils but worse
 */
public class TalonStages implements InitPreBuild, PercentPreBuild, PidPreBuild, EncoderMotorPreBuild, MotorPreBuild {

    final int processID;
    final INetworking console;
    final BaseTalon talonWithID;

    public TalonStages(int processID, INetworking console, BaseTalon talonWithID) {
        this.processID = processID;
        this.console = console;
        this.talonWithID = talonWithID;
    }

    ControlMode cachedMode = ControlMode.Disabled;

    @Override
    public PercentPreBuild usePercent() {
        cachedMode = ControlMode.PercentOutput;

        talonWithID.set(ControlMode.PercentOutput, 0);
        return this;
    }

    @Override
    public PidPreBuild usePID(boolean positionOrVelocity, double[] constants) {
        cachedMode = positionOrVelocity ? ControlMode.Velocity : ControlMode.Position; //ternary is a tiny if statement

        talonWithID.set(ControlMode.PercentOutput, 0); // i said stop

        if (talonWithID.config_kP(0, constants[PIDIndex.P], LibConstants.TIMEOUT_MS) != ErrorCode.OK) {
            console.flagError(processID, "unable to kP, rare");
        }

        if (talonWithID.config_kI(0, constants[PIDIndex.I], LibConstants.TIMEOUT_MS) != ErrorCode.OK) {
            console.flagError(processID, "unable to kI, rare");
        }

        if (talonWithID.config_kD(0, constants[PIDIndex.D], LibConstants.TIMEOUT_MS) != ErrorCode.OK) {
            console.flagError(processID, "unable to kD, rare");
        }

        if (talonWithID.config_kF(0, constants[PIDIndex.FF], LibConstants.TIMEOUT_MS) != ErrorCode.OK) {
            console.flagError(processID, "unable to kD, rare");
        }

        if (talonWithID.config_IntegralZone(0, constants[PIDIndex.IZONE], LibConstants.TIMEOUT_MS) != ErrorCode.OK) {
            console.flagError(processID, "unable to kI, rare");
        }

        return this;
    }


    @Override
    public EncoderMotorPreBuild withEncoder(EncoderType type) {

        ErrorCode code = null;
        if (type == EncoderType.ANALOG) {
            code = talonWithID.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, LibConstants.TIMEOUT_MS);
        } else if (type == EncoderType.INTEGRATED) {
            code = talonWithID.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, LibConstants.TIMEOUT_MS);
        } else if (type == EncoderType.QUAD) {
            code = talonWithID.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, LibConstants.TIMEOUT_MS);
        }

        if (code != null && code != ErrorCode.OK) {
            console.flagError(processID, "unable to configure encoder mode of controller");
        }

        return this;
    }

    //this is stupid
    @Override
    public EncoderMotorPreBuild withNoEncoder() {
        return this;
    }


    public IControllerEncoder acquireEncoder() {
        //aaaaa

        return null;
    }

    @Override
    public IMotor build(boolean use) {

        if (use) {
            //nothing yet, but i'll do status frame optimizations

        }

        return new TalonMotor(talonWithID, cachedMode);
    }

    @Override
    public IMotor buildNoEncoder(boolean use) {

        if (use) {
            //nothing yet, but i'll do status frame optimizations
        }

        return new TalonMotor(talonWithID, cachedMode);
    }
}
