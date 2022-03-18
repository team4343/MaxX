package com.maxtech.maxx.commands.porcelain.flywheel;

import com.maxtech.maxx.Constants;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetFlywheelHigh extends CommandBase {
    private final Flywheel flywheel = Flywheel.getInstance();

    public SetFlywheelHigh() {
        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
        flywheel.setGoal(Constants.Flywheel.topBinRPM);
    }

    @Override
    public boolean isFinished() {
        return flywheel.atGoal();
    }
}
