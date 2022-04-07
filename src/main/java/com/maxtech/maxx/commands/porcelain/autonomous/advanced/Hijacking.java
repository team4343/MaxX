package com.maxtech.maxx.commands.porcelain.autonomous.advanced;

import com.maxtech.lib.command.AutonomousSequentialCommandGroup;
import com.maxtech.maxx.commands.plumbing.autonomous.RunTrajectory;
import com.maxtech.maxx.commands.plumbing.flywheel.SetFlywheelFarHigh;
import com.maxtech.maxx.commands.porcelain.intake.SetIntake;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

/**
 * Hit the other balls away from the field.
 */
public class Hijacking extends AutonomousSequentialCommandGroup {
    Trajectory start = loadPathweaverTrajectory("paths/hijack.corner6.start.wpilib.json");
    Trajectory middle = loadPathweaverTrajectory("paths/hijack.corner6.middle.wpilib.json");
    Trajectory end = loadPathweaverTrajectory("paths/hijack.corner6.end.wpilib.json");

    public Hijacking() {
        addCommands(
                new SetIntake(true),
                new RunTrajectory(start),
                new RunTrajectory(middle),
                new ParallelDeadlineGroup(
                        new RunTrajectory(end),
                        new SetFlywheelFarHigh()
                ),
                new SetIntake(false)
        );
    }

    @Override
    public Pose2d getStartingPosition() {
        return start.getInitialPose();
    }
}
