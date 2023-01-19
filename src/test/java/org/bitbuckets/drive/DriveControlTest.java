package org.bitbuckets.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.drive.control.DriveControl;
import org.bitbuckets.drive.control.DriveControlDataAutoGen;
import org.bitbuckets.drive.module.IModule;
import org.bitbuckets.lib.abstractions.log.IDataLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * TODO more than just mocking tests
 */
public class DriveControlTest {

    @Test
    public void reportActualStates_shouldReflectCurrentStatesStates() {
        IModule drive1 = Mockito.mock(IModule.class);
        IModule drive2 = Mockito.mock(IModule.class);
        IModule drive3 = Mockito.mock(IModule.class);
        IModule drive4 = Mockito.mock(IModule.class);
        IDataLogger<DriveControlDataAutoGen> logger = Mockito.mock(IDataLogger.class);

        Mockito.when(drive1.reportState()).thenReturn(new SwerveModuleState(5, Rotation2d.fromDegrees(180)));
        Mockito.when(drive2.reportState()).thenReturn(new SwerveModuleState(5, Rotation2d.fromDegrees(180)));
        Mockito.when(drive3.reportState()).thenReturn(new SwerveModuleState(5, Rotation2d.fromDegrees(180)));
        Mockito.when(drive4.reportState()).thenReturn(new SwerveModuleState(5, Rotation2d.fromDegrees(180)));

        DriveControl control = new DriveControl(logger, drive1, drive2, drive3, drive4);

        SwerveModuleState[] states = control.reportActualStates();

        Assertions.assertEquals(5, states[0].speedMetersPerSecond);
        Assertions.assertEquals(180, states[1].angle.getDegrees(), 0.01);
    }

    @Test
    public void reportState_shouldReflectSetpoints() {
        IModule drive1 = Mockito.mock(IModule.class);
        IModule drive2 = Mockito.mock(IModule.class);
        IModule drive3 = Mockito.mock(IModule.class);
        IModule drive4 = Mockito.mock(IModule.class);
        IDataLogger<DriveControlDataAutoGen> logger = Mockito.mock(IDataLogger.class);
        Mockito.when(drive1.reportState()).thenReturn(new SwerveModuleState(0, Rotation2d.fromDegrees(0)));

        DriveControl control = new DriveControl(logger, drive1, drive2, drive3, drive4);
        control.doDriveWithStates(new SwerveModuleState[]{
                new SwerveModuleState(5, Rotation2d.fromDegrees(180))
        });

        Assertions.assertEquals(5, control.reportSetpointStates()[0].speedMetersPerSecond);


    }

}
