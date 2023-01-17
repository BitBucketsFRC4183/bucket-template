package org.bitbuckets.drive.module;

import org.bitbuckets.lib.IProcessPath;
import org.bitbuckets.lib.ISetup;
import org.bitbuckets.lib.encoder.IEncoder;
import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.motor.IMotor;
import org.bitbuckets.lib.network.Loggable;

public class ModuleSetup implements ISetup<IModule> {

    final ISetup<IMotor> drive;
    final ISetup<IMotor> turn;
    final ISetup<IEncoder> driveEncoder;
    final ISetup<IRotationEncoder> turnEncoder;

    public ModuleSetup(ISetup<IMotor> drive, ISetup<IMotor> turn, ISetup<IEncoder> driveEncoder, ISetup<IRotationEncoder> turnEncoder) {
        this.drive = drive;
        this.turn = turn;
        this.driveEncoder = driveEncoder;
        this.turnEncoder = turnEncoder;
    }

    @Override
    public IModule build(IProcessPath path) {
        IMotor driveMotor = drive.build( path.addChild("drive-motor") );
        IMotor turnMotor = turn.build( path.addChild("turn-motor") );
        IRotationEncoder turnEncoderSelf = turnEncoder.build( path.addChild("turn-encoder") );
        IEncoder driveEncoderSelf = driveEncoder.build( path.addChild("drive-encoder") );



        throw new UnsupportedOperationException();
        //return new GenericModule(driveMotor, turnMotor, turnEncoderSelf, filter, preFilter);
    }
}
