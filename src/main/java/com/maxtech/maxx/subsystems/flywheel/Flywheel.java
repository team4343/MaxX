package com.maxtech.maxx.subsystems.flywheel;

import com.maxtech.lib.controllers.SimpleFlywheelController;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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
        // Create the I/O.
        io = new FlywheelIOSim();

        // Associate handlers for states.
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

    private void handleIdle(StateMachineMeta meta) {
        // Force set the voltage to zero.
        io.setVoltage(0);
    }

    private void handleSpinUp(StateMachineMeta meta) {
        io.setVoltage(this.controller.computeNextVoltage(getCurrentVelocity()));

        if (isVelocityCorrect()) {
            statemachine.toState(FlywheelStates.AtGoal);
        }
    }

    private void handleAtGoal(StateMachineMeta meta) {
        io.setVoltage(controller.computeNextVoltage(getCurrentVelocity()));

        if (!isVelocityCorrect()) {
            statemachine.toState(FlywheelStates.SpinUp);
        }
    }

    // === I/O ===
    private FlywheelIO io;

    // === CONTROLLERS ===

    private SimpleFlywheelController controller = new SimpleFlywheelController(DCMotor.getFalcon500(2), 0.0023, 1);

    // === HELPER METHODS ===

    /** Get the current velocity that we are running at. */
    public double getCurrentVelocity() {
        return io.getVelocity();
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
