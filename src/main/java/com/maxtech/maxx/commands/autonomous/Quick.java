package com.maxtech.maxx.commands.autonomous;

import com.maxtech.maxx.commands.drivetrain.ArcadeDrive;
import com.maxtech.maxx.commands.flywheel.SetFlywheelFor;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Quick extends SequentialCommandGroup {
    public Quick() {
        addCommands(
                new SetFlywheelFor(Flywheel.FlywheelStates.ShootLow, 2),
                new ArcadeDrive(.3, 0)
        );
    }
}
