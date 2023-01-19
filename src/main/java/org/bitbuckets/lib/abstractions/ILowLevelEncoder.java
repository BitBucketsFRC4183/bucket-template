package org.bitbuckets.lib.abstractions;

public interface ILowLevelEncoder extends IRaw {

    double getRawPosition_baseUnits();
    double getRawVelocity_baseUnitsBaseTime();
    void forceOffset(double offsetUnits_baseUnits);


}
