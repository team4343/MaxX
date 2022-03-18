package com.maxtech.maxx.commands.plumbing.flywheel;

import com.maxtech.maxx.Constants;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetFlywheelLow extends CommandBase {
    private final Flywheel flywheel = Flywheel.getInstance();

    public SetFlywheelLow() {
        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
        flywheel.setGoal(Constants.Flywheel.bottomBinRPM);
    }

    @Override
    public boolean isFinished() {
        return flywheel.atGoal();
    }
}
