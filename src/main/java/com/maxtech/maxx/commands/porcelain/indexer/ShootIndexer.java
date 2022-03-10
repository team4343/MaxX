package com.maxtech.maxx.commands.porcelain.indexer;

import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootIndexer extends CommandBase {
    private final Indexer indexer = Indexer.getInstance();

    public ShootIndexer() {
        addRequirements(indexer);
    }

    @Override
    public void execute() {
        indexer.shoot();
    }

    @Override
    public void end(boolean interrupted) {
        indexer.stopShoot();
    }
}
