package org.bitbuckets.drive;

import org.bitbuckets.drive.control.RealDriveController;
import org.bitbuckets.drive.module.ModuleSetup;
import org.bitbuckets.lib.IProcessFactory;
import org.bitbuckets.lib.Tools;

/**
 * Sets up prereqs for a drive controller
 *
 * really fucking simple because a drivecontrol is super simple LMAO
 */
public class DriveControlSetup implements IProcessFactory<RealDriveController> {

    final ModuleSetup fr;
    final ModuleSetup fl;
    final ModuleSetup br;
    final ModuleSetup bl;

    public DriveControlSetup(ModuleSetup fr, ModuleSetup fl, ModuleSetup br, ModuleSetup bl) {
        this.fr = fr;
        this.fl = fl;
        this.br = br;
        this.bl = bl;
    }

    @Override
    public RealDriveController build(Tools tools) {
        return new RealDriveController(
                fr.build(tools.child("swerve-module-fr")),
                fl.build(tools.child("swerve-module-fl")),
                br.build(tools.child("swerve-module-br")),
                bl.build(tools.child("swerve-module-bl"))
        );
    }
}
