package com.maxtech.maxx.subsystems.intake;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import com.maxtech.maxx.Constants;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import static com.maxtech.maxx.RobotContainer.decideIO;

public class Intake extends Subsystem {
    private static Intake instance;

    private final IntakeIO io = decideIO(IntakeIOMax.class, IntakeIOMax.class);
    private final StateMachine<IntakeState> statemachine = new StateMachine<>("Intake", IntakeState.Raised);

    private static final RobotLogger logger = RobotLogger.getInstance();

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
        statemachine.associateState(IntakeState.Raised, this::handleRaising);
        statemachine.associateState(IntakeState.Lowered, this::handleLowering);
        statemachine.associateState(IntakeState.Dumping, this::handleDumping);
        statemachine.runCurrentHandler();

        var tab = Shuffleboard.getTab("Intake");
        tab.addString("state", statemachine::currentStateName);
    }

    /** We want to raise the intake. */
    private void handleRaising(StateMachineMeta m) {
        io.setPos(Constants.Intake.upPos);
        io.setWheels(0);
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

    public void runDump() {
        statemachine.toState(IntakeState.Dumping);
    }

    public void stop() {
        statemachine.toState(IntakeState.Raised);
    }

    public IntakeState getState() {
        return statemachine.currentState();
    }
}