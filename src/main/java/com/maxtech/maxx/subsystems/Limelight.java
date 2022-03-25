package com.maxtech.maxx.subsystems;

import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.maxx.Constants;
import com.maxtech.maxx.subsystems.intake.Intake;
import com.maxtech.maxx.subsystems.intake.IntakeIO;
import com.maxtech.maxx.subsystems.intake.IntakeIOMax;
import edu.wpi.first.math.controller.PIDController;
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
    private static PIDController pidController;

    public static Limelight getInstance() {
        if (instance == null) {
            instance = new Limelight();
            instance.table = NetworkTableInstance.getDefault().getTable("limelight");
        }
        instance.table.getEntry("pipeline").setNumber(1);
        return instance;
    }

    public Limelight() {
        pidController = new PIDController(0.2,0,0);
        pidController.setTolerance(5); // 3 degree tolerance.
        pidController.setSetpoint(0.0);
        pidController.setIntegratorRange(-0.5,0.5);

    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Distance", getDistance());
        SmartDashboard.putBoolean("Aligned", aligned());
    }

    public boolean getTv() {
        return table.getEntry("tv").getBoolean(false);
    }
    public double getTx() {
        return table.getEntry("tx").getDouble(0);
    }
    public double getTy() {
        return table.getEntry("ty").getDouble(0);
    }
    public double getTa() {
        return table.getEntry("ta").getDouble(0);
    }
    public double getTs() {
        return table.getEntry("ts").getDouble(0);
    }
    public double getTl() {
        return table.getEntry("tl").getDouble(0);
    }
    public double getTd() {
        return table.getEntry("td").getDouble(0);
    }

    public void setNumberTable(String name, int value) {
        instance.table.getEntry(name).setNumber(value);
    }

    public void setPipeline(int pipeline) {table.getEntry("pipeline").setNumber(pipeline);}

    public double getDistance() {
        if (getTv())
            return 30; // However far back the average between fender and limelight vision loss is.

        // Distance is to the center of the flywheel axel.
        // TODO: The height (5) is probably inaccurate, please review.
        // Goal Height 104
        // Limelight Height 33
        // Limelight angle to goal is ta
        // Limelight angle from horizontal 28.51
        // Axel offset from limelight camera 3
        setNumberTable("pipeline", 1);
        //System.out.println((106-33) / (Math.tan((getTy() + 37)* 0.0175)));
        return (106-33) / (Math.tan((getTy() + 37)* 0.0175)) ;
    }

    public double getDriveRotation() {
        if (getTv()) return 0;
        return pidController.calculate(getTx(), 0);
    }

    public boolean aligned() {
        if (getTy() == 0) return false;
        //System.out.println("Tx" + getTx());
        //System.out.println("Position Error" +  pidController.getPositionError());
        return (getTx()>-5&&getTx()<5) && !getTv();
    }
}
