package com.maxtech.maxx.commands.plumbing.flywheel;

import com.maxtech.lib.command.TimedCommand;
import com.maxtech.maxx.Constants;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;

public class SetFlywheelHighFor extends TimedCommand {
    private final Flywheel flywheel = Flywheel.getInstance();

    public SetFlywheelHighFor(double length) {
        setLength(length);
        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
        flywheel.setGoal(Constants.Flywheel.topBinRPM);
    }

    @Override
    public void end(boolean interrupted) {
        flywheel.stop();
    }
}