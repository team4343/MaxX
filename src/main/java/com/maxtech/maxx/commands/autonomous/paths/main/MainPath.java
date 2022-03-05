package com.maxtech.maxx.commands.autonomous.paths.main;

import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.commands.autonomous.RunPath;
import com.maxtech.maxx.commands.flywheel.SetFlywheel;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.io.IOException;

public class MainPath extends SequentialCommandGroup {
    public MainPath() {
        Trajectory start;
        Trajectory middle;
        Trajectory end;

        try {
            start = TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("paths/Start.wpilib.json"));
            middle = TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("paths/Middle.wpilib.json"));
            end = TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("paths/Collect.wpilib.json"));

            addCommands(
                    new RunPath(start),
                    new ParallelDeadlineGroup(
                            new RunCommand(() -> {
                                Timer.delay(3);
                            }),
                            new SetFlywheel(Flywheel.FlywheelStates.ShootLow)
                    ),
                    new RunPath(middle),
                    new ParallelDeadlineGroup(
                            new RunCommand(() -> {
                                Timer.delay(3);
                            }),
                            new SetFlywheel(Flywheel.FlywheelStates.ShootLow)
                    ),
                    new RunPath(end)
            );
        } catch (IOException e) {
            RobotLogger.getInstance().err("%s", e);
        }
    }
}
