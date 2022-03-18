package com.maxtech.maxx.commands.porcelain.intake;

import com.maxtech.lib.command.TimedCommand;
import com.maxtech.maxx.subsystems.indexer.Indexer;
import com.maxtech.maxx.subsystems.intake.Intake;

public class LowerIntakeFor extends TimedCommand {
    private final Intake intake = Intake.getInstance();
    private final Indexer indexer = Indexer.getInstance();

    public LowerIntakeFor(double length) {
        setLength(length);

        addRequirements(intake, indexer);
    }

    @Override
    public void initialize() {
        intake.run();
        indexer.turnOn();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
        indexer.turnOff();
    }
}
