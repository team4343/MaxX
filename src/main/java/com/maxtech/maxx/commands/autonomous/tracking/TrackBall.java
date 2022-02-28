package com.maxtech.maxx.commands.autonomous.tracking;

import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import com.maxtech.maxx.commands.flywheel.SetFlywheel;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.ArrayList;
import java.util.Collections;

public class TrackBall extends CommandBase {
    RobotLogger logger = RobotLogger.getInstance();

    private ArrayList<Double> distances = new ArrayList<>();
    private ArrayList<Double> rotations = new ArrayList<>();

    private double optimalDistance = 0;
    private double optimalRotation = 0;

    private enum TrackingStates {
        Searching, Moving, Collecting, Shooting,
    }

    StateMachine<TrackingStates> statemachine = new StateMachine<>("TrackingStates", TrackingStates.Searching);

    public TrackBall() {
        statemachine.associateState(TrackingStates.Searching, this::handleSearching);
        statemachine.associateState(TrackingStates.Moving, this::handleMoving);
        statemachine.associateState(TrackingStates.Collecting, this::handleCollecting);
        statemachine.associateState(TrackingStates.Shooting, this::handleShooting);
    }

    /**
     * If we are in this state, then we are looking for a ball, and have space in the indexer to keep it. This method is
     * basically a call to the Limelight, trying to find an average amount of points that are also within the same
     * space. If we are successful, we move to {@link TrackingStates.Moving}.
     * */
    private void handleSearching(StateMachineMeta m) {
        logger.dbg("Searching...");

        // Get the currently tracked rotation and distance.
        double rotation = 0;
        double distance = 100;

        // Add it to our list of rotations and distances.
        distances.add(distance);
        rotations.add(rotation);

        logger.log("distances: %s", distances.size() >= 10);

        /*
         Let's do some comparison. If the length of the ArrayList<> if more than 10, we have enough data to compare.
         If not, then we do nothing and continue. This method will be called again, and the sequence will be re-done
         in a new frame.
         */
        if (distances.size() >= 10) {
            // If we're here, then we have enough data to compare. Let's see if they are all within an epsilon value.
            double distancesMax = Collections.max(distances);
            double distancesMin = Collections.min(distances);
            boolean distancesWithin = Math.abs(distancesMax - distancesMin) < 10;

            // We must do the same with the rotations.
            double rotationsMax = Collections.max(rotations);
            double rotationsMin = Collections.min(rotations);
            boolean rotationsWithin = Math.abs(rotationsMax - rotationsMin) < 10;

            if (distancesWithin && rotationsWithin) {
                // We have a valid target. Now, let's find the average, and drive towards it. This is the only point in
                // this state where we move to another state.
                optimalDistance = distances.stream()
                        .mapToDouble(d -> d)
                        .average()
                        .orElse(0.0);

                optimalRotation = rotations.stream()
                        .mapToDouble(d -> d)
                        .average()
                        .orElse(0.0);

                statemachine.toState(TrackingStates.Moving);
            } else {
                // We don't have a very certain target. Clear the arrays and try to lock on again.
                distances.clear();
                rotations.clear();
            }
        }
    }

    /**
     * If we are in this state, we want to smoothly move from our current point to another. This method is basically a
     * call to WPILib's path planning, giving it a position.
     * */
    private void handleMoving(StateMachineMeta m) {
        new MoveTowards(optimalRotation, optimalDistance).execute();
    }

    /** If we are in this state, we are positioned and just need to turn the intake on. */
    private void handleCollecting(StateMachineMeta m) {
        // TODO: replace this with a ShootLow() command
        new SetFlywheel(100).execute();
    }

    /**
     * If we are in this state, we are positioned to shoot and just need to turn the
     * {@link com.maxtech.maxx.subsystems.flywheel.Flywheel} on.
     * */
    private void handleShooting(StateMachineMeta m) {

    }

    @Override
    public void execute() {
        statemachine.start();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
