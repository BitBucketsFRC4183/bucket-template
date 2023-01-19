package org.bitbuckets.lib.vendor.ctre;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import org.bitbuckets.lib.abstractions.ILowLevelEncoder;

class TalonLowLevelEncoder implements ILowLevelEncoder {

    final TalonFX talonFX;

    TalonLowLevelEncoder(TalonFX talonFX) {
        this.talonFX = talonFX;
    }

    @Override
    public double getRawPosition_baseUnits() {
        return talonFX.getSelectedSensorPosition();
    }

    @Override
    public double getRawVelocity_baseUnitsBaseTime() {
        return talonFX.getSelectedSensorVelocity();
    }

    @Override
    public void forceOffset(double offsetUnits_baseUnits) {
        talonFX.setSelectedSensorPosition(offsetUnits_baseUnits);
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) throws UnsupportedOperationException {
        return clazz.cast(talonFX);
    }
}
