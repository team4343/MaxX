package com.maxtech.maxx.commands.porcelain.autonomous;

import com.maxtech.lib.command.AutonomousSequentialCommandGroup;
import com.maxtech.maxx.commands.plumbing.autonomous.RunTrajectory;
import com.maxtech.maxx.commands.porcelain.intake.LowerIntakeFor;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;

public class TwoBallFromFender extends AutonomousSequentialCommandGroup {
    private final Drive drivetrain = Drive.getInstance();

    public TwoBallFromFender() {
        addRequirements(drivetrain);

        Trajectory start = loadPathweaverTrajectory("paths/Unnamed.wpilib.json");
        Trajectory finish = loadPathweaverTrajectory("paths/from ball to fender.wpilib.json");

        addCommands(
                new RunTrajectory(start),
                new LowerIntakeFor(4)
                // new RunTrajectory(finish)
        );
    }

    @Override
    public Pose2d getStartingPosition() {
        return new Pose2d(7.988, 3.192, new Rotation2d(-0.9, -1.822));
    }
}
