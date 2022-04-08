package com.maxtech.lib.controllers;

import com.maxtech.lib.logging.RobotLogger;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.LinearQuadraticRegulator;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.KalmanFilter;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.LinearSystemLoop;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;

public class PIDFlywheelController {
    private final RobotLogger logger = RobotLogger.getInstance();

    private final PIDController controller;

    private double desired = 0;
    double lastTimeSeconds = 0;

    public PIDFlywheelController(double kV, double kA, double threadSpeed) {
        controller = new PIDController(.2, 0, 0);
        controller.setTolerance(100);
    }

    /** Construct {@link this} with a default thread speed. */
    public PIDFlywheelController(double kV, double kA) {
        this(kV, kA, 0.020);
    }

    public double computeNextVoltage(double currentVelocity) {
        return controller.calculate(currentVelocity, lastTimeSeconds);
    }

    public void setDesiredVelocity(double rpm) {
        desired = rpm;
        controller.setSetpoint(rpm);
    }

    public double getDesiredVelocity() {
        return desired;
    }

    public boolean withinEpsilon(double currentVelocity) {
        return controller.atSetpoint();
    }
}
