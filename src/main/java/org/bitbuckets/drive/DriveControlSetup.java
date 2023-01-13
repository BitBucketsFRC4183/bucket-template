package org.bitbuckets.drive;

import org.bitbuckets.drive.module.SwerveModuleSetup;
import org.bitbuckets.lib.ISetup;
import org.bitbuckets.lib.UserBucketLib;

/**
 * Sets up prereqs for a drive controller
 *
 * really fucking simple because a drivecontrol is super simple LMAO
 */
public class DriveControlSetup implements ISetup<RealDriveControl> {

    final SwerveModuleSetup fr;
    final SwerveModuleSetup fl;
    final SwerveModuleSetup br;
    final SwerveModuleSetup bl;

    public DriveControlSetup(SwerveModuleSetup fr, SwerveModuleSetup fl, SwerveModuleSetup br, SwerveModuleSetup bl) {
        this.fr = fr;
        this.fl = fl;
        this.br = br;
        this.bl = bl;
    }

    @Override
    public RealDriveControl build(UserBucketLib userBucketLib) {
        return new RealDriveControl(
                fr.build(userBucketLib.child("swerve-module-fr")),
                fl.build(userBucketLib.child("swerve-module-fl")),
                br.build(userBucketLib.child("swerve-module-br")),
                bl.build(userBucketLib.child("swerve-module-bl"))
        );
    }
}
