package com.maxtech.maxx.subsystems.flywheel;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import com.maxtech.maxx.Constants;
import com.maxtech.maxx.RobotContainer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Flywheel extends Subsystem {
    private static Flywheel instance;

    private FlywheelIO io;
    private final StateMachine<FlywheelStates> statemachine = new StateMachine<>("Flywheel", FlywheelStates.Idle);

    private static final RobotLogger logger = RobotLogger.getInstance();
    private double goal;

    public enum FlywheelStates {
        Idle, ShootHigh, ShootLow, SpinupHigh, SpinupLow
    }

    public static Flywheel getInstance() {
        if (instance == null) {
            instance = new Flywheel();
        }

        return instance;
    }

    private Flywheel() {
        switch (RobotContainer.teamNumber) {
            case 4343:
                io = new FlywheelIOMax();
                break;
            case 914:
                io = new FlywheelIOPeter();
                break;
            case -1:
                io = new FlywheelIOSim();
                break;
            default:
                logger.err("Could not pick I/O, no matches.");
                break;
        }

        var tab = Shuffleboard.getTab("Flywheel");
        tab.addString("state", statemachine::currentStateName);
        tab.addBoolean("at goal", this::atGoal);
        tab.addNumber("goal", this::getGoal);
        tab.addNumber("velocity", this::getVelocity);

        // Associate handlers for states.
        statemachine.associateState(FlywheelStates.Idle, this::handleIdle);
        statemachine.associateState(FlywheelStates.ShootHigh, this::handleShootHigh);
        statemachine.associateState(FlywheelStates.ShootLow, this::handleShootLow);
        statemachine.associateState(FlywheelStates.SpinupHigh, this::handleSpinupHigh);
        statemachine.associateState(FlywheelStates.SpinupLow, this::handleSpinupLow);
        statemachine.runCurrentHandler();
    }

    @Override
    public void sendTelemetry(String prefix) {
        SmartDashboard.putString(prefix + "state", statemachine.currentState().toString());
        SmartDashboard.putNumber(prefix + "speed", io.getVelocity());
        SmartDashboard.putNumber(prefix + "voltage", io.getVoltage());
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Shooter Speed",io.getVelocity());
        SmartDashboard.putNumber("Ready",getVelocity()/Constants.Flywheel.topBinRPM*100);
    }

    private void handleIdle(StateMachineMeta meta) {
        setVelocity(0.0);
    }

    private void handleShootHigh(StateMachineMeta meta) {
        if (io.getVelocity() < Constants.Flywheel.topBinRPM *  Constants.Flywheel.rpmThreshold) {
            statemachine.toState(FlywheelStates.SpinupHigh);
        }
        shoot(Constants.Flywheel.topBinRPM);
    }

    private void handleSpinupHigh(StateMachineMeta meta) {
        shoot(Constants.Flywheel.topBinRPM);
        if (io.getVelocity() > Constants.Flywheel.topBinRPM *  Constants.Flywheel.rpmThreshold) {
            statemachine.toState(FlywheelStates.ShootHigh);
        }
    }

    private void handleShootLow(StateMachineMeta meta) {
        if (io.getVelocity() < Constants.Flywheel.bottomBinRPM *  Constants.Flywheel.rpmThreshold) {
            statemachine.toState(FlywheelStates.SpinupLow);
        }
        shoot(Constants.Flywheel.bottomBinRPM);
    }

    private void handleSpinupLow(StateMachineMeta meta) {
        shoot(Constants.Flywheel.bottomBinRPM);
        if (io.getVelocity() > Constants.Flywheel.bottomBinRPM * Constants.Flywheel.rpmThreshold) {
            statemachine.toState(FlywheelStates.ShootLow);
        }
    }

    public void shoot(int rpm) {
        this.goal = rpm;
        setVelocity(rpm);
        logger.dbg("Velocity at %s", getVelocity());
    }

    private void setVelocity(double velocity) {
        io.setVelocity(velocity);
    }

    public double getVelocity() {
        return io.getVelocity();
    }

    public double getGoal() {
        return goal;
    }

    public boolean atGoal() {
        return Math.abs(getVelocity() - goal) < 5000;
    }

    public FlywheelStates getState() {
        return statemachine.currentState();
    }

    public void run(FlywheelStates state) {
        statemachine.toState(state);
    }

    public void stop() {
        statemachine.toState(FlywheelStates.Idle);
    }
}
