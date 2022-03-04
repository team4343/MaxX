package com.maxtech.maxx.subsystems.intake;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import com.maxtech.maxx.Constants;
import com.maxtech.maxx.RobotContainer;
import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Intake extends Subsystem {
    StateMachine<IntakeState> statemachine = new StateMachine<>("Intake", IntakeState.Raised);
    private static final RobotLogger logger = RobotLogger.getInstance();
    private static Intake instance;
    private IntakeIO io;
    private boolean dumpDown = false;

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;
    }

    public enum IntakeState {
        Raised, Lowered, Dumping
    }

    private Intake() {
        switch(RobotContainer.teamNumber) {
            case 4343: io = new IntakeIOMax(); break;
            default: logger.err("Could not pick I/O, no matches."); break;
        }

        statemachine.associateState(IntakeState.Raised, this::handleRaising);
        statemachine.associateState(IntakeState.Lowered, this::handleLowering);
        statemachine.associateState(IntakeState.Dumping, this::handleDumping);
        statemachine.start();

        var tab = Shuffleboard.getTab("Intake");
    }

    /** We want to raise the intake. */
    private void handleRaising(StateMachineMeta m) {
        io.setPos(Constants.Intake.upPos);
        io.setWheels(0);
        // Does the indexer need a stop command?
       // indexer.stop();
    }

    /** We want to lower the intake. */
    private void handleLowering(StateMachineMeta m) {
        io.setPos(Constants.Intake.downPos);
        io.setWheels(Constants.Intake.wheelsInPercentOut);
    }

    /** We want to lower the intake. */
    private void handleDumping(StateMachineMeta m) {
        io.setPos(Constants.Intake.upPos);
        io.setWheels(-Constants.Intake.wheelsInPercentOut);
    }


    public void run() {
        statemachine.toState(IntakeState.Lowered);
    }

    public void runDump(boolean down) {
        this.dumpDown = down;
        statemachine.toState(IntakeState.Dumping);
    }

    public void stop() {
        statemachine.toState(IntakeState.Raised);
    }

    @Override
    public void sendTelemetry(String prefix) {

    }

    public IntakeState getState() {
        return statemachine.currentState();
    }
}