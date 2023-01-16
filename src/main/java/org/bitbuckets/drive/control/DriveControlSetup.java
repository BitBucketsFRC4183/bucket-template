package org.bitbuckets.drive.control;

import org.bitbuckets.drive.module.GenericModule;
import org.bitbuckets.drive.module.GenericModuleSetup;
import org.bitbuckets.lib.IHandle;
import org.bitbuckets.lib.ISetup;

/**
 * Sets up prereqs for a drive controller
 *
 * really fucking simple because a drivecontrol is super simple LMAO
 */
public class DriveControlSetup implements ISetup<DriveControl> {

    final ISetup<GenericModule> frontLeft;
    final ISetup<GenericModule> frontRight;
    final ISetup<GenericModule> backLeft;
    final ISetup<GenericModule> backRight;

    public DriveControlSetup(ISetup<GenericModule> frontLeft, ISetup<GenericModule> frontRight, ISetup<GenericModule> backLeft, ISetup<GenericModule> backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
    }


    @Override
    public DriveControl build(IHandle userBucketLib) {



        DriveControl control = new DriveControl(
                frontLeft.build(userBucketLib.child("swerve-module-fr")),
                frontRight.build(userBucketLib.child("swerve-module-fl")),
                backLeft.build(userBucketLib.child("swerve-module-br")),
                backRight.build(userBucketLib.child("swerve-module-bl"))
        );

        userBucketLib.logFactory().periodicModuleLogger("ass", () -> control.cached);

        return control;
    }
}
