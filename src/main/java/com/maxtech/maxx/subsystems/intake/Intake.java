package com.maxtech.maxx.subsystems.intake;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import com.maxtech.maxx.RobotContainer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Intake extends Subsystem {
    private static final RobotLogger logger = RobotLogger.getInstance();
    private static Intake instance;

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;
    }

    private enum IntakeState {
        Raising, Raised, Lowering, Lowered,
    }

    StateMachine<IntakeState> statemachine = new StateMachine<>("Intake", IntakeState.Raised);

    private Intake() {
        switch(RobotContainer.teamNumber) {
            case 4343: io = new IntakeIOMax(); break;
            default: logger.err("Could not pick I/O, no matches."); break;
        }

        statemachine.associateState(IntakeState.Raised, this::handleRaising);
        statemachine.associateState(IntakeState.Lowering, this::handleLowering);
        statemachine.start();

        var tab = Shuffleboard.getTab("Intake");
    }

    private IntakeIO io;

    /** We want to raise the intake. */
    private void handleRaising(StateMachineMeta m) {
    }

    /** We want to lower the intake. */
    private void handleLowering(StateMachineMeta m) {
    }

    @Override
    public void sendTelemetry(String prefix) {
    }
}