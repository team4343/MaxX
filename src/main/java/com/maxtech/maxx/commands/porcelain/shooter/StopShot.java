package com.maxtech.maxx.commands.porcelain.shooter;

import com.maxtech.maxx.commands.plumbing.flywheel.StopFlywheel;
import com.maxtech.maxx.commands.porcelain.indexer.ResetIndexer;
import com.maxtech.maxx.commands.porcelain.indexer.StopIndexer;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class StopShot extends ParallelCommandGroup {
    public StopShot() {
        addCommands(
                new StopFlywheel(),
                new SequentialCommandGroup(
                        new ResetIndexer(),
                        new StopIndexer()
                ));
    }
}
