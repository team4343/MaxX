package com.maxtech.maxx.commands.plumbing.climber;

import com.maxtech.maxx.subsystems.climber.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Extend extends CommandBase {
    private final Climber climber = Climber.getInstance();

    public Extend() {
        addRequirements(climber);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        climber.run(Climber.ClimberState.Extend);
    }

    @Override
    public void end(boolean interrupted) {
        climber.halt();
    }
}