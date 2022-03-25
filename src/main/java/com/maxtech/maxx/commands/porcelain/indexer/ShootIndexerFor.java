package com.maxtech.maxx.commands.porcelain.indexer;

import com.maxtech.lib.command.TimedCommand;
import com.maxtech.maxx.subsystems.indexer.Indexer;

public class ShootIndexerFor extends TimedCommand {
    private final Indexer indexer = Indexer.getInstance();

    public ShootIndexerFor(double time) {
        setLength(time);
    }

    @Override
    public void initialize() {
        super.initialize();
        indexer.shoot();
    }

    @Override
    public void end(boolean interrupted) {
        indexer.stopShoot();
        indexer.reset();
        indexer.turnOff();
    }
}
