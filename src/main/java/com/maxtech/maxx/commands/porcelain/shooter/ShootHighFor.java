package com.maxtech.maxx.commands.porcelain.shooter;

import com.maxtech.maxx.commands.plumbing.flywheel.SetFlywheelHigh;
import com.maxtech.maxx.commands.plumbing.flywheel.SetFlywheelHighFor;
import com.maxtech.maxx.commands.plumbing.flywheel.StopFlywheel;
import com.maxtech.maxx.commands.porcelain.indexer.ShootIndexer;
import com.maxtech.maxx.commands.porcelain.indexer.ShootIndexerFor;
import com.maxtech.maxx.commands.porcelain.indexer.StopIndexer;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootHighFor extends SequentialCommandGroup {
    public ShootHighFor(double time) {
        addCommands(
                new SetFlywheelHigh(),
                new ShootIndexerFor(time),
                new StopShot()
        );
    }
}
