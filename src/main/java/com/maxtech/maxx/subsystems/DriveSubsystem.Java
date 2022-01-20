package com.maxtech.maxx.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    CANSparkMax left1 = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
    CANSparkMax left2 = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
    MotorControllerGroup left = new MotorControllerGroup(left1, left2);

    CANSparkMax right1 = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
    CANSparkMax right2 = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
    MotorControllerGroup right = new MotorControllerGroup(right1, right2);

    DifferentialDrive drivetrain = new DifferentialDrive(left, right)
    public DriveSubsystem() {
    }

        @Override
        public void periodic() {
            drivetrain.arcadeDrive(1,0.8);
        }

        @Override
        public void simulationPeriodic() {

        }
}