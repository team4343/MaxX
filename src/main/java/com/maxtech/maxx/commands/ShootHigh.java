package com.maxtech.maxx.commands;

import com.maxtech.maxx.commands.porcelain.flywheel.SetFlywheel;
import com.maxtech.maxx.commands.porcelain.indexer.ShootIndexer;
import com.maxtech.maxx.commands.porcelain.indexer.StopIndexer;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootHigh extends SequentialCommandGroup {
    public ShootHigh() {
        addCommands(new SetFlywheel(Flywheel.FlywheelStates.ShootHigh), new ShootIndexer(), new StopIndexer());
    }
}
