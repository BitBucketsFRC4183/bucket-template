package org.bitbuckets.lib;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import org.bitbuckets.lib.motor.FXMotorFactory;
import org.bitbuckets.lib.motor.IMotorFactory;
import org.bitbuckets.lib.network.INetworking;

public interface Tools {

    Tools child(String type);

    IMotorFactory talonFXFactory();


}
