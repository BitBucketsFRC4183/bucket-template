package org.bitbuckets.lib.encoder.fusion;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.lib.encoder.Angle;
import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.motor.BaseUnitType;
import org.bitbuckets.lib.network.Loggable;

public class FusionEncoder implements IRotationEncoder {

    final IRotationEncoder talon;
    final IRotationEncoder can; //we trust you the most (i don't know why you suck)

    int counter = 0;
    boolean fired = false;

    public FusionEncoder(IRotationEncoder talon, IRotationEncoder can) {
        this.talon = talon;
        this.can = can;
    }

    public double calculateAlignSU() {

        double totalAccumPlusAngle_radians = talon.getMechanismPositionAccumulated_radians(); //0
        double totalAngle = totalAccumPlusAngle_radians % (2.0 * Math.PI); //0
        double taoPrewrap = totalAccumPlusAngle_radians - totalAngle;
        double totalAccumOnly = Angle.wrap(taoPrewrap); //accumulated radians


        //0 - 0
        double actualAngle = can.getEncoderPositionBounded_radians(); //1.9
        double totalAccumPlusReal_radians = totalAccumOnly + actualAngle; //1.9
        double adjusted = totalAccumPlusReal_radians / (Math.PI * 2) / talon.getMotorFactor() * 2048;

        return adjusted;
    }

    public void realign() {

        talon.rawAccess(TalonFX.class).setSelectedSensorPosition(calculateAlignSU());

        counter++;

    }

    @Override
    public boolean isAbsolute() {
        return false;
    }

    @Override
    public double getMotorFactor() {
        return talon.getMotorFactor();
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
        return talon.getMechanismPositionBounded_radians();
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
