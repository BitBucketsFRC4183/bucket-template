package org.bitbuckets.lib.implementations;

import org.bitbuckets.lib.logging.Loggable;

@Loggable
public class MotorControllerData {

    double currentSetpoint = 0.0;

    double encoderReadoutRaw = 0.0;
    double encoderPositionAccumulated = 0.0;
    double encoderPositionBounded = 0.0;
    double mechanismPositionAccumulated = 0.0;
    double mechanismPositionBounded = 0.0;

    double busVoltage = 0.0;
    double temp = 0.0;

}
