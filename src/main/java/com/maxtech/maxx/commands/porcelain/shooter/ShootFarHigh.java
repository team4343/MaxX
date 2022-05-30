package com.maxtech.maxx.commands.porcelain.shooter;

import com.maxtech.maxx.commands.plumbing.flywheel.SetFlywheelFarHigh;
import com.maxtech.maxx.commands.porcelain.indexer.ShootIndexer;
import com.maxtech.maxx.commands.porcelain.indexer.StopIndexer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootFarHigh extends SequentialCommandGroup {
    public ShootFarHigh() {
        addCommands(new SetFlywheelFarHigh(), new ShootIndexer(), new StopIndexer());
    }
}
