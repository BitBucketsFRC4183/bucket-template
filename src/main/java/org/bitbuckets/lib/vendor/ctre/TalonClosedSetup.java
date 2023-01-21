package org.bitbuckets.lib.vendor.ctre;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import org.bitbuckets.lib.IProcessPath;
import org.bitbuckets.lib.LibConstants;
import org.bitbuckets.lib.PIDIndex;
import org.bitbuckets.lib.abstractions.IMotorController;
import org.bitbuckets.lib.abstractions.ISetup;
import org.bitbuckets.lib.abstractions.MotorMode;
import org.bitbuckets.lib.abstractions.log.IDataLogger;
import org.bitbuckets.lib.implementations.MotorController;
import org.bitbuckets.lib.implementations.MotorControllerData;
import org.bitbuckets.lib.implementations.MotorControllerDataAutoGen;
import org.bitbuckets.robot.RobotConstants;

public class TalonClosedSetup implements ISetup<IMotorController> {

    final MotorMode motorMode;
    final int canId;

    final double selfToMechanismFactor;
    final double currentLimit;
    final double[] pidConstants;

    /**
     * @param motorMode             true for position, false for velocity
     * @param canId
     * @param selfToMechanismFactor factor converting revolutions of the encoder to the revolutions of the main axis
     * @param currentLimit          supply side current limit useful for making your motor not explode the breaker
     * @param pidConstants
     */
    public TalonClosedSetup(MotorMode motorMode, int canId, double selfToMechanismFactor, double currentLimit, double[] pidConstants) {
        this.motorMode = motorMode;
        this.canId = canId;
        this.selfToMechanismFactor = selfToMechanismFactor;
        this.currentLimit = currentLimit;
        this.pidConstants = pidConstants;
    }

    @Override
    public IMotorController build(IProcessPath path) {

        WPI_TalonFX talon = new WPI_TalonFX(canId);

        talon.configVoltageCompSaturation(12);
        talon.enableVoltageCompensation(true);
        SupplyCurrentLimitConfiguration config = new SupplyCurrentLimitConfiguration(true, currentLimit, 0, 0);
        talon.configSupplyCurrentLimit(config, 250);
        talon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, LibConstants.TIMEOUT_MS);

        talon.config_kP(0, pidConstants[PIDIndex.P]);
        talon.config_kI(0, pidConstants[PIDIndex.I]);
        talon.config_kD(0, pidConstants[PIDIndex.D]);

        talon.setSensorPhase(true);
        talon.setInverted(true);
        talon.setNeutralMode(NeutralMode.Brake);

        IDataLogger<MotorControllerData> logger = path.generatePushDataLogger(MotorControllerDataAutoGen::new);
        TalonLowLevelEncoder low = new TalonLowLevelEncoder(talon);
        
        TalonMotor motor = new TalonMotor(talon, motorMode.toCtre());

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
