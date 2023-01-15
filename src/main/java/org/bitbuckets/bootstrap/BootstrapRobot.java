package org.bitbuckets.bootstrap;

import edu.wpi.first.wpilibj.PowerDistribution;
import org.bitbuckets.lib.BucketLib;
import org.bitbuckets.lib.IRobotContainer;
import org.bitbuckets.lib.ISetup;
import org.bitbuckets.robot.RobotContainer;
import org.bitbuckets.robot.RobotSetup;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

/**
 * Launchpoint for the robot (It's like the launchpoint for the robot or something)
 * Has all the bucketlib and advantagekit code in it so don't touch it unless you really need to
 */
public class BootstrapRobot extends LoggedRobot {


    final ISetup<IRobotContainer> setup;

    public BootstrapRobot(ISetup<IRobotContainer> setup) {
        this.setup = setup;
    }


    BucketLib libHandle;
    RobotContainer robotHandle;

    @Override
    public void robotInit() {

        //inner setup script

        Logger logger = Logger.getInstance();

        logger.recordMetadata("ProjectName", BuildConstants.MAVEN_NAME);
        logger.recordMetadata("BuildDate", BuildConstants.BUILD_DATE);
        logger.recordMetadata("GitSHA", BuildConstants.GIT_SHA);
        logger.recordMetadata("GitDate", BuildConstants.GIT_DATE);
        logger.recordMetadata("GitBranch", BuildConstants.GIT_BRANCH);


        if (isReal()) {
            Logger.getInstance().addDataReceiver(new WPILOGWriter("/media/sda1/")); // Log to a USB stick
            Logger.getInstance().addDataReceiver(new NT4Publisher()); // Publish data to NetworkTables
            new PowerDistribution(1, PowerDistribution.ModuleType.kRev); // Enables power distribution logging
        } else {
            logger.addDataReceiver(new WPILOGWriter("analysis/"));
            logger.addDataReceiver(new NT4Publisher());
        }

        Logger.getInstance().start(); // Start logging! No more data receivers, replay sources, or metadata values may be added.

        BucketLib lib = new BucketLib();
        RobotSetup setup = new RobotSetup();

        robotHandle = setup.build(lib.rootHandle());
        //TODO RobotSetup here, set to a Robot instance, etc

        libHandle = lib;


    }

    @Override
    public void robotPeriodic() {
        libHandle.runLoop();
        robotHandle.teleopPeriodic();
    }

}
