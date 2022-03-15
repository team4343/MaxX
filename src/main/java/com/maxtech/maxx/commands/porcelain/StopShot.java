package com.maxtech.maxx.commands.porcelain;

import com.maxtech.maxx.commands.porcelain.flywheel.StopFlywheel;
import com.maxtech.maxx.commands.porcelain.indexer.ResetIndexer;
import com.maxtech.maxx.commands.porcelain.indexer.StopIndexer;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class StopShot extends ParallelCommandGroup {
    public StopShot() {
        addCommands(
                new StopFlywheel(),
                new SequentialCommandGroup(
                        new StopIndexer(),
                        new ResetIndexer()
                ));
    }
}
