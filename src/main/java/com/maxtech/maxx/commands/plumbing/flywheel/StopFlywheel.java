package com.maxtech.maxx.commands.plumbing.flywheel;

import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class StopFlywheel extends CommandBase {
    private final Flywheel flywheel = Flywheel.getInstance();

    public StopFlywheel() {
        addRequirements(flywheel);
    }

    @Override
    public void execute() {
        flywheel.stop();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
