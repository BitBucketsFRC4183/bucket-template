package org.bitbuckets.lib.ctre;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import java.util.HashMap;
import java.util.Map;

public class Talon {

    static final Map<Integer, WPI_TalonFX> fxMap = new HashMap<>();

    public static WPI_TalonFX init(int id) {
        WPI_TalonFX fx = fxMap.get(id);

        if (fx == null) {
            fx = new WPI_TalonFX(id);
            fxMap.put(id, fx);
        }

        return fx;
    }

}
