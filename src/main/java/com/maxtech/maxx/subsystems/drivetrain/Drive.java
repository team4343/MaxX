package com.maxtech.maxx.subsystems.drivetrain;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {

    // === INSTANCES ===

    private static Drive instance;

    public static Drive getInstance() {
        if (instance == null) {
            instance = new Drive();
        }

        return instance;
    }

    private Drive() {
        io.setDefaultOption("Max", new DriveIOMax());

        SmartDashboard.putData("Drivetrain chooser", io);
    }

    // === I/O ===
    private SendableChooser<DriveIO> io = new SendableChooser<>();

    // === PUBLIC METHODS ===

    public void arcade(double s, double r) {
        io.getSelected().arcade(s, r);
    }

    public void tank(double ls, double rs) {
        io.getSelected().tank(ls, rs);
    }

    public void resetOdometry(Pose2d pose) {
        io.getSelected().resetOdometry(pose);
    };

    public Pose2d getPose() {
        return io.getSelected().getPose();
    };

    public double getDistanceTravelled(CANSparkMax controller, double gearing, double wheelDiameter) {
        return io.getSelected().getDistanceTravelled(controller, gearing, wheelDiameter);
    };

    public double getDistanceTravelled(CANSparkMax controller) {
        return io.getSelected().getDistanceTravelled(controller);
    };

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return io.getSelected().getWheelSpeeds();
    }
}
