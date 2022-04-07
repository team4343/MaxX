package com.maxtech.maxx.commands.porcelain.autonomous.advanced;

import com.maxtech.lib.command.AutonomousSequentialCommandGroup;
import com.maxtech.maxx.commands.plumbing.autonomous.RunTrajectory;
import com.maxtech.maxx.commands.porcelain.intake.SetIntake;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ThreeBallThroughMiddle extends AutonomousSequentialCommandGroup {
    Trajectory start = loadPathweaverTrajectory("paths/threeball.fender1.start.wpilib.json");
    Trajectory end = loadPathweaverTrajectory("paths/threeball.fender1.end.wpilib.json");

    public ThreeBallThroughMiddle() {
        addCommands(
                new SetIntake(true),
                new RunTrajectory(start),
                new WaitCommand(2),
                new RunTrajectory(end)
        );
    }

    @Override
    public Pose2d getStartingPosition() {
        return start.getInitialPose();
    }
}
