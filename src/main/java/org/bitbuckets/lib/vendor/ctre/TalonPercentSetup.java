package org.bitbuckets.lib.vendor.ctre;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import org.bitbuckets.lib.IProcessPath;
import org.bitbuckets.lib.LibConstants;
import org.bitbuckets.lib.abstractions.IMotorController;
import org.bitbuckets.lib.abstractions.ISetup;
import org.bitbuckets.lib.abstractions.log.IDataLogger;
import org.bitbuckets.lib.implementations.MotorController;
import org.bitbuckets.lib.implementations.MotorControllerData;
import org.bitbuckets.lib.implementations.MotorControllerDataAutoGen;
import org.bitbuckets.robot.RobotConstants;

public class TalonPercentSetup implements ISetup<IMotorController> {

    final int canId;

    //TODO read this stuff from config
    final double currentLimit;
    final double selfToMechanismFactor;

    public TalonPercentSetup(int canId, double currentLimit, double selfToMechanismFactor) {
        this.canId = canId;
        this.currentLimit = currentLimit;
        this.selfToMechanismFactor = selfToMechanismFactor;
    }

    @Override
    public IMotorController build(IProcessPath path) {

        WPI_TalonFX talon = new WPI_TalonFX(canId);

        talon.configVoltageCompSaturation(12);
        talon.enableVoltageCompensation(true);
        SupplyCurrentLimitConfiguration config = new SupplyCurrentLimitConfiguration(true, currentLimit, 0,0);
        talon.configSupplyCurrentLimit(config, 250);
        talon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, LibConstants.TIMEOUT_MS);

        talon.setSensorPhase(true);
        talon.setInverted(true);
        talon.setNeutralMode(NeutralMode.Brake);

        IDataLogger<MotorControllerData> logger = path.generatePushDataLogger(MotorControllerDataAutoGen::new);
        TalonLowLevelEncoder low = new TalonLowLevelEncoder(talon);
        TalonMotor motor = new TalonMotor(talon, ControlMode.PercentOutput);

        MotorController controller = new MotorController(
                motor,
                low,
                logger,
                TalonConstants.TALON_UNITS_TO_ROTATIONS,
                (RobotConstants.WHEEL_DIAMETER_METERS / 2.0),
                selfToMechanismFactor,
                TalonConstants.TALON_TIME_TO_SECONDS
        );

        path.registerLoop(controller::guaranteedLoggingLoop);

        return controller;
    }
}
