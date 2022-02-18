package com.maxtech.maxx.commands;

import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetFlywheelCommand extends CommandBase {
    private final double speed;
    private final Flywheel flywheel = Flywheel.getInstance();

    public SetFlywheelCommand(double rpm) {
        this.speed = rpm;

        addRequirements(flywheel);
    }

    @Override
    public void execute() {
        flywheel.setGoalVelocity(speed);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
