package com.maxtech.maxx.commands.autonomous.paths;

import com.maxtech.maxx.commands.drivetrain.TankDrive;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.io.IOException;
import java.nio.file.Path;

import static com.maxtech.maxx.Constants.Drive.*;

public class ExamplePath extends SequentialCommandGroup {
    private final Drive drivetrain = Drive.getInstance();

    /**
     * The file from which to load the trajectory during runtime.
     */
    private final String trajectoryFile = "paths/GetBallA.wpilib.json";
    private Trajectory trajectory = new Trajectory();

    RamseteCommand command;

    public ExamplePath() {
        addRequirements(drivetrain);

        // Load the trajectory.
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryFile);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryFile, ex.getStackTrace());
        }

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

        addCommands(command, new TankDrive(0, 0, drivetrain));
    }
}
