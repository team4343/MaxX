package com.maxtech.maxx.subsystems.climber;

public interface ClimberIO {
    public void setPivotPos(double pos);
    public void setWinchPos(double pos);
    public double getWinchPos();
    public double getPivotPos();
    public void halt();
}
