package com.maxtech.maxx.commands.plumbing.climber;

import com.maxtech.maxx.subsystems.climber.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Handoff extends CommandBase {
    // TODO Make this when needed...
    private final Climber climber = Climber.getInstance();

    public Handoff() {addRequirements(climber);}

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        climber.run(Climber.State.Handoff);
    }

    @Override
    public void end(boolean interrupted) {
        //climber.halt();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
