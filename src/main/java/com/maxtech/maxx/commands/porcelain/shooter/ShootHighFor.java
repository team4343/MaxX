package com.maxtech.maxx.commands.porcelain.shooter;

import com.maxtech.maxx.commands.plumbing.flywheel.SetFlywheelHighFor;
import com.maxtech.maxx.commands.porcelain.indexer.ShootIndexer;
import com.maxtech.maxx.commands.porcelain.indexer.StopIndexer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootHighFor extends SequentialCommandGroup {
    public ShootHighFor(double time) {
        addCommands(
                new SetFlywheelHighFor(time),
                new ShootIndexer(),
                new StopIndexer()
        );
    }
}