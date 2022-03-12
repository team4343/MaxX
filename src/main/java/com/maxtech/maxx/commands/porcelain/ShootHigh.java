package com.maxtech.maxx.commands.porcelain;

import com.maxtech.maxx.commands.porcelain.flywheel.SetFlywheelHigh;
import com.maxtech.maxx.commands.porcelain.indexer.ShootIndexer;
import com.maxtech.maxx.commands.porcelain.indexer.StopIndexer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootHigh extends SequentialCommandGroup {
    public ShootHigh() {
        addCommands(new SetFlywheelHigh(), new ShootIndexer(), new StopIndexer());
    }
}
