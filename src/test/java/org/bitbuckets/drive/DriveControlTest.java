package org.bitbuckets.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.drive.control.DriveControl;
import org.bitbuckets.drive.module.IModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DriveControlTest {

    @Test
    public void queryState() {
        IModule drive1 = Mockito.mock(IModule.class);
        IModule drive2 = Mockito.mock(IModule.class);
        IModule drive3 = Mockito.mock(IModule.class);
        IModule drive4 = Mockito.mock(IModule.class);

        Mockito.when(drive1.reportState()).thenReturn(new SwerveModuleState(5, Rotation2d.fromDegrees(180)));

        new DriveControl(drive1, drive2, drive3, drive4);
    }

}
