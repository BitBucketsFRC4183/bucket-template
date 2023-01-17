package org.bitbuckets.drive.control;

import org.bitbuckets.drive.module.IModule;
import org.bitbuckets.lib.IProcessPath;
import org.bitbuckets.lib.ISetup;

/**
 * Sets up prereqs for a drive controller
 *
 * really fucking simple because a drivecontrol is super simple LMAO
 */
public class DriveControlSetup implements ISetup<DriveControl> {

    final ISetup<IModule> frontLeft;
    final ISetup<IModule> frontRight;
    final ISetup<IModule> backLeft;
    final ISetup<IModule> backRight;

    public DriveControlSetup(ISetup<IModule> frontLeft, ISetup<IModule> frontRight, ISetup<IModule> backLeft, ISetup<IModule> backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
    }


    @Override
    public DriveControl build(IProcessPath path) {
        DriveControl control = new DriveControl(
                frontLeft.build(path.addChild("swerve-module-fr")),
                frontRight.build(path.addChild("swerve-module-fl")),
                backLeft.build(path.addChild("swerve-module-br")),
                backRight.build(path.addChild("swerve-module-bl"))
        );

        path.logFactory().periodicModuleLogger("ass", () -> control.cachedSetpoint);

        return control;
    }
}
