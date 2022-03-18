package com.maxtech.lib.command;

import com.maxtech.lib.logging.RobotLogger;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.io.IOException;
import java.nio.file.Path;

public abstract class AutonomousSequentialCommandGroup extends SequentialCommandGroup {
    public abstract Pose2d getStartingPosition();

    public Trajectory loadPathweaverTrajectory(String file) {
        Trajectory trajectory = new Trajectory();

        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(file);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            RobotLogger.getInstance().err("Unable to open trajectory: %s, stack: %s", file, ex.getStackTrace());
        }

        return trajectory;
    }
}
