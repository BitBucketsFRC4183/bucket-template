package org.bitbuckets.lib.encoder.talon;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import org.bitbuckets.lib.IHandle;
import org.bitbuckets.lib.ISetup;
import org.bitbuckets.lib.ctre.Talon;
import org.bitbuckets.lib.encoder.IRotationEncoder;

public class TalonRotationSetup implements ISetup<IRotationEncoder> {

    final int id;
    final double gearRatio;

    public TalonRotationSetup(int id, double gearRatio) {
        this.id = id;
        this.gearRatio = gearRatio;
    }

    @Override
    public IRotationEncoder build(IHandle userBucketLib) {
        TalonFX fx = Talon.init(id);

        fx.setInverted(true);
        fx.configIntegratedSensorAbsoluteRange(AbsoluteSensorRange.Unsigned_0_to_360);

        MockEncoder encoder = new MockEncoder(gearRatio, fx::getSelectedSensorPosition, () -> fx   );

        //TODO logging, other setup

        userBucketLib.logFactory().periodicDoubleLogger("bound", encoder::getEncoderPositionBounded_radians);
        userBucketLib.logFactory().periodicDoubleLogger("accum", encoder::getEncoderPositionAccumulated_radians);
        userBucketLib.logFactory().periodicDoubleLogger("mechbound", encoder::getMechanismPositionBounded_radians);
        userBucketLib.logFactory().periodicDoubleLogger("mechaccum", encoder::getMechanismPositionAccumulated_radians);
        userBucketLib.logFactory().periodicDoubleLogger("raw",encoder::getPositionRaw);

        return encoder;
    }
}
