package com.maxtech.maxx.commands.flywheel;

import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetFlywheel extends CommandBase {
    private final double speed;
    private final Flywheel flywheel = Flywheel.getInstance();

    public SetFlywheel(double rpm) {
        this.speed = rpm;

        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
        flywheel.setGoalVelocity(speed);
    }

    @Override
    public void execute() {
        flywheel.run();
    }
}
