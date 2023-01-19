package org.bitbuckets.lib.implementations;

import org.bitbuckets.lib.abstractions.ILowLevelEncoder;
import org.bitbuckets.lib.abstractions.IMotor;
import org.bitbuckets.lib.abstractions.IMotorController;
import org.bitbuckets.lib.abstractions.log.IDataLogger;
import org.bitbuckets.lib.implementations.encoder.Angle;

//TODO write tests and documentation

/**
 * Basic implementation of the IEncoder interface which allows the usage of ILowLevelEncoder
 * real laggy if you try to log it directly, use the caching wrapper
 */
public class MotorController implements IMotorController {

    final IMotor motorDelegate;
    final ILowLevelEncoder lowLevelEncoder;
    final IDataLogger<MotorControllerData> logger;
    final double rawUnitsToRotationsFactor;
    final double rotationsToMetersFactor;
    final double selfToMechanismFactor;
    final double baseTimeToSecondsFactor;

    public MotorController(IMotor motorDelegate, ILowLevelEncoder lowLevelEncoder, IDataLogger<MotorControllerData> logger, double rawUnitsToRotationsFactor, double rotationsToMetersFactor, double selfToMechanismFactor, double baseTimeToSecondsFactor) {
        this.motorDelegate = motorDelegate;
        this.lowLevelEncoder = lowLevelEncoder;
        this.logger = logger;
        this.rawUnitsToRotationsFactor = rawUnitsToRotationsFactor;
        this.rotationsToMetersFactor = rotationsToMetersFactor;
        this.selfToMechanismFactor = selfToMechanismFactor;
        this.baseTimeToSecondsFactor = baseTimeToSecondsFactor;
    }

    public void guaranteedLoggingLoop() {
        logger.process(data -> {
            data.currentSetpoint = motorDelegate.getSetpoint();
            data.encoderReadoutRaw = lowLevelEncoder.getRawPosition_baseUnits();
            data.encoderPositionBounded = getEncoderPositionBounded_radians();
            data.encoderPositionAccumulated = getEncoderPositionAccumulated_radians();
            data.mechanismPositionBounded = getMechanismPositionBounded_radians();
            data.mechanismPositionAccumulated = getMechanismPositionAccumulated_radians();
        });
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) throws UnsupportedOperationException {
        return lowLevelEncoder.rawAccess(clazz);
    }

    @Override
    public double getMechanismFactor() {
        return selfToMechanismFactor;
    }

    @Override
    public double getRotationsToMetersFactor() {
        return rotationsToMetersFactor;
    }

    @Override
    public double getPositionRaw() {
        return lowLevelEncoder.getRawPosition_baseUnits();
    }

    @Override
    public void forceOffset(double offsetUnits_baseUnits) {
        lowLevelEncoder.forceOffset(offsetUnits_baseUnits);
    }

    @Override
    public double getEncoderPositionAccumulated_radians() {
        return lowLevelEncoder.getRawPosition_baseUnits() * rawUnitsToRotationsFactor * Math.PI * 2.0;
    }

    @Override
    public double getEncoderPositionBounded_radians() {
        return Angle.wrap(getEncoderPositionAccumulated_radians());
    }

    @Override
    public double getMechanismPositionAccumulated_radians() {
        return getEncoderPositionAccumulated_radians() * selfToMechanismFactor;
    }

    @Override
    public double getMechanismPositionBounded_radians() {
        return Angle.wrap(getMechanismPositionAccumulated_radians());
    }

    @Override
    public double getPositionMechanism_meters() {
        return getPositionEncoder_meters() * selfToMechanismFactor;
    }

    @Override
    public double getPositionEncoder_meters() {
        return lowLevelEncoder.getRawPosition_baseUnits() * rawUnitsToRotationsFactor * rotationsToMetersFactor;
    }

    @Override
    public double getVelocityMechanism_metersPerSecond() {
        return getVelocityEncoder_metersPerSecond() * selfToMechanismFactor;
    }

    @Override
    public double getVelocityEncoder_metersPerSecond() {
        return lowLevelEncoder.getRawVelocity_baseUnitsBaseTime() * baseTimeToSecondsFactor * rawUnitsToRotationsFactor *rotationsToMetersFactor;
    }

    @Override
    public void moveAt(double units) {
        motorDelegate.moveAt(units);
    }

    @Override
    public double getSetpoint() {
        return motorDelegate.getSetpoint();
    }
}
