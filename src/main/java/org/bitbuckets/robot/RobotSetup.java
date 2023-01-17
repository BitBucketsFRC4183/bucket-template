package org.bitbuckets.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.bitbuckets.drive.DriveConstants;
import org.bitbuckets.drive.DriveInput;
import org.bitbuckets.drive.control.DriveControl;
import org.bitbuckets.drive.control.DriveControlSetup;
import org.bitbuckets.drive.module.ModuleSetup;
import org.bitbuckets.lib.IProcessPath;
import org.bitbuckets.lib.ISetup;
import org.bitbuckets.lib.encoder.can.CANRotationSetup;
import org.bitbuckets.lib.encoder.talon.TalonRotationSetup;
import org.bitbuckets.lib.index.PIDIndex;
import org.bitbuckets.lib.motor.talon.TalonClosedSetup;
import org.bitbuckets.lib.motor.talon.TalonPercentSetup;

public class RobotSetup implements ISetup<RobotContainer> {


    @Override
    public RobotContainer build(IProcessPath path) {



        /*new FusionRotationSetup(
                new TalonRotationSetup(2, DriveConstants.ROTATION_REDUCTION),
                new CANRotationSetup(9,-232.55,1)
        )
        new FusionRotationSetup(
                new TalonRotationSetup(8, DriveConstants.ROTATION_REDUCTION),
                new CANRotationSetup(12,-(331.96 - 180),1)
        )
        new FusionRotationSetup(
                new TalonRotationSetup(6, DriveConstants.ROTATION_REDUCTION),
                new CANRotationSetup(11,-(255.49),1)
        )
        new FusionRotationSetup(
                new TalonRotationSetup(4, DriveConstants.ROTATION_REDUCTION),
                new CANRotationSetup(10,-(70.66 + 180),1)
        )*/

        DriveControl control = new DriveControlSetup(
                new ModuleSetup(
                        new TalonPercentSetup(1, 80, true),
                        new TalonClosedSetup(2, 20, true, PIDIndex.CONSTANTS(1,0,0.1,0,0)),
                        null,
                        null
                ),
                new ModuleSetup(
                        new TalonPercentSetup(7, 80, true),
                        new TalonClosedSetup(8, 20, true, PIDIndex.CONSTANTS(1,0,0.1,0,0)),
                        null,
                        null
                ),
                new ModuleSetup(
                        new TalonPercentSetup(5, 80, true),
                        new TalonClosedSetup(6, 20, true, PIDIndex.CONSTANTS(1,0,0.1,0,0)),
                        null,
                        null
                ),
                new ModuleSetup(
                        new TalonPercentSetup(3, 80, true),
                        new TalonClosedSetup(4, 20, true, PIDIndex.CONSTANTS(1,0,0.1,0,0)),
                        null,
                        null
                )

        ).build(path.addChild("drive-subsystem"));

        DriveInput input = new DriveInput(new Joystick(0));

        return new RobotContainer(input, control);
    }
}
