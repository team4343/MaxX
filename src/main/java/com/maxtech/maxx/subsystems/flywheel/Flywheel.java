package com.maxtech.maxx.subsystems.flywheel;

import com.maxtech.lib.controllers.SimpleFlywheelController;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        // Create the I/O based on a SendableChooser.
        io.setDefaultOption("Max", new FlywheelIOMax());
        io.setDefaultOption("Peter", new FlywheelIOPeter());
        io.addOption("Simulation", new FlywheelIOSim());

        SmartDashboard.putData("Flywheel chooser", io);

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
        setVoltage(0);
    }

    private void handleSpinUp(StateMachineMeta meta) {
        setVoltage(this.controller.computeNextVoltage(getCurrentVelocity()));

        if (isVelocityCorrect()) {
            statemachine.toState(FlywheelStates.AtGoal);
        }
    }

    private void handleAtGoal(StateMachineMeta meta) {
        setVoltage(controller.computeNextVoltage(getCurrentVelocity()));

        if (!isVelocityCorrect()) {
            statemachine.toState(FlywheelStates.SpinUp);
        }
    }

    // === I/O ===
    private SendableChooser<FlywheelIO> io = new SendableChooser<>();

    // === CONTROLLERS ===

    private SimpleFlywheelController controller = new SimpleFlywheelController(DCMotor.getFalcon500(2), 0.0023, 1);

    // === HELPER METHODS ===

    private double getVelocity() {
        return io.getSelected().getVelocity();
    }

    private void setVoltage(double voltage) {
        io.getSelected().setVoltage(voltage);
    }

    // === PUBLIC METHODS ===

    /** Get the current velocity that we are running at. */
    public double getCurrentVelocity() {
        return getVelocity();
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
