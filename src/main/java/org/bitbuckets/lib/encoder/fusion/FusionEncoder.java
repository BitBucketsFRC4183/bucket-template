package org.bitbuckets.lib.encoder.fusion;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import org.bitbuckets.lib.encoder.Angle;
import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.motor.BaseUnitType;
import org.bitbuckets.lib.network.Loggable;

public class FusionEncoder implements IRotationEncoder {

    final IRotationEncoder talon;
    final IRotationEncoder can; //we trust you the most (i don't know why you suck)

    int counter = 0;

    public FusionEncoder(IRotationEncoder talon, IRotationEncoder can) {
        this.talon = talon;
        this.can = can;
    }

    public void realign() {

        counter++;

        if (counter > 500) {
            counter = 0;
            //math in mechanism-space
            double totalAccumPlusAngle_radians = talon.getMechanismPositionAccumulated_radians(); //0
            double totalAngle = totalAccumPlusAngle_radians % (2.0 * Math.PI); //0
            double totalAccumOnly = Angle.wrap(totalAccumPlusAngle_radians - totalAngle); //accumulated radians
            //0 - 0
            double actualAngle = can.getEncoderPositionBounded_radians(); //1.9
            double totalAccumPlusReal_radians = totalAccumOnly + actualAngle; //1.9

            //convert from accumulated + real radians back to the talon SU lmao
            double adjusted = totalAccumPlusReal_radians / talon.getMotorFactor() / 2.0 / Math.PI * 2048;

            //TODO WARNING BLOCKING CODE HERE
            talon.rawAccess(TalonFX.class).setSelectedSensorPosition(adjusted, 0, 0);
        }

    }

    @Override
    public boolean isAbsolute() {
        return false;
    }

    @Override
    public double getMotorFactor() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BaseUnitType getBaseUnitType() {
        return BaseUnitType.FUSION;
    }


    //Apparently we trust the can
    @Override
    public double getEncoderPositionAccumulated_radians() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getEncoderPositionBounded_radians() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getMechanismPositionAccumulated_radians() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getMechanismPositionBounded_radians() {
        return can.getMechanismPositionBounded_radians();
    }

    @Override
    public double getPositionRaw() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) {
        throw new UnsupportedOperationException();
    }
}
