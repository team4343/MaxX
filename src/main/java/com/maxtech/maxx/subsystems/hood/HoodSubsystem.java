package com.maxtech.maxx.subsystems.hood;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXSimCollection;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.maxtech.maxx.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HoodSubsystem extends SubsystemBase {

    private final VictorSPX Hood_Motor = new VictorSPX(Constants.HoodID);

    @Override
    public void periodic() {

    }

    @Override
    public void simulationPeriodic() {

    }

    public void start() {
        Hood_Motor.set(ControlMode.Velocity, 1);

    }

    public void stop() {
        Hood_Motor.set(ControlMode.Velocity, 0);

    }



}
