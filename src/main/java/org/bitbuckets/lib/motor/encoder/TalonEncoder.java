package org.bitbuckets.lib.motor.encoder;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import org.bitbuckets.lib.motor.BaseUnitType;

//TODO this is really risky and also bad. Don't use this unless you have to! (you do)
public class TalonEncoder implements IControllerEncoder {

    final BaseTalon readOnlyTalon;
    final double conversionFactor; //needs to convert from SU to something...

    public TalonEncoder(BaseTalon readOnlyTalon, double conversionFactor) {
        this.readOnlyTalon = readOnlyTalon;
        this.conversionFactor = conversionFactor;
    }

    @Override
    public boolean isAbsolute() {
        return false;
    }

    @Override
    public double getGeneralConversionFactor() {
        return conversionFactor;
    }

    @Override
    public BaseUnitType getBaseUnitType() {
        return BaseUnitType.SU;
    }

    @Override
    public double getPosition() {
        return readOnlyTalon.getSelectedSensorPosition() * conversionFactor;
    }

    @Override
    public double getPositionNoFactor() {
        return 0;
    }

    @Override
    public double getVelocity() {
        return 0;
    }

    @Override
    public double getVelocityNoFactor() {
        return 0;
    }
}
