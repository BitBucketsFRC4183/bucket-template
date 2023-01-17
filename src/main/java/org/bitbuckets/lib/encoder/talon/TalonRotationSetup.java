package org.bitbuckets.lib.encoder.talon;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import org.bitbuckets.lib.IProcessPath;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import org.bitbuckets.lib.ISetup;
import org.bitbuckets.lib.util.TalonInitUtil;
import org.bitbuckets.lib.encoder.IRotationEncoder;

public class TalonRotationSetup implements ISetup<IRotationEncoder> {

    final int id;
    final double gearRatio;

    public TalonRotationSetup(int id, double gearRatio) {
        this.id = id;
        this.gearRatio = gearRatio;
    }

    @Override
    public IRotationEncoder build(IProcessPath path) {
        TalonFX fx = TalonInitUtil.init(id);

        fx.setInverted(true);
        fx.configIntegratedSensorAbsoluteRange(AbsoluteSensorRange.Unsigned_0_to_360);

        TalonRotationEncoder encoder = new TalonRotationEncoder(gearRatio, fx::getSelectedSensorPosition, () -> fx   );

        //TODO logging, other setup

        path.logFactory().periodicDoubleLogger("bound", encoder::getEncoderPositionBounded_radians);
        path.logFactory().periodicDoubleLogger("accum", encoder::getEncoderPositionAccumulated_radians);
        path.logFactory().periodicDoubleLogger("mechbound", encoder::getMechanismPositionBounded_radians);
        path.logFactory().periodicDoubleLogger("mechaccum", encoder::getMechanismPositionAccumulated_radians);
        path.logFactory().periodicDoubleLogger("raw",encoder::getPositionRaw);

        return encoder;
    }
}
