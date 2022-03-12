package com.maxtech.maxx.commands.porcelain.autonomous.tracking;

import com.maxtech.maxx.commands.plumbing.drivetrain.DriveTrajectory;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.List;

import static com.maxtech.maxx.Constants.Drive.*;

public class MoveTowards extends SequentialCommandGroup {
    private final double rotation;
    private final double distance;
    private final Drive drivetrain = Drive.getInstance();

    public MoveTowards(double rotation, double distance) {
        this.rotation = rotation;
        this.distance = distance;
    }

    @Override
    public void execute() {
        addCommands(new DriveTrajectory(generateTrajectory()));
    }

    private Trajectory generateTrajectory() {
        // Create a voltage constraint, which we will use in the configuration below.
        DifferentialDriveVoltageConstraint voltageConstraint =
                new DifferentialDriveVoltageConstraint(
                        new SimpleMotorFeedforward(
                                ksVolts,
                                kvVoltSecondsPerRadian,
                                kaVoltSecondsSquaredPerRadian),
                        kinematics,
                        maxVoltage);

        // Create a config for our trajectory.
        TrajectoryConfig config = new TrajectoryConfig(maxSpeedMetersPerSecond, maxAccelerationMetersPerSecondSquared)
                .setKinematics(kinematics)
                .addConstraint(voltageConstraint);

        return TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the given direction.
                new Pose2d(0, 0, new Rotation2d(rotation)),
                // Pass through nothing.
                List.of(),
                // End at the given X.
                new Pose2d(distance, 0, new Rotation2d(0)),
                // Pass the config
                config);
    }
}
