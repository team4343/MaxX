package com.maxtech.maxx.commands;

import com.maxtech.maxx.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Intake_up_down extends CommandBase {
    Intake intake;

    public Intake_up_down(Intake intake){
        this.intake = intake;
    }

    @Override
    public void execute() {
        intake.start();
    }
}

