package com.maxtech.maxx.commands;

import com.maxtech.maxx.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class StartIntake extends CommandBase {
    Intake intake;

    public StartIntake(Intake intake){
        this.intake = intake;
    }

    @Override
    public void execute() {
        intake.start();
    }
}

