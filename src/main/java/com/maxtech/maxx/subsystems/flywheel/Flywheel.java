package com.maxtech.maxx.subsystems.flywheel;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import com.maxtech.maxx.Constants;
import com.maxtech.maxx.RobotContainer;
import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Flywheel extends Subsystem {
    RobotLogger logger = RobotLogger.getInstance();

    private static Flywheel instance;
    private static Indexer indexer;

    public enum FlywheelStates {
        Idle, ShootHigh, ShootLow
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

        // Associate handlers for states.
        statemachine.associateState(FlywheelStates.Idle, this::handleIdle);
        statemachine.associateState(FlywheelStates.ShootHigh, this::handleShootHigh);
        statemachine.associateState(FlywheelStates.ShootLow, this::handleShootLow);
        statemachine.start();

        // Create the shuffleboard tab.
        var tab = Shuffleboard.getTab("Flywheel");
        tab.addString("IO", io::toString);
        tab.addString("state", statemachine::currentStateName);
        tab.addNumber("speed", io::getVelocity);
        tab.addNumber("voltage", io::getVoltage);

        indexer = Indexer.getInstance();

        logger.dbg("Created new FlyWheel Object");
    }

    @Override
    public void sendTelemetry(String prefix) {
        SmartDashboard.putString(prefix + "state", statemachine.currentState().toString());
        SmartDashboard.putNumber(prefix + "speed", io.getVelocity());
        SmartDashboard.putNumber(prefix + "voltage", io.getVoltage());
    }

    private final StateMachine<FlywheelStates> statemachine = new StateMachine<>("Flywheel", FlywheelStates.Idle);

    private void handleIdle(StateMachineMeta meta) {
        setVelocity(0.0);
        indexer.stop();
    }

    private void handleShootHigh(StateMachineMeta meta) {
        shoot(Constants.Flywheel.topBinRPM);
    }

    private void handleShootLow(StateMachineMeta meta) {
        shoot(Constants.Flywheel.bottomBinRPM);
    }

    private void shoot(int rpm) {
        setVelocity(rpm);
        logger.dbg("Velocity at %s", getVelocity());

        if (getVelocity() < rpm * Constants.Flywheel.rpmThreshold)
            indexer.run(true);
        else
            indexer.stop();
    }

    private FlywheelIO io;

    private void setVelocity(double velocity) {
        io.setVelocity(velocity);
    }

    public double getVelocity() {
        return io.getVelocity();
    }

    public FlywheelStates getState() {
        return statemachine.currentState();
    }


    public void run(FlywheelStates state) {
        statemachine.toState(state);
    }

    public void stop() {
        statemachine.toState(FlywheelStates.Idle);
        indexer.stop();
    }


}
