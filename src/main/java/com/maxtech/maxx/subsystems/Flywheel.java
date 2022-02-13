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
        // Slave the right motor to the left one.
        right.follow(left);

        statemachine.associateState(FlywheelStates.Idle, this::handleIdle);
        statemachine.associateState(FlywheelStates.SpinUp, this::handleSpinUp);
        statemachine.associateState(FlywheelStates.AtGoal, this::handleAtGoal);
    }

    // === STATES ===

    /** The states for the flywheel. */
    private enum FlywheelStates {
        Idle, SpinUp, AtGoal
    }

    private StateMachine<FlywheelStates> statemachine = new StateMachine<>(FlywheelStates.Idle);

    // === STATE ACTIONS ===

    private void handleIdle(Object o) {
    }

    private void handleSpinUp(Object o) {
        this.left.setVoltage(this.controller.computeNextVoltage(getCurrentVelocity()));
    }

    private void handleAtGoal(Object o) {
        this.left.setVoltage(controller.computeNextVoltage(getCurrentVelocity()));

        if (!isVelocityCorrect()) {
            statemachine.toState(FlywheelStates.SpinUp);
        }
    }

    // === MOTOR CONTROLLERS ===

    private final CANSparkMax left = new CANSparkMax(0, kBrushless);
    private final CANSparkMax right = new CANSparkMax(1, kBrushless);

    // === CONTROLLERS ===

    private SimpleFlywheelController controller = new SimpleFlywheelController(DCMotor.getFalcon500(2), 0.0023, 1);

    // === HELPER METHODS ===

    /** Get the current velocity that we are running at. */
    public double getCurrentVelocity() {
        return left.getEncoder().getVelocity();
    }

    public void setGoalVelocity(double rpm) {
        this.controller.reset(getCurrentVelocity());
        this.controller.setDesiredVelocity(rpm);
        this.statemachine.toState(FlywheelStates.SpinUp);
    }

    public void stop() {
        this.controller.reset(getCurrentVelocity());
        statemachine.toState(FlywheelStates.Idle);
    }

    public boolean isAtGoal() {
        return statemachine.currentState() == FlywheelStates.AtGoal;
    }

    public boolean isVelocityCorrect() {
        return controller.withinEpsilon(getCurrentVelocity());
    }
}
