package com.maxtech.maxx;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * A WPILib-specified entry point for robot code.
 */
public final class Main {
    private Main() {
    }

    /**
     * Main initialization method. Do not perform any initialization here.
     * <p>
     * If you change your main Robot class (name), change the parameter type.
     *
     * @param args arguments passed onto this program from invocation.
     */
    public static void main(String... args) {
        RobotBase.startRobot(Robot::new);
    }
}
