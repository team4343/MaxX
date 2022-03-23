package com.maxtech.maxx.commands.plumbing.flywheel;

import com.maxtech.maxx.Constants;
import com.maxtech.maxx.subsystems.Limelight;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetFlywheelHighLimelight extends CommandBase {
    private final Flywheel flywheel = Flywheel.getInstance();
    private final Limelight limelight = Limelight.getInstance();

    public SetFlywheelHighLimelight() {
        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
        double dist = limelight.getDistance();
        double rpm = 0;
        if (dist < 80)
            rpm = 2300;
        else if (dist < 100)
            rpm = 3000;

        flywheel.setGoal(rpm);
    }

    @Override
    public boolean isFinished() {
        return flywheel.atGoal();
    }
}
