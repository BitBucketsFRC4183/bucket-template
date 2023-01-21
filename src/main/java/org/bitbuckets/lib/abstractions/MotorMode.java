package org.bitbuckets.lib.abstractions;

import com.ctre.phoenix.motorcontrol.ControlMode;

public enum MotorMode {
    Position,
    Velocity,
    PercentOutput;

    public ControlMode toCtre() {
        switch (this) {
            case Position:
                return ControlMode.Position;
            case Velocity:
                return ControlMode.Velocity;
            case PercentOutput:
                return ControlMode.PercentOutput;
        }
        // should not be possible...
        throw new IllegalArgumentException("Invalid control mode");
    }
}
