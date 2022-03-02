package com.maxtech.maxx.subsystems.indexer;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.Constants;
import com.maxtech.maxx.RobotContainer;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * A drivetrain subsystem for Max X.
 */
public class Indexer extends Subsystem {
    RobotLogger logger = RobotLogger.getInstance();
    private static Indexer instance;
    private IndexerIO io;

    public static Indexer getInstance() {
        if (instance == null)
            instance = new Indexer();
        return instance;
    }

    private Indexer() {
        switch (RobotContainer.teamNumber) {
            case 4343:
                io = new IndexerIOMax();
                break;
            case 914:
                io = new IndexerIOPeter();
                break;
            default:
                logger.err("Could not pick I/O, no matches.");
                break;
        }

        statemachine.associateState(IndexerStates.Idle, this::handleIdle);
        statemachine.associateState(IndexerStates.OneLoaded, this::handleOneLoaded);
        statemachine.associateState(IndexerStates.TwoLoaded, this::handleTwoLoaded);
        statemachine.associateState(IndexerStates.Shooting, this::handleShooting);

        var tab = Shuffleboard.getTab("Indexer");
        tab.addBoolean("top sensor", this::isTopEmpty);
        tab.addBoolean("bottom sensor", this::isBottomEmpty);
    }

    private enum IndexerStates {
        Idle, OneLoaded, TwoLoaded, Shooting,
    }

    private final StateMachine<IndexerStates> statemachine = new StateMachine<>("Indexer", IndexerStates.Idle);

    @Override
    public void sendTelemetry(String prefix) {}

    /**
     * If we are in this state, there is really nothing to do. Just spin the bottom motor to handle balls, and stop the
     * top one. We also listen for balls incoming, and send them to the top. If we do this, we also move to the
     * OneLoaded state.
     */
    private void handleIdle(StateMachineMeta m) {
        io.set(Constants.Indexer.maxOutput, 0.);

        // If we detect something in the bottom spot, and the top spot is empty, move it up. We do this by turning on
        // both motors. Once the top is loaded, we turn off the top one.
        if (isBottomLoaded() && isTopEmpty()) {
            io.set(0.5, 0.5);
            while (isTopLoaded()) {
                Timer.delay(0.02);
            }

            // If we're here, then the top is loaded. Stop spinning the top, and move to the correct state. We don't do
            // anything to the bottom motor, so presumably, it will continue spinning at the same speed.
            io.set(null, 0.);
            statemachine.toState(IndexerStates.OneLoaded);
        }
    }

    /**
     * If we are in this state, there is one ball **at the top** of the indexer. Now, we're just waiting for a second,
     * bottom ball.
     */
    private void handleOneLoaded(StateMachineMeta m) {
        io.set(0.5, 0.);

        if (isBottomLoaded()) {
            // If we see something at the bottom, just wait until we don't see it anymore, then stop.
            while (isBottomLoaded()) {
                Timer.delay(0.02);
            }

            // If we are here, then the ball is now unloaded. Stop the motors and move to the TwoLoaded state.
            io.set(0., 0.);
            statemachine.toState(IndexerStates.TwoLoaded);
        }
    }

    private void handleTwoLoaded(StateMachineMeta m) {

    }

    private void handleShooting(StateMachineMeta m) {

    }

    public IndexerSensors getSensors() {
        return io.get();
    }

    public boolean isTopEmpty() {
        return getSensors().top;
    }

    public boolean isBottomEmpty() {
        return getSensors().bottom;
    }

    public boolean isTopLoaded() {
        return !isTopEmpty();
    }

    public boolean isBottomLoaded() {
        return !isBottomEmpty();
    }

    public void dump() {
        this.stop();
        io.set(-Constants.Indexer.maxOutput, -Constants.Indexer.maxOutput);
        statemachine.toState(IndexerStates.Idle);
    }

    public void stop() {
        io.set(0., 0.);
    }
}
