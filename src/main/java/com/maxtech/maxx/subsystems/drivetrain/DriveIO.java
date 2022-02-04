package com.maxtech.maxx.subsystems.drivetrain;

import com.maxtech.lib.logging.LoggableInputs;
import com.maxtech.lib.logging.attributes.Log;

public interface DriveIO {
    class DriveIOInputs implements LoggableInputs {
        @Log(name = "Left1Volts")
        public double left1AppliedVolts = 0.0;
        @Log(name = "Left2Volts")
        public double left2AppliedVolts = 0.0;
        @Log(name = "Right1Volts")
        public double right1AppliedVolts = 0.0;
        @Log(name = "Right2Volts")
        public double right2AppliedVolts = 0.0;
    }

    // TODO: Modify these methods to be more expressive and composable.
    void drive(double ls, double rs);

    DriveIOInputs inputs = new DriveIOInputs();
    void updateInputs();
}
