package com.maxtech.maxx.commands.intake;

import com.maxtech.maxx.subsystems.intake.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DumpIntake extends CommandBase {
    private final Intake intake = Intake.getInstance();

    public DumpIntake() {
        addRequirements(intake);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        intake.runDump(true);
    }
}
