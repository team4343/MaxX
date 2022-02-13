package com.maxtech.lib.controllers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.LinearQuadraticRegulator;
import edu.wpi.first.math.estimator.KalmanFilter;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.LinearSystemLoop;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;

public class SimpleFlywheelController {
    private final LinearSystem<N1, N1, N1> m_plant;
    private final KalmanFilter<N1, N1, N1> m_filter;
    private final LinearQuadraticRegulator<N1, N1, N1> m_regulator;
    private final LinearSystemLoop<N1, N1, N1> m_loop;

    double lastTimeSeconds = 0;

    public SimpleFlywheelController(DCMotor motor, double momentOfInertia, double gearing, double threadSpeed) {
        m_plant = LinearSystemId.createFlywheelSystem(motor, momentOfInertia, gearing);
        m_filter = new KalmanFilter<>(Nat.N1(), Nat.N1(), m_plant, VecBuilder.fill(3.0), VecBuilder.fill(0.01), 0.020);
        m_regulator = new LinearQuadraticRegulator<>(m_plant, VecBuilder.fill(8), VecBuilder.fill(12), 12);
        m_loop = new LinearSystemLoop<>(m_plant, m_regulator, m_filter, 12, threadSpeed);
    }

    /** Construct {@link this} with a default thread speed. */
    public SimpleFlywheelController(DCMotor motor, double momentOfInertia, double gearing) {
        this(motor, momentOfInertia, gearing, 0.020);
    }

    public double computeNextVoltage(double currentVelocity) {
        double currentTimeSeconds = Timer.getFPGATimestamp();
        double dt = currentTimeSeconds - lastTimeSeconds;
        lastTimeSeconds = currentTimeSeconds;

        return computeNextVoltage(currentVelocity, dt);
    }

    public double computeNextVoltage(double currentVelocity, double dt) {
        double currentRADS = Units.rotationsPerMinuteToRadiansPerSecond(currentVelocity);
        m_loop.correct(VecBuilder.fill(currentRADS));
        m_loop.predict(dt);

        return m_loop.getU(0);
    }

    public void reset(double currentRPM) {
        m_loop.reset(VecBuilder.fill(Units.rotationsPerMinuteToRadiansPerSecond(currentRPM)));
    }

    public void setDesiredVelocity(double rpm) {
        double rads = Units.rotationsPerMinuteToRadiansPerSecond(rpm);
        m_loop.setNextR(VecBuilder.fill(rads));
    }

    // Please check that this is accurate...
    public boolean withinEpsilon(double currentVelocity) {
        return Units.rotationsPerMinuteToRadiansPerSecond(currentVelocity) == m_loop.getNextR(0);
    }
}
