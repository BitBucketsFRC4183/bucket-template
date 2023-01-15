package org.bitbuckets.drive.module;

import org.bitbuckets.lib.IHandle;
import org.bitbuckets.lib.ISetup;
import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.motor.IMotor;
import org.bitbuckets.lib.network.Loggable;

public class GenericModuleSetup implements ISetup<GenericModule> {

    final ISetup<IMotor> drive;
    final ISetup<IMotor> turn;
    final ISetup<IRotationEncoder> turnEncoder;

    public GenericModuleSetup(ISetup<IMotor> drive, ISetup<IMotor> turn, ISetup<IRotationEncoder> turnEncoder) {
        this.drive = drive;
        this.turn = turn;
        this.turnEncoder = turnEncoder;
    }



    @Override
    public GenericModule build(IHandle tools) {
        IMotor driveMotor = drive.build(tools.child("drive-motor"));
        IMotor turnMotor = turn.build(tools.child("turn-motor"));
        IRotationEncoder turnEncoderSelf = turnEncoder.build(tools.child("turn-encoder"));

        Loggable<Double> preFilter = tools.logFactory().doubleLogger("pre-filtered-setpoint");
        Loggable<Double> filter = tools.logFactory().doubleLogger("setpoint");

        return new GenericModule(driveMotor, turnMotor, turnEncoderSelf, filter, preFilter);
    }
}
