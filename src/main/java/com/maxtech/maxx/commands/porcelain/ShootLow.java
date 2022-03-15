package com.maxtech.maxx.commands.porcelain;

import com.maxtech.maxx.commands.porcelain.flywheel.SetFlywheelLow;
import com.maxtech.maxx.commands.porcelain.indexer.ShootIndexer;
import com.maxtech.maxx.commands.porcelain.indexer.StopIndexer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootLow extends SequentialCommandGroup {
    public ShootLow() {
        addCommands(new SetFlywheelLow(), new ShootIndexer(), new StopIndexer());
    }
}
