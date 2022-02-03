package com.maxtech.maxx.subsystems.drivetrain;

import com.maxtech.lib.logging.LoggableInputs;
import com.maxtech.lib.logging.Table;

public interface DriveIO {
    class DriveIOInputs implements LoggableInputs {
        public double left1AppliedVolts = 0.0;
        public double left2AppliedVolts = 0.0;
        public double right1AppliedVolts = 0.0;
        public double right2AppliedVolts = 0.0;

        // We're basically associating each value to a key.

        /** Generate a Table based on the current inputs. */
        public Table serialize() {
            Table table = new Table("Drivetrain");

            table.putDouble("Left1AppliedVolts", left1AppliedVolts);
            table.putDouble("Left2AppliedVolts", left2AppliedVolts);
            table.putDouble("Right1AppliedVolts", right1AppliedVolts);
            table.putDouble("Right2AppliedVolts", right2AppliedVolts);

            return table;
        }
    }

    // TODO: Modify these methods to be more expressive and composable.
    void drive(double ls, double rs);

    DriveIOInputs inputs = new DriveIOInputs();
    void updateInputs();
}
