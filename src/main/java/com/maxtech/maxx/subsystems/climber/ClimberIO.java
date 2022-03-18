package com.maxtech.maxx.subsystems.climber;

public interface ClimberIO {
    void setPivotPos(double pos);
    void setWinchPos(double pos);
    double getWinchPos();
    double getPivotPos();
    void halt();
}
