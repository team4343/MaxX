package com.maxtech.maxx.subsystems.intake;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import com.maxtech.maxx.Constants;
import com.maxtech.maxx.RobotContainer;
import com.maxtech.maxx.subsystems.indexer.Indexer;
import com.maxtech.maxx.subsystems.indexer.IndexerIO;
import com.maxtech.maxx.subsystems.indexer.IndexerIOMax;
import com.maxtech.maxx.subsystems.indexer.IndexerIOPeter;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Intake extends Subsystem {
    StateMachine<IntakeState> statemachine = new StateMachine<>("Intake", IntakeState.Raised);
    private static final RobotLogger logger = RobotLogger.getInstance();
    private static Intake instance;
    private static Indexer indexer;
    private IntakeIO io;

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;
    }

    private enum IntakeState {
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
        indexer = Indexer.getInstance();
    }

    /** We want to raise the intake. */
    private void handleRaising(StateMachineMeta m) {
        io.setPos(Constants.Intake.upPos);
        io.setWheels(0);
        // Does the indexer need a stop command?
        indexer.stop();
    }

    /** We want to lower the intake. */
    private void handleLowering(StateMachineMeta m) {
        io.setPos(Constants.Intake.downPos);
        io.setWheels(Constants.Intake.wheelsInPercentOut);
        indexer.run(false);
    }

    /** We want to lower the intake. */
    private void handleDumping(StateMachineMeta m) {
        io.setPos(Constants.Intake.downPos);
        io.setWheels(-Constants.Intake.wheelsInPercentOut);
        indexer.dump();
    }


    public void run() {
        statemachine.toState(IntakeState.Lowered);
    }

    public void runDump(boolean dump) {
        statemachine.toState(IntakeState.Dumping);
    }

    public void stop() {
        statemachine.toState(IntakeState.Raised);
    }

    @Override
    public void sendTelemetry(String prefix) {

    }
}