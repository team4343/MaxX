package com.maxtech.maxx.commands.autonomous;

import com.maxtech.maxx.subsystems.DriveSubsystem;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.io.IOException;
import java.nio.file.Path;

import static com.maxtech.maxx.Constants.Drive.*;

public class ExamplePath extends CommandBase {
    private final DriveSubsystem drivetrain;

    /**
     * The file from which to load the trajectory during runtime.
     */
    private final String trajectoryFile = "paths/GetBallA.wpilib.json";
    private Trajectory trajectory = new Trajectory();

    RamseteCommand command;

    public ExamplePath(DriveSubsystem drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);

        // Load the trajectory.
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryFile);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryFile, ex.getStackTrace());
        }

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

        // Finally, create our actual command.
        RamseteCommand command = new RamseteCommand(
                trajectory,
                drivetrain::getPose,
                new RamseteController(ramseteB, ramseteZeta),
                new SimpleMotorFeedforward(
                        ksVolts,
                        kvVoltSecondsPerRadian,
                        kaVoltSecondsSquaredPerRadian
                ),
                kinematics,
                drivetrain::getWheelSpeeds,
                new PIDController(kpDriveVelocity, 0, 0),
                new PIDController(kpDriveVelocity, 0, 0),
                drivetrain::tank,
                drivetrain
        );

        this.command = command;
    }

    @Override
    public void execute() {
        // Reset the odometry to the initial pose provided by the trajectory.
        drivetrain.resetOdometry(trajectory.getInitialPose());

        // Run the RamseteCommand.
        command.execute();
    }
}
