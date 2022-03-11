package com.maxtech.maxx.commands.plumbing.climber;


import com.maxtech.maxx.Constants;
import com.maxtech.maxx.subsystems.climber.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Raise extends CommandBase {
    private final Climber climber = Climber.getInstance();

    public Raise() {
        addRequirements(climber);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        climber.run(Climber.ClimberState.Raising);
    }

    @Override
    public void end(boolean interrupted) {
        climber.halt();
    }
}