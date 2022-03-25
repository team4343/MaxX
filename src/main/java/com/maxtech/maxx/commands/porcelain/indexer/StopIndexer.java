package com.maxtech.maxx.commands.porcelain.indexer;

import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class StopIndexer extends CommandBase {
    private final Indexer indexer = Indexer.getInstance();

    public StopIndexer() {
        addRequirements(indexer);
    }

    @Override
    public void execute() {
        indexer.turnOff();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
