package org.bitbuckets.bootstrap;

import edu.wpi.first.wpilibj.RobotBase;

public class Main {
    public static void main(String[] args) {

        boolean isSim = RobotBase.isSimulation(); //TODO feed into robot!
        RobotBase.startRobot(MyBot::new);
    }
}