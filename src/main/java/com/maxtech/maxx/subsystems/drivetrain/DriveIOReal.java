package com.maxtech.maxx.subsystems.drivetrain;

import com.maxtech.lib.logging.Log;
import com.maxtech.maxx.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class DriveIOReal implements DriveIO {
    private final CANSparkMax left1 = new CANSparkMax(Constants.left1ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax left2 = new CANSparkMax(Constants.left2ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final MotorControllerGroup left = new MotorControllerGroup(left1, left2);

    private final CANSparkMax right1 = new CANSparkMax(Constants.right1ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax right2 = new CANSparkMax(Constants.right2ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final MotorControllerGroup right = new MotorControllerGroup(right1, right2);

    private final DifferentialDrive drivetrain = new DifferentialDrive(left, right);

    public DriveIOReal() {
        Log.info("DriveIO", "Entered DriveIOReal.");
        right.setInverted(true);
    }

    public void drive(double ls, double rs) {
        drivetrain.tankDrive(ls, rs);
    }

    public void updateInputs() {
        inputs.left1AppliedVolts = left1.getBusVoltage();
        inputs.left2AppliedVolts = left2.getBusVoltage();
        inputs.right1AppliedVolts = right1.getBusVoltage();
        inputs.right2AppliedVolts = right2.getBusVoltage();
    }
}
