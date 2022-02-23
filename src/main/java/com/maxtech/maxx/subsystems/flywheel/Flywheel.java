package com.maxtech.maxx.subsystems.flywheel;

import com.maxtech.lib.controllers.SimpleFlywheelController;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import com.maxtech.maxx.RobotContainer;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Flywheel extends SubsystemBase {
    RobotLogger logger = RobotLogger.getInstance();

    private static Flywheel instance;

    public static Flywheel getInstance() {
        if (instance == null) {
            instance = new Flywheel();
        }

        return instance;
    }

    private Flywheel() {
        switch(RobotContainer.teamNumber) {
            case 4343: io = new FlywheelIOMax(); break;
            case 914: io = new FlywheelIOPeter(); break;
            default: logger.err("Could not pick I/O, no matches."); break;
        }

        // Associate handlers for states.
        statemachine.associateState(FlywheelStates.Idle, this::handleIdle);
        statemachine.associateState(FlywheelStates.SpinUp, this::handleSpinUp);
        statemachine.associateState(FlywheelStates.AtGoal, this::handleAtGoal);

        statemachine.start();
    }

    /** The states for the flywheel. */
    private enum FlywheelStates {
        Idle, SpinUp, AtGoal,
    }

    private StateMachine<FlywheelStates> statemachine = new StateMachine<>("Flywheel", FlywheelStates.Idle);

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

    private FlywheelIO io;

    private SimpleFlywheelController controller = new SimpleFlywheelController(DCMotor.getFalcon500(2), 0.0023, 1);

    private double getVelocity() {
        return io.getVelocity();
    }

    private void setVoltage(double voltage) {
        io.setVoltage(voltage);
    }

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
