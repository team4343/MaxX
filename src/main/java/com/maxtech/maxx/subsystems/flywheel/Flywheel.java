package com.maxtech.maxx.subsystems.flywheel;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.controllers.SimpleFlywheelController;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import com.maxtech.maxx.RobotContainer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.maxtech.maxx.Constants.Flywheel.kA;
import static com.maxtech.maxx.Constants.Flywheel.kV;

public class Flywheel extends Subsystem {
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

    @Override
    public void sendTelemetry(String prefix) {
        SmartDashboard.putString(prefix + "state", statemachine.currentState().toString());
        SmartDashboard.putNumber(prefix + "speed", io.getVelocity());
        SmartDashboard.putBoolean(prefix + "atGoal", isAtGoal());
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
        setVoltage(this.controller.computeNextVoltage(getVelocity()));

        if (isVelocityCorrect()) {
            statemachine.toState(FlywheelStates.AtGoal);
        }
    }

    private void handleAtGoal(StateMachineMeta meta) {
        setVoltage(controller.computeNextVoltage(getVelocity()));

        if (!isVelocityCorrect()) {
            statemachine.toState(FlywheelStates.SpinUp);
        }
    }

    private FlywheelIO io;

    private final SimpleFlywheelController controller = new SimpleFlywheelController(kV, kA);

    private void setVoltage(double voltage) {
        io.setVoltage(voltage);
    }

    public double getVelocity() {
        return io.getVelocity();
    }

    public void setGoalVelocity(double rpm) {
        this.controller.reset(getVelocity());
        this.controller.setDesiredVelocity(rpm);
        this.statemachine.toState(FlywheelStates.SpinUp);
    }

    public void stop() {
        this.controller.reset(getVelocity());
        statemachine.toState(FlywheelStates.Idle);
    }

    public boolean isAtGoal() {
        return statemachine.currentState() == FlywheelStates.AtGoal;
    }

    public boolean isVelocityCorrect() {
        return controller.withinEpsilon(getVelocity());
    }
}
