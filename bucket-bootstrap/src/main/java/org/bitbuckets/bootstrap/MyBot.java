package org.bitbuckets.bootstrap;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import org.bitbuckets.drive.DriveInput;
import org.bitbuckets.drive.controller.IDriveController;
import org.bitbuckets.drive.controller.RealDriveController;
import org.bitbuckets.drive.controller.module.ISwerveModule;
import org.bitbuckets.drive.controller.module.TalonSwerveModule;
import org.bitbuckets.shared.RobotConstants;

import java.util.Arrays;

/**
 * Acts as the startup location for all of the robot
 */
public class MyBot extends TimedRobot {


    DriveInput cachedInput;
    IDriveController cachedDriveController;


    @Override
    public void robotInit() {

        System.out.println("ON");

        //TODO PRESET CODE PLEASE FIX
        //TODO if this were fully functional you'd have to register these since they are IProcesses
        ISwerveModule[] modules = new ISwerveModule[] {
                TalonSwerveModule.acquire(
                        new TalonFX(1),
                        new TalonFX(2),
                        new CANCoder(9),
                        Rotation2d.fromRadians(-232.55)
                ),
                TalonSwerveModule.acquire(
                        new TalonFX(7),
                        new TalonFX(8),
                        new CANCoder(12),
                        Rotation2d.fromRadians(-(331.96 - 180))
                ),
                TalonSwerveModule.acquire(
                        new TalonFX(5),
                        new TalonFX(6),
                        new CANCoder(11),
                        Rotation2d.fromRadians(-255.49)
                ),
                TalonSwerveModule.acquire(
                        new TalonFX(3),
                        new TalonFX(4),
                        new CANCoder(10),
                        Rotation2d.fromRadians(-(70.66 + 180))
                )

        };


        cachedDriveController = new RealDriveController(modules);
        cachedInput = new DriveInput(new Joystick(0));
    }

    @Override
    public void teleopPeriodic() {
        ChassisSpeeds desired = new ChassisSpeeds(cachedInput.getInputX(), cachedInput.getInputY(), cachedInput.getInputRot());
        SwerveModuleState[] states = RobotConstants.KINEMATICS.toSwerveModuleStates(desired);
        SwerveModuleState[] optimized = Arrays.stream(states).map(s -> SwerveModuleState.optimize(s, new Rotation2d())).toArray(SwerveModuleState[]::new);

        cachedDriveController.doDriveWithStates(optimized);
    }
}
