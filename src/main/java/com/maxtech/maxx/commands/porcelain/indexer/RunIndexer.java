package com.maxtech.maxx.commands.porcelain.indexer;

import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RunIndexer extends CommandBase {
    private final Indexer indexer = Indexer.getInstance();

    public RunIndexer() {
        addRequirements(indexer);
    }

    @Override
    public void initialize() {
        indexer.turnOn();
    }
}
