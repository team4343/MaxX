package com.maxtech.maxx.commands.porcelain.shooter;

import com.maxtech.maxx.commands.plumbing.flywheel.SetFlywheelHigh;
import com.maxtech.maxx.commands.plumbing.flywheel.SetFlywheelHighLimelight;
import com.maxtech.maxx.commands.porcelain.indexer.ShootIndexer;
import com.maxtech.maxx.commands.porcelain.indexer.StopIndexer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootHighLimelight extends SequentialCommandGroup {
    public ShootHighLimelight() {
        addCommands(new SetFlywheelHighLimelight(), new ShootIndexer(), new StopIndexer());
    }

}
