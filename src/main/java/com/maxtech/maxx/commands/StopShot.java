package com.maxtech.maxx.commands;

import com.maxtech.maxx.commands.porcelain.flywheel.SetFlywheel;
import com.maxtech.maxx.commands.porcelain.indexer.ResetIndexer;
import com.maxtech.maxx.commands.porcelain.indexer.StopIndexer;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class StopShot extends ParallelCommandGroup {
    public StopShot() {
        addCommands(
                new SetFlywheel(Flywheel.FlywheelStates.Idle),
                new SequentialCommandGroup(
                        new StopIndexer(),
                        new ResetIndexer()
                ));
    }
}
