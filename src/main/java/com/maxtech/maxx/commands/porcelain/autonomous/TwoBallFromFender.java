package com.maxtech.maxx.commands.porcelain.autonomous;

import com.maxtech.lib.command.AutonomousSequentialCommandGroup;
import com.maxtech.maxx.commands.plumbing.autonomous.RunTrajectory;
import com.maxtech.maxx.commands.porcelain.intake.LowerIntakeFor;
import com.maxtech.maxx.commands.porcelain.intake.SetIntake;
import com.maxtech.maxx.commands.porcelain.shooter.ShootHighFor;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

public class TwoBallFromFender extends AutonomousSequentialCommandGroup {
    private final Drive drivetrain = Drive.getInstance();

    public TwoBallFromFender() {
        addRequirements(drivetrain);

        Trajectory start = loadPathweaverTrajectory("paths/fender to ball.wpilib.json");
        Trajectory finish = loadPathweaverTrajectory("paths/from ball to fender.wpilib.json");

        addCommands(
                new ShootHighFor(5),
                new ParallelDeadlineGroup(
                    new RunTrajectory(start),
                    new SetIntake(true)
                ),
                new LowerIntakeFor(2),
                new RunTrajectory(finish),
                new ShootHighFor(4)
        );
    }

    @Override
    public Pose2d getStartingPosition() {
        return new Pose2d(7.898, 3.136, new Rotation2d(-0.697, -1.754));
    }
}
