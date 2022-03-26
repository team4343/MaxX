package com.maxtech.maxx.commands.porcelain.autonomous;

import com.maxtech.lib.command.AutonomousSequentialCommandGroup;
import edu.wpi.first.math.geometry.Pose2d;

public class NoOp extends AutonomousSequentialCommandGroup {
    @Override
    public Pose2d getStartingPosition() {
        return new Pose2d();
    }
}
