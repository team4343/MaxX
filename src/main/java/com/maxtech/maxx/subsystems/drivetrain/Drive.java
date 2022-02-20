package com.maxtech.maxx.subsystems.drivetrain;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.wrappers.rev.CANSparkMax;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends Subsystem {

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

    @Override
    public void periodic() {
        io.getSelected().periodic();
    }

    @Override
    public void simulationPeriodic() {
        io.getSelected().simulationPeriodic();
    }

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

    @Override
    public void sendTelemetry(String prefix) {
        SmartDashboard.putData(prefix + "field", io.getSelected().getField());

        SmartDashboard.putNumber(prefix + "x", getPose().getX());
        SmartDashboard.putNumber(prefix + "y", getPose().getY());
    }
}
