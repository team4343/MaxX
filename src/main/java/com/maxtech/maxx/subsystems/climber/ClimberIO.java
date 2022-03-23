package com.maxtech.maxx.subsystems.climber;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;

public interface ClimberIO {
    void setPivotPos(double pos);
    void setWinchPos(double pos);
    double getWinchPos();
    double getPivotPos();
    CANSparkMax getWinchL();
    CANSparkMax getWinchR();
    TalonSRX getPivotL();
    TalonSRX getPivotR();
    void halt();
}
