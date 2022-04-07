package com.maxtech.maxx.commands.porcelain.autonomous.advanced;

import com.maxtech.lib.command.AutonomousSequentialCommandGroup;
import com.maxtech.maxx.commands.plumbing.autonomous.RunTrajectory;
import com.maxtech.maxx.commands.porcelain.intake.SetIntake;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ThreeBallThroughMiddleAlternative extends AutonomousSequentialCommandGroup {
    Trajectory start = loadPathweaverTrajectory("paths/threeball.fender1.start.alt.wpilib.json");
    Trajectory end = loadPathweaverTrajectory("paths/threeball.fender1.end.wpilib.json");

    public ThreeBallThroughMiddleAlternative() {
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
