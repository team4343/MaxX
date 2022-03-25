package com.maxtech.lib.controllers;

import com.maxtech.lib.logging.RobotLogger;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.LinearQuadraticRegulator;
import edu.wpi.first.math.estimator.KalmanFilter;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.LinearSystemLoop;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;

public class SimpleFlywheelController {
    private final RobotLogger logger = RobotLogger.getInstance();

    private final LinearSystem<N1, N1, N1> plant;
    private final KalmanFilter<N1, N1, N1> filter;
    private final LinearQuadraticRegulator<N1, N1, N1> regulator;
    private final LinearSystemLoop<N1, N1, N1> loop;

    double lastTimeSeconds = 0;

    public SimpleFlywheelController(double kV, double kA, double threadSpeed) {
        plant = LinearSystemId.identifyVelocitySystem(kV, kA);
        filter = new KalmanFilter<>(Nat.N1(), Nat.N1(), plant, VecBuilder.fill(3.), VecBuilder.fill(10), 0.02);
        regulator = new LinearQuadraticRegulator<>(plant, VecBuilder.fill(8.0), VecBuilder.fill(12.0), 0.02);
        loop = new LinearSystemLoop<>(plant, regulator, filter, 11, threadSpeed);
    }

    /** Construct {@link this} with a default thread speed. */
    public SimpleFlywheelController(double kV, double kA) {
        this(kV, kA, 0.020);
    }

    public double computeNextVoltage(double currentVelocity) {
        double currentTimeSeconds = Timer.getFPGATimestamp();
        double dt = currentTimeSeconds - lastTimeSeconds;
        lastTimeSeconds = currentTimeSeconds;

        return computeNextVoltage(currentVelocity, dt);
    }

    public double computeNextVoltage(double currentVelocity, double dt) {
        double currentRADS = Units.rotationsPerMinuteToRadiansPerSecond(currentVelocity);
        loop.correct(VecBuilder.fill(currentRADS));
        loop.predict(dt);

        return loop.getU(0);
    }

    public void reset(double currentRPM) {
        loop.reset(VecBuilder.fill(Units.rotationsPerMinuteToRadiansPerSecond(currentRPM)));
    }

    public void setDesiredVelocity(double rpm) {
        double rads = Units.rotationsPerMinuteToRadiansPerSecond(rpm);
        loop.setNextR(VecBuilder.fill(rads));
    }

    public double getDesiredVelocity() {
        return Units.radiansPerSecondToRotationsPerMinute(loop.getNextR(0));
    }

    public boolean withinEpsilon(double currentVelocity) {
        return withinEpsilon(currentVelocity, 500);
    }

    public boolean withinEpsilon(double currentVelocity, double epsilon) {
        double v = getDesiredVelocity();

        if (currentVelocity == v) return true;
        return Math.abs(currentVelocity - v) <= epsilon;
    }
}
