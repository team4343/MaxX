package com.maxtech.maxx.commands.plumbing.flywheel;

import com.maxtech.maxx.Constants;
import com.maxtech.maxx.subsystems.Limelight;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetFlywheelHighLimelight extends CommandBase {
    private final Flywheel flywheel = Flywheel.getInstance();
    private final Limelight limelight = Limelight.getInstance();
    private final Drive drive = Drive.getInstance();

    public SetFlywheelHighLimelight() {
        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
        double dist = limelight.getDistance();

        double rpm = 2300;
        if (dist < 80)
            rpm = 2300;
        else if (dist < 100)
            rpm = 3000;
        else if (dist < 130)
            rpm = 3300;

        //rpm = Constants.Flywheel.topBinRPM;


        if (!limelight.aligned())
            rpm = 2500;
        flywheel.setGoal(rpm);
        System.out.println(limelight.getDriveRotation());
        //drive.arcade(0, limelight.getDriveRotation());
    }

    @Override
    public boolean isFinished() {
        return flywheel.atGoal();
    }
}
