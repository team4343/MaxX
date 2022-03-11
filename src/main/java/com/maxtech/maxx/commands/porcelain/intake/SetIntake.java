package com.maxtech.maxx.commands.porcelain.intake;

import com.maxtech.maxx.subsystems.intake.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetIntake extends CommandBase {
    private final Intake intake = Intake.getInstance();
    private boolean down = false;
    private boolean dump = false;

    public SetIntake(boolean setDown) {
        addRequirements(intake);
        down = setDown;
    }
    public SetIntake(boolean setDown, boolean setDump) {
        addRequirements(intake);
        down = setDown;
        dump = setDump;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (dump)
            intake.runDump();
        else if (down) {
            intake.run();
        }
        else {
            intake.stop();
        }
    }
}