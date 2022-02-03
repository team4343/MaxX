package com.maxtech.maxx.subsystems.drivetrain;

import com.maxtech.lib.logging.LoggableInputs;
import com.maxtech.lib.logging.Table;

public interface DriveIO {
    class DriveIOInputs implements LoggableInputs {
        public double left1AppliedVolts = 0.0;
        public double left2AppliedVolts = 0.0;
        public double right1AppliedVolts = 0.0;
        public double right2AppliedVolts = 0.0;

        public void serialize(Table table) {
            table.putDouble("Left1AppliedVolts", left1AppliedVolts);
            table.putDouble("Left2AppliedVolts", left2AppliedVolts);
            table.putDouble("Right1AppliedVolts", right1AppliedVolts);
            table.putDouble("Right2AppliedVolts", right2AppliedVolts);
        }

        public void deserialize(Table table) {
            left1AppliedVolts = table.getDouble("Left1AppliedVolts", left1AppliedVolts);
            left2AppliedVolts = table.getDouble("Left2AppliedVolts", left2AppliedVolts);
            right1AppliedVolts = table.getDouble("Right1AppliedVolts", right1AppliedVolts);
            right2AppliedVolts = table.getDouble("Right2AppliedVolts", right2AppliedVolts);
        }
    }

    // TODO: Modify these methods to be more expressive and composable.
    default void drive(double ls, double rs) {}

    DriveIOInputs inputs = new DriveIOInputs();
    default void updateInputs() {
        inputs.serialize(new Table("Drivetrain"));
    }
}
