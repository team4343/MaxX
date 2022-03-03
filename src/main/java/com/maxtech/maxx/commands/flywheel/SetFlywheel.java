package com.maxtech.maxx.commands.flywheel;

import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetFlywheel extends CommandBase {
    private final Flywheel flywheel = Flywheel.getInstance();
    private final Indexer indexer = Indexer.getInstance();
    private final Flywheel.FlywheelStates state;

    public SetFlywheel(Flywheel.FlywheelStates state) {
        this.state = state;
        addRequirements(flywheel);
        addRequirements(indexer);
    }

    @Override
    public void execute() {
        flywheel.run(this.state);
        indexer.run(true);
    }
}
