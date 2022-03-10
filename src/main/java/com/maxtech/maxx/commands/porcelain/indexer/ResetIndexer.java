package com.maxtech.maxx.commands.porcelain.indexer;

import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ResetIndexer extends CommandBase {
    private final Indexer indexer = Indexer.getInstance();

    public ResetIndexer() {
        addRequirements(indexer);
    }

    @Override
    public void execute() {
        indexer.reset();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
