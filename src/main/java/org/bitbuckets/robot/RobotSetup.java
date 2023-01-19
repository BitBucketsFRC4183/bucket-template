package org.bitbuckets.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.bitbuckets.DriveSubsystem;
import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.drive.DriveInput;
import org.bitbuckets.drive.control.DriveControl;
import org.bitbuckets.drive.control.DriveControlSetup;
import org.bitbuckets.drive.module.ModuleSetup;
import org.bitbuckets.lib.IProcessPath;
import org.bitbuckets.lib.PIDIndex;
import org.bitbuckets.lib.abstractions.ISetup;
import org.bitbuckets.lib.abstractions.log.ISignalLogger;
import org.bitbuckets.lib.vendor.ctre.TalonClosedSetup;
import org.bitbuckets.lib.vendor.ctre.TalonPercentSetup;

public class RobotSetup implements ISetup<RobotContainer> {


    @Override
    public RobotContainer build(IProcessPath path) {

        //ISignalLogger SYSTEMS_GREEN = path.generateSignalLogger("SYSTEMS_GREEN"); //signal in dashboard


        ModuleSetup frontLeftModule = new ModuleSetup(
                new TalonPercentSetup(1, 80, DriveConstants.DRIVE_REDUCTION),
                new TalonClosedSetup(
                        false,
                        2,
                        DriveConstants.TURN_REDUCTION,
                        20,
                        PIDIndex.CONSTANTS(1,0,0.1,0,0)
                )
        );

        ModuleSetup frontRightModule = new ModuleSetup(
                new TalonPercentSetup(7, 80, DriveConstants.DRIVE_REDUCTION),
                new TalonClosedSetup(
                        false,
                        8,
                        DriveConstants.TURN_REDUCTION,
                        20,
                        PIDIndex.CONSTANTS(1,0,0.1,0,0)
                )
        );

        ModuleSetup backleftModule = new ModuleSetup(
                new TalonPercentSetup(5, 80, DriveConstants.DRIVE_REDUCTION),
                new TalonClosedSetup(
                        false,
                        6,
                        DriveConstants.TURN_REDUCTION,
                        20,
                        PIDIndex.CONSTANTS(1,0,0.1,0,0)
                )
        );

        ModuleSetup backRightModule = new ModuleSetup(
                new TalonPercentSetup(3, 80, DriveConstants.DRIVE_REDUCTION),
                new TalonClosedSetup(
                        false,
                        4,
                        DriveConstants.TURN_REDUCTION,
                        20,
                        PIDIndex.CONSTANTS(1,0,0.1,0,0)
                )
        );


        DriveControl driveControl = new DriveControlSetup(frontLeftModule, frontRightModule, backleftModule, backRightModule).build(path.addChild("drive-control"));
        DriveInput input = new DriveInput(new Joystick(0));
        DriveSubsystem driveSubsystem = new DriveSubsystem(input, driveControl);

        //SYSTEMS_GREEN.setOn(); //LET'S WIN SOME DAMN REGIONALS!!

        return new RobotContainer(driveSubsystem);
    }
}
