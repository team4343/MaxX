package com.maxtech.maxx.subsystems.flywheel;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.controllers.SimpleFlywheelController;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import com.maxtech.maxx.RobotContainer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
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
            case -1: io = new FlywheelIOSim(); break;
            default: logger.err("Could not pick I/O, no matches."); break;
        }

        // Associate handlers for states.
        statemachine.associateState(FlywheelStates.Idle, this::handleIdle);
        statemachine.associateState(FlywheelStates.SpinUp, this::handleSpinUp);
        statemachine.associateState(FlywheelStates.AtGoal, this::handleAtGoal);
        statemachine.start();

        // Create the shuffleboard tab.
        var tab = Shuffleboard.getTab("Flywheel");
        tab.addString("IO", io::toString);
        tab.addString("state", statemachine::currentStateName);
        tab.addNumber("speed", io::getVelocity);
        tab.addNumber("voltage", io::getVoltage);
        tab.addNumber("desired", controller::getDesiredVelocity);
        tab.addBoolean("atGoal", this::isAtGoal);
    }

    @Override
    public void sendTelemetry(String prefix) {
        SmartDashboard.putString(prefix + "state", statemachine.currentState().toString());
        SmartDashboard.putNumber(prefix + "speed", io.getVelocity());
        SmartDashboard.putNumber(prefix + "voltage", io.getVoltage());
        SmartDashboard.putBoolean(prefix + "atGoal", isAtGoal());
    }

    /** The states for the flywheel. */
    private enum FlywheelStates {
        Idle, SpinUp, AtGoal,
    }

    private final StateMachine<FlywheelStates> statemachine = new StateMachine<>("Flywheel", FlywheelStates.Idle);

    private void handleIdle(StateMachineMeta meta) {
    }

    private void handleSpinUp(StateMachineMeta meta) {
        setVoltage(controller.computeNextVoltage(getVelocity()));
        logger.dbg("Velocity at %s", getVelocity());

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

    public void run() {
        statemachine.toState(FlywheelStates.SpinUp);
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
