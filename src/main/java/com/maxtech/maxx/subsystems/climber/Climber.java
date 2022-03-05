package com.maxtech.maxx.subsystems.climber;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import com.maxtech.maxx.Constants;
import com.maxtech.maxx.RobotContainer;
import com.maxtech.maxx.subsystems.indexer.Indexer;
import com.maxtech.maxx.subsystems.climber.Climber;
import com.maxtech.maxx.subsystems.climber.ClimberIO;
import com.maxtech.maxx.subsystems.climber.ClimberIOMax;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber extends Subsystem {
    StateMachine<Climber.ClimberState> statemachine = new StateMachine<>("Climber", Climber.ClimberState.Raising);
    private static final RobotLogger logger = RobotLogger.getInstance();
    private static Climber instance;
    private ClimberIO io;

    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }

        return instance;
    }

    public enum ClimberState {
        Extend, Raising
    }

    private Climber() {
        switch(RobotContainer.teamNumber) {
            case 4343: io = new ClimberIOMax(); break;
            default: logger.err("Could not pick I/O, no matches."); break;
        }

        statemachine.associateState(Climber.ClimberState.Extend, this::handleExtend);
        statemachine.associateState(Climber.ClimberState.Raising, this::handleRaising);
        statemachine.start();
    }

    /** We want to raise the Climber. */
    private void handleRaising(StateMachineMeta m) {
        if ( io.getPos() > Constants.Climber.downPos * 0.95 )
            io.setPos(Constants.Climber.downPos);
    }

    /** We want to lower the Climber. */
    private void handleExtend(StateMachineMeta m) {
        if ( io.getPos() < Constants.Climber.upPos * 0.95 )
            io.setPos(Constants.Climber.upPos);
    }

    private boolean getExtended() {
        return io.getPos() > Constants.Climber.upPos * 0.95;
    }

    private boolean getRaised() {
        return io.getPos() < Constants.Climber.upPos * 0.05;
    }

    public void run(ClimberState state) {
        statemachine.toState(state);
    }


    public void halt() {
        io.halt();
    }

    @Override
    public void periodic(){
        SmartDashboard.putString("Climber Status",statemachine.currentStateName());
    }

    @Override
    public void sendTelemetry(String prefix) {

    }
}
