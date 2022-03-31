package com.maxtech.maxx.commands.porcelain.shooter;

import com.maxtech.maxx.commands.plumbing.flywheel.SetFlywheelFarHigh;
import com.maxtech.maxx.commands.porcelain.indexer.ShootIndexerFor;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootFarHighFor extends SequentialCommandGroup {
    public ShootFarHighFor(double time) {
        addCommands(
                new SetFlywheelFarHigh(),
                new ShootIndexerFor(time),
                new StopShot()
        );
    }
}
