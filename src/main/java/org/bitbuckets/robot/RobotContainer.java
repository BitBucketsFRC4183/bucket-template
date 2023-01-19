package org.bitbuckets.robot;

import edu.wpi.first.wpilibj.DriverStation;
import org.bitbuckets.DriveSubsystem;
import org.bitbuckets.lib.IRobotContainer;

/**
 * This class represents your robot's periodic behavior
 */
public class RobotContainer implements IRobotContainer {

    final DriveSubsystem subsystem;

    public RobotContainer(DriveSubsystem subsystem) {
        this.subsystem = subsystem;
    }

    @Override
    public void autoPeriodic() {

        DriverStation.reportWarning("ass", false);

    }

    //Shouldn't need to do anything here
    @Override
    public void teleopPeriodic() {
        subsystem.teleopPeriodic();
    }

}
