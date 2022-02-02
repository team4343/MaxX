package com.maxtech.maxx.subsystems.drivetrain;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public interface DriveIO {
    class DriveIOInputs implements LoggableInputs {
        public double left1AppliedVolts = 0.0;
        public double left2AppliedVolts = 0.0;
        public double right1AppliedVolts = 0.0;
        public double right2AppliedVolts = 0.0;

        public void toLog(LogTable table) {
            table.put("Left1AppliedVolts", left1AppliedVolts);
            table.put("Left2AppliedVolts", left2AppliedVolts);
            table.put("Right1AppliedVolts", right1AppliedVolts);
            table.put("Right2AppliedVolts", right2AppliedVolts);
        }

        public void fromLog(LogTable table) {
            left1AppliedVolts = table.getDouble("Left1AppliedVolts", left1AppliedVolts);
            left2AppliedVolts = table.getDouble("Left2AppliedVolts", left2AppliedVolts);
            right1AppliedVolts = table.getDouble("Right1AppliedVolts", right1AppliedVolts);
            right2AppliedVolts = table.getDouble("Right2AppliedVolts", right2AppliedVolts);
        }
    }

    // TODO: Modify these methods to be more expressive and composable.
    default void drive(double ls, double rs) {}
}
