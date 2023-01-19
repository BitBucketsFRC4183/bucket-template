package org.bitbuckets.drive.module;

import org.bitbuckets.lib.IProcessPath;
import org.bitbuckets.lib.abstractions.IMotorController;
import org.bitbuckets.lib.abstractions.ISetup;
import org.bitbuckets.lib.abstractions.log.IDataLogger;

public class ModuleSetup implements ISetup<IModule> {

    final ISetup<IMotorController> driveMotor;
    final ISetup<IMotorController> turnMotor;

    public ModuleSetup(ISetup<IMotorController> driveMotor, ISetup<IMotorController> turnMotor) {
        this.driveMotor = driveMotor;
        this.turnMotor = turnMotor;
    }

    @Override
    public IModule build(IProcessPath path) {

        IMotorController drive = driveMotor.build(path.addChild("drive"));
        IMotorController turn = turnMotor.build(path.addChild("turn"));

        IDataLogger<ModuleData> logger = path.generatePushDataLogger(ModuleDataAutoGen::new);

        return new Module(drive, turn, drive, new FilteredEncoder(turn), logger);
    }
}
