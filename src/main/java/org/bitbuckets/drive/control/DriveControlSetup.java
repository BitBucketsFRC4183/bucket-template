package org.bitbuckets.drive.control;

import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.drive.module.IModule;
import org.bitbuckets.lib.IProcessPath;
import org.bitbuckets.lib.abstractions.ISetup;
import org.bitbuckets.lib.abstractions.log.IDataLogger;

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
        IDataLogger<DriveControlDataAutoGen> logger = path.generatePushDataLogger(DriveControlDataAutoGen::new);
        DriveControl control = new DriveControl(
                logger,
                frontLeft.build(path.addChild("swerve-module-fr")),
                frontRight.build(path.addChild("swerve-module-fl")),
                backLeft.build(path.addChild("swerve-module-br")),
                backRight.build(path.addChild("swerve-module-bl"))
        );


        path.registerLoop(control::guaranteedLoggingLoop);

        return control;
    }
}
