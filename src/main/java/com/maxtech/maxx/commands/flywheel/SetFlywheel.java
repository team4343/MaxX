package com.maxtech.maxx.commands.flywheel;

import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetFlywheel extends CommandBase {
    private final double speed;
    private final Flywheel flywheel = Flywheel.getInstance();
    private final Indexer indexer = Indexer.getInstance();

    public SetFlywheel(double rpm) {
        this.speed = rpm;

        addRequirements(flywheel);
        addRequirements(indexer);
    }

    @Override
    public void initialize() {
        flywheel.setGoalVelocity(speed);
    }

    @Override
    public void execute() {
        flywheel.run();
        indexer.run(true);
    }
}
