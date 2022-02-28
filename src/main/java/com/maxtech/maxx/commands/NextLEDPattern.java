package com.maxtech.maxx.commands;

import com.maxtech.maxx.subsystems.LEDs;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class NextLEDPattern extends CommandBase {
    private LEDs leds = LEDs.getInstance();

    public NextLEDPattern() {
        addRequirements(leds);
    }

    @Override
    public void execute() {
        leds.toNextPattern();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
