package com.maxtech.maxx.commands.porcelain.intake;

import com.maxtech.maxx.subsystems.intake.Intake;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetIntakeDownFor extends CommandBase {
    private final Intake intake = Intake.getInstance();

    private final double startTime;
    private final double length;

    public SetIntakeDownFor(double length) {
        this.length = length;

        startTime = Timer.getFPGATimestamp();

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.run();
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime > length;
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }
}
