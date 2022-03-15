package com.maxtech.maxx.commands.porcelain.flywheel;

import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.Constants;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetFlywheelHighFor extends CommandBase {
    private final Flywheel flywheel = Flywheel.getInstance();

    private final double startTime;
    private final double length;

    public SetFlywheelHighFor(double length) {
        this.length = length;

        startTime = Timer.getFPGATimestamp();

        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
        flywheel.setGoal(Constants.Flywheel.topBinRPM);
    }

    @Override
    public void execute() {
        flywheel.run();
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime > length;
    }

    @Override
    public void end(boolean interrupted) {
        flywheel.stop();
    }
}