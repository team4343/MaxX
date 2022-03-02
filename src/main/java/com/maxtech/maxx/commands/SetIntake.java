package com.maxtech.maxx.commands;


import com.maxtech.maxx.Constants;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import com.maxtech.maxx.subsystems.intake.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetIntake extends CommandBase {
    private final Intake intake = Intake.getInstance();
    private boolean down = false;

    public SetIntake(Boolean setDown) {
        addRequirements(intake);
        down = setDown;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (down) {
            intake.run();
        }
        else {
            intake.stop();
        }

    }



}