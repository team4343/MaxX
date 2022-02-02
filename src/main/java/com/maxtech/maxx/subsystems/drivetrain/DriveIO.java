package com.maxtech.maxx.subsystems.drivetrain;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public interface DriveIO {
    class DriveIOInputs implements LoggableInputs {
        public double leftAppliedVolts = 0.0;
        public double rightAppliedVolts = 0.0;

        public void toLog(LogTable table) {
            table.put("LeftAppliedVolts", leftAppliedVolts);
            table.put("RightAppliedVolts", rightAppliedVolts);
        }

        public void fromLog(LogTable table) {
            leftAppliedVolts = table.getDouble("LeftAppliedVolts", leftAppliedVolts);
            rightAppliedVolts = table.getDouble("RightAppliedVolts", rightAppliedVolts);
        }
    }

    // TODO: Modify these methods to be more expressive and composable.
    default void drive(double ls, double rs) {}
}
