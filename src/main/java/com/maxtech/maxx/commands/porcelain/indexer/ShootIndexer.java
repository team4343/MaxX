package com.maxtech.maxx.commands.porcelain.indexer;

import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootIndexer extends CommandBase {
    private final Indexer indexer = Indexer.getInstance();
    private final Flywheel flywheel = Flywheel.getInstance();

    public ShootIndexer() {
        addRequirements(indexer, flywheel);
    }

    @Override
    public void execute() {
        if (flywheel.atGoal()) {
            indexer.shoot();
        }
    }

    @Override
    public void end(boolean interrupted) {
        indexer.stopShoot();
    }
}
