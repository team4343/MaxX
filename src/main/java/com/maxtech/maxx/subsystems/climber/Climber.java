package com.maxtech.maxx.subsystems.climber;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import com.maxtech.maxx.Constants;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.maxtech.maxx.Constants.Climber.*;
import static com.maxtech.maxx.RobotContainer.decideIO;

public class Climber extends Subsystem {
    private static Climber instance;
    private final ClimberIO io = decideIO(ClimberIOMax.class, ClimberIOMax.class);
    private final StateMachine<State> statemachine = new StateMachine<>("Climber", State.Raising);

    /*
    This climb is interesting. The concept uses the winch to elevate the robot
    to height and then handoff the weight of the robot to the secondary arms.
    This handoff has the winch retract, raising the robot. The pivot hooks
    should be behind the bar and pivot forward into contact with the bar when
    the winch is fully retracted. When rotated forward the winch releases,
    transferring weight to the pivot hooks. The pivot will rotate slightly to
    allow the winch to extend fully underneath the next rung. When the winch is
    fully extended then the pivot will rotate so the winch is in contact with the
    bar. The winch will then retract fully allowing the pivot hooks to pivot back.
    As soon as there is movement on the pivot hooks away from the bar the winch
    extends enough to allow the pivot back under the current bar and the process
    restarts from baseline.

    Winch to 20%                           1 - Hanging
    Pivot Under                            1 - Hanging
    Winch to 0%                            2 - Handoff
    Pivot on                               2 - Handoff
    Winch to 50%                           3 - Nextbar
    Pivot robot to under next bar          3 - Nextbar
    Winch to 100%                          3 - Nextbar
    Pivot to contact next bar              3 - Nextbar
    Winch to 20%                           1 - Hanging
    Repeat...

    The Climb should use the manual process
     */

    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }
        return instance;
    }

    public enum State {
        Extend, Raising, Hanging, Handoff, HighBar, Traverse, Finish, Match
    }

    private Climber() {
        // Single bar climb
        statemachine.associateState(State.Extend, this::handleExtend);
        statemachine.associateState(State.Raising, this::handleRaising);

        // Multiple bar Climb
        statemachine.associateState(State.Hanging, this::handleHanging);
        statemachine.associateState(State.Handoff, this::handleHandoff);
        statemachine.associateState(State.HighBar, this::handleHighBar);
        statemachine.associateState(State.Traverse, this::handleTraverse);
        statemachine.associateState(State.Finish, this::handleFinish);
        statemachine.associateState(State.Match, this::handleMatch);
        statemachine.runCurrentHandler();

        var tab = Shuffleboard.getTab("Climber");
        tab.addString("state", statemachine::currentStateName);
        tab.addNumber("winch position", io::getWinchPos);
        tab.addNumber("pivot position", io::getPivotPos);
    }

    /** We want to raise the Climber. */
    private void handleRaising(StateMachineMeta m) {
        io.setWinchPos(winchDownPos);
    }

    /** We want to lower the Climber. */
    private void handleExtend(StateMachineMeta m) {
        io.setWinchPos(winchUpPos);
    }

    /** How the climber is stored during the match. **/
    private void handleMatch(StateMachineMeta m) {
        io.setWinchPos(winchDownPos);
        io.setPivotPos(pivotHangingPos);
    }

    /** Makes sure the pivot arms are on correct side of the bar **/
    private void handleHanging(StateMachineMeta m) {
        // Set winch to ~20% extended
        io.setWinchPos(winchHangingPos);

        // Check if winch is around 20% extended (room for pivot under bar).
        System.out.println(io.getWinchPos());
        if (threshold(winchHangingPos, io.getWinchPos(), winchHangingThreshold)) {
            // Set pivot hooks out of the way
            io.setPivotPos(pivotHangingPos);
            if (threshold(pivotHangingPos, io.getPivotPos(), pivotHangingThreshold))
                statemachine.toState(State.Handoff);
        }
    }

    /** Hands off the weight of the robot to the pivot arms **/
    private void handleHandoff(StateMachineMeta m) {
        // Lift enough for pivot hook to pass over bar
        io.setWinchPos(winchDownPos);

        // If fully retracted move the pivot to contact bar.
        if (threshold(winchDownPos, io.getWinchPos(), winchDownThreshold)) {
            io.setPivotPos(pivotHandoffPos); // Contact Bar
            System.out.println("Handoff - Pivot to bar.");
            // If contacting bar move to highbar transition
            if (threshold(pivotHandoffPos, io.getPivotPos(), pivotHandoffThreshold))
                statemachine.toState(State.HighBar);
        } else
            System.out.println("Handoff - Winch not down yet.");
    }

    private void handleHighBar(StateMachineMeta m) {
        double newPivot = io.getPivotPos();
        double newWinch = winchUpPos/2; // Winch to 50%

        // If winch extended halfway --- Pivot robot to under next bar.
        if (threshold(winchUpPos/2, io.getWinchPos(), winchUpThreshold*2)) {
            newPivot = pivotClearBarPos;
        }

        // If pivot past bar --- Winch to 100%
        if (threshold(pivotClearBarPos, io.getPivotPos(), pivotClearBarThreshold))
            newWinch = winchUpPos;

        // If winch at 100% --- Pivot to contact next bar.
        if (threshold(winchUpPos, io.getWinchPos(), winchUpThreshold)) {
            newPivot = pivotContactHighBarPos;
        }

        // If contacting high bar and winch at 100% --- Move to hanging position.
        if (threshold(pivotContactHighBarPos, io.getPivotPos(), pivotContactHighBarThreshold)
                && (threshold(winchUpPos, io.getWinchPos(), winchUpThreshold))) {
            statemachine.toState(State.Hanging);
        }

        io.setPivotPos(newPivot);
        io.setWinchPos(newWinch);
    }

    private void handleTraverse(StateMachineMeta m) {
        // Don't do anything for now.
        // We can potentially shorten our last climb by using double-sided hooks and not reaching all the way under.
        statemachine.toState(State.Finish);
    }

    private void handleFinish(StateMachineMeta m) {
        io.halt();
    }

    public void run(State state) {
        statemachine.toState(state);
    }

    public void halt() {
        io.halt();
    }

    /** Check if a given position is within a threshold of another
     * @param threshold 0 - 1.00 **/
    public boolean threshold(double desiredPos, double currentPos, double threshold) {
        if (threshold == 0) return false; // This prevents any directly matching positions at threshold=0.
        return Math.abs((desiredPos - currentPos)) / desiredPos <= threshold;
    }

    @Override
    public void periodic(){
        statemachine.runCurrentHandler();
        SmartDashboard.putString("Climber Status",statemachine.currentStateName());
    }
}
