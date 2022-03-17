package com.maxtech.maxx.commands.plumbing.climber;


import com.maxtech.maxx.subsystems.climber.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Default extends CommandBase {
    private final Climber climber = Climber.getInstance();

    public Default() {
        addRequirements(climber);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        climber.run(Climber.State.Match);
    }

    @Override
    public void end(boolean interrupted) {
        //climber.halt();
    }
}