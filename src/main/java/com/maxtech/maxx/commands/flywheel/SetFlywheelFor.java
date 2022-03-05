package com.maxtech.maxx.commands.flywheel;

import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.Robot;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetFlywheelFor extends CommandBase {
    private final Flywheel flywheel = Flywheel.getInstance();
    private final Indexer indexer = Indexer.getInstance();

    private final double time;

    private final Flywheel.FlywheelStates state;
    private final double seconds;

    public SetFlywheelFor(Flywheel.FlywheelStates state, double seconds) {
        this.state = state;
        this.seconds = seconds;

        time = Timer.getFPGATimestamp();

        addRequirements(flywheel);
    }

    @Override
    public void execute() {
        indexer.run();
        flywheel.run(this.state);
    }

    @Override
    public boolean isFinished() {
        double current = Timer.getFPGATimestamp();
        RobotLogger.getInstance().dbg("current: %s, start time: %s", current, time);
        return current - time > seconds;
    }

    @Override
    public void end(boolean interrupted) {
        flywheel.run(Flywheel.FlywheelStates.Idle);
    }
}
