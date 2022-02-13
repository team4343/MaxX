package com.maxtech.maxx.subsystems;

import com.maxtech.lib.controllers.SimpleFlywheelController;
import com.maxtech.lib.statemachines.StateMachine;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;

public class Flywheel extends SubsystemBase {

    // === INSTANCES ===

    private static Flywheel instance;

    public static Flywheel getInstance() {
        if (instance == null) {
            instance = new Flywheel();
        }

        return instance;
    }

    private Flywheel() {
        statemachine.associateState(FlywheelStates.kIdle, this::handleIdle);
        statemachine.associateState(FlywheelStates.kSpinUp, this::handleSpinUp);
        statemachine.associateState(FlywheelStates.kAtGoal, this::handleAtGoal);
    }

    // === STATES ===

    /** The states for the flywheel. */
    private enum FlywheelStates {
        kIdle, kSpinUp, kAtGoal
    }

    private StateMachine<FlywheelStates> statemachine;

    // === STATE ACTIONS ===

    private void handleIdle(Object o) {
    }

    private void handleSpinUp(Object o) {
        this.left.setVoltage(this.controller.computeNextVoltage(getCurrentVelocity()));
    }

    private void handleAtGoal(Object o) {
        this.left.setVoltage(controller.computeNextVoltage(getCurrentVelocity()));

        if (!isVelocityCorrect()) {
            statemachine.toState(FlywheelStates.kSpinUp);
        }
    }

    // === MOTOR CONTROLLERS ===

    private CANSparkMax left = new CANSparkMax(0, kBrushless);
    private CANSparkMax right = new CANSparkMax(1, kBrushless);

    // === CONTROLLERS ===

    private SimpleFlywheelController controller = new SimpleFlywheelController(DCMotor.getNEO(2), 0.0023, 1);

    // === HELPER METHODS ===

    /** Get the current velocity that we are running at. */
    public double getCurrentVelocity() {
        return left.getEncoder().getVelocity();
    }

    public void setGoalVelocity(double rpm) {
        this.controller.reset(getCurrentVelocity());
        this.controller.setDesiredVelocity(rpm);
        this.statemachine.toState(FlywheelStates.kSpinUp);
    }

    public void stop() {
        this.controller.reset(getCurrentVelocity());
        statemachine.toState(FlywheelStates.kIdle);
    }

    public boolean isAtGoal() {
        return statemachine.currentState() == FlywheelStates.kAtGoal;
    }

    public boolean isVelocityCorrect() {
        return controller.withinEpsilon(getCurrentVelocity());
    }
}
