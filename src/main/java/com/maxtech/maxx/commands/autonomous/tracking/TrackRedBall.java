package com.maxtech.maxx.commands.autonomous.tracking;

import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TrackRedBall extends CommandBase {
    @Override
    public void execute() {
        // We need to obtain a target from the Limelight.

        // Let's add that to our list of coordinates.

        // If we're at 100 coordinates...
        if (true) {
            // Compare them all to be within an epsilon value.

            // If they are, find the average and move towards it!
            new MoveTowards(1, 1).schedule();

            // If not, clear the buffer and try again.

        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
