package com.maxtech.maxx.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.maxtech.maxx.Constants.a1;
import static com.maxtech.maxx.Constants.a2;

public class Limelight extends SubsystemBase {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

    double tv;
    double tx;
    double ty;
    double ta;
    double ts;
    double tl;
    double td;

    @Override
    public void periodic() {
        tv = table.getEntry("tv").getDouble(0);
        tx = table.getEntry("tx").getDouble(0);
        ty = table.getEntry("ty").getDouble(0);
        ta = table.getEntry("ta").getDouble(0);
        ts = table.getEntry("ts").getDouble(0);
        tl = table.getEntry("tl").getDouble(0);
        td = table.getEntry("td").getDouble(0);
        double pipeline = table.getEntry("getpipe").getDouble(0);

        SmartDashboard.putNumber("tv", tv);
        SmartDashboard.putNumber("tx", tx);
        SmartDashboard.putNumber("ty", ty);
        SmartDashboard.putNumber("ta", ta);
        SmartDashboard.putNumber("ts", ts);
        SmartDashboard.putNumber("tl", tl);
        SmartDashboard.putNumber("td", td);
        SmartDashboard.putNumber("l", getDistance());
        SmartDashboard.putNumber("Pipeline", pipeline);
    }

    public double getTv() {
        return tv;
    }

    public double getTx() {
        return tv;
    }

    public double getTy() {
        return ty;
    }

    public double getTa() {
        return ta;
    }

    public double getTs() {
        return ts;
    }

    public double getTl() {
        return tl;
    }

    public double getTd() {
        return td;
    }

    public double getDistance() {
        // TODO: The height (5) is probably inaccurate, please review.
        return (5 + 104) / (Math.tan(a1 + a2));
    }
}