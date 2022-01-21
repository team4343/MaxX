package com.maxtech.maxx.subsystems;

import com.maxtech.maxx.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    CANSparkMax left1 = new CANSparkMax(Constants.left1ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    CANSparkMax left2 = new CANSparkMax(Constants.left2ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    MotorControllerGroup left = new MotorControllerGroup(left1, left2);

    CANSparkMax right1 = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
    CANSparkMax right2 = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
    MotorControllerGroup right = new MotorControllerGroup(right1, right2);

    DifferentialDrive drivetrain = new DifferentialDrive(left, right);

    public DriveSubsystem() {
    }

    @Override
    public void periodic() {
        drivetrain.tankDrive(1, 1);
            left.set(1);
            right.set(1);

        drivetrain.arcadeDrive( 1, 0);

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}