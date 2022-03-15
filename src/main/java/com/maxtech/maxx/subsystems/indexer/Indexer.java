package com.maxtech.maxx.subsystems.indexer;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import static com.maxtech.maxx.Constants.Indexer.maxOutput;
import static com.maxtech.maxx.RobotContainer.decideIO;

/**
 * A drivetrain subsystem for Max X.
 */
public class Indexer extends Subsystem {
    private static Indexer instance;

    private final IndexerIO io = decideIO(IndexerIOMax.class, IndexerIOPeter.class);
    private final StateMachine<State> statemachine = new StateMachine<>("Indexer", State.Unloaded);

    private static final RobotLogger logger = RobotLogger.getInstance();
    private State previousOnState = State.Unloaded;

    public static Indexer getInstance() {
        if (instance == null) {
            instance = new Indexer();
        }
        return instance;
    }

    private Indexer() {
        var tab = Shuffleboard.getTab("Indexer");
        tab.addBoolean("top", this::isTopLoaded);
        tab.addBoolean("bottom", this::isBottomLoaded);
        tab.addString("state", statemachine::currentStateName);

        statemachine.associateState(State.Off, this::handleOff);
        statemachine.associateState(State.Unloaded, this::handleUnloaded);
        statemachine.associateState(State.OneLoaded, this::handleOneLoaded);
        statemachine.associateState(State.TwoLoaded, this::handleTwoLoaded);
        statemachine.associateState(State.Shooting, this::handleShooting);
    }

    private void handleOff(StateMachineMeta m) {
        // Don't do anything!
        io.set(0, 0);
    }

    private void handleUnloaded(StateMachineMeta m) {
        previousOnState = State.Unloaded;

        // Listen for a ball to come in and direct it to the top position.
        io.set(0, maxOutput);

        if (isBottomLoaded()) {
            while (isTopEmpty()) {
                io.set(maxOutput, maxOutput);
            }

            statemachine.toState(State.OneLoaded);
        }
    }

    private void handleOneLoaded(StateMachineMeta m) {
        previousOnState = State.OneLoaded;

        // Listen for a ball to come in and direct it to the bottom position.
        io.set(0, maxOutput);

        if (isBottomLoaded()) {
            statemachine.toState(State.TwoLoaded);
        }
    }

    private void handleTwoLoaded(StateMachineMeta m) {
        previousOnState = State.TwoLoaded;

        // Chilling
        io.set(0, 0);
    }

    private void handleShooting(StateMachineMeta m) {
        io.set(maxOutput, maxOutput);
    }

    private enum State {
        Off, Unloaded, OneLoaded, TwoLoaded, Shooting,
    }

    public void run() {
        statemachine.runCurrentHandler();
    }

    public void reset() {
        statemachine.toState(State.Unloaded);
    }

    public void turnOff() {
        statemachine.toState(State.Off);
    }

    public void turnOn() {
        statemachine.toState(previousOnState);
    }

    public void shoot() {
        statemachine.toState(State.Shooting);
    }

    public void stopShoot() {
        statemachine.toState(previousOnState);
    }

    private IndexerSensors getSensors() {
        return io.getSensors();
    }

    private boolean isTopEmpty() {
        return getSensors().top;
    }

    private boolean isBottomEmpty() {
        return getSensors().bottom;
    }

    private boolean isTopLoaded() {
        return !isTopEmpty();
    }

    private boolean isBottomLoaded() {
        return !isBottomEmpty();
    }
}
