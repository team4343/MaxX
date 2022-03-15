package com.maxtech.maxx.subsystems.climber;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import com.maxtech.maxx.Constants;
import com.maxtech.maxx.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.maxtech.maxx.RobotContainer.decideIO;

public class Climber extends Subsystem {
    private static Climber instance;

    private final ClimberIO io = decideIO(ClimberIOMax.class, ClimberIOMax.class);
    private final StateMachine<State> statemachine = new StateMachine<>("Climber", State.Raising);

    private static final RobotLogger logger = RobotLogger.getInstance();

    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }

        return instance;
    }

    public enum State {
        Extend, Raising
    }

    private Climber() {
        statemachine.associateState(State.Extend, this::handleExtend);
        statemachine.associateState(State.Raising, this::handleRaising);
        statemachine.runCurrentHandler();
    }

    /** We want to raise the Climber. */
    private void handleRaising(StateMachineMeta m) {
        if ( io.getPos() > Constants.Climber.downPos * 0.95 )
            io.setPos(Constants.Climber.downPos);
        if (io.getPos() < Constants.Climber.downPos + 10)
            Constants.Climber.pinned = true;
    }

    /** We want to lower the Climber. */
    private void handleExtend(StateMachineMeta m) {
        System.out.println(io.getPos());
        System.out.println(Constants.Climber.pinned);

        if (false) {
            io.setPos(Constants.Climber.releasePos);
            if (io.getPos() < 0) {
                Constants.Climber.pinned = false;
            }
        } else if ( io.getPos() < Constants.Climber.upPos * 0.95 )
            io.setPos(Constants.Climber.upPos);
    }

    private boolean getExtended() {
        return io.getPos() > Constants.Climber.upPos * 0.95;
    }

    private boolean getRaised() {
        return io.getPos() < Constants.Climber.upPos * 0.05;
    }

    public void run(State state) {
        statemachine.toState(state);
    }


    public void halt() {
        io.halt();
    }

    @Override
    public void periodic(){
        SmartDashboard.putString("Climber Status",statemachine.currentStateName());
    }
}
