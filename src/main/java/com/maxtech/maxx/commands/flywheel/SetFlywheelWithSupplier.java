package com.maxtech.maxx.commands.flywheel;

import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.DoubleSupplier;

public class SetFlywheelWithSupplier extends CommandBase {
    private final DoubleSupplier speed;
    private final Flywheel flywheel = Flywheel.getInstance();

    public SetFlywheelWithSupplier(DoubleSupplier rpm) {
        this.speed = rpm;

        addRequirements(flywheel);
    }

    @Override
    public void execute() {
        flywheel.setGoalVelocity(speed.getAsDouble());
    }

    @Override
    public void end(boolean interrupted) {
        flywheel.stop();
    }
}
