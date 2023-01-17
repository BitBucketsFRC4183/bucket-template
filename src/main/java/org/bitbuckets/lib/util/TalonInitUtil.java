package org.bitbuckets.lib.util;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import org.bitbuckets.lib.CTREPhysicsSim;

import java.util.HashMap;
import java.util.Map;

public class TalonInitUtil {

    static final Map<Integer, WPI_TalonFX> fxMap = new HashMap<>();

    public static WPI_TalonFX init(int id) {
        WPI_TalonFX fx = fxMap.get(id);

        if (fx == null) {
            fx = new WPI_TalonFX(id);
            fxMap.put(id, fx);
        }

        CTREPhysicsSim.getInstance().addFX(fx, .75, 5100, true);

        return fx;
    }

}
