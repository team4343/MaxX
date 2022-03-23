package com.maxtech.maxx.subsystems;

import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.maxx.Constants;
import com.maxtech.maxx.subsystems.intake.Intake;
import com.maxtech.maxx.subsystems.intake.IntakeIO;
import com.maxtech.maxx.subsystems.intake.IntakeIOMax;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.maxtech.maxx.Constants.a1;
import static com.maxtech.maxx.Constants.a2;
import static com.maxtech.maxx.RobotContainer.decideIO;

public class Limelight extends SubsystemBase {
    NetworkTable table;

    private static Limelight instance;
    double tv;
    double tx;
    double ty;
    double ta;
    double ts;
    double tl;
    double td;

    public static Limelight getInstance() {
        if (instance == null) {
            instance = new Limelight();
            instance.table = NetworkTableInstance.getDefault().getTable("limelight");
        }
        instance.table.getEntry("pipeline").setNumber(1);
        return instance;
    }

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
        SmartDashboard.putNumber("Distance", getDistance());
        System.out.println(getDistance());
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

    public void setNumberTable(String name, int value) {
        //instance.table.getEntry(name).setNumber(value);
    }

    public double getDistance() {
        // Distance is to the center of the flywheel axel.
        // TODO: The height (5) is probably inaccurate, please review.
        // Goal Height 104
        // Limelight Height 33
        // Limelight angle to goal is ta
        // Limelight angle from horizontal 28.51
        // Axel offset from limelight camera 3
        setNumberTable("pipeline", 1);
        System.out.println(table.getEntry("ty").getDouble(0));
        return (106-33) / (Math.tan((getTy() + 37)* 0.0175)) ;
    }
}