package com.maxtech.maxx.subsystems.drivetrain;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.wrappers.rev.CANSparkMax;
import com.maxtech.maxx.RobotContainer;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends Subsystem {
    private final RobotLogger logger = RobotLogger.getInstance();

    private static Drive instance;

    public static Drive getInstance() {
        if (instance == null) {
            instance = new Drive();
        }

        return instance;
    }

    private Drive() {
        switch(RobotContainer.teamNumber) {
            case 4343: io = new DriveIOMax(); break;
            default: logger.err("Could not pick I/O, no matches."); break;
        }

        logger.log("Chose %s for I/O", io.getClass().getName());
    }

    private DriveIO io;

    @Override
    public void periodic() {
        io.periodic();
    }

    @Override
    public void simulationPeriodic() {
        io.simulationPeriodic();
    }

    public void arcade(double s, double r) {
        io.arcade(s, r);
    }

    public void tank(double ls, double rs) {
        io.tank(ls, rs);
    }

    public void resetOdometry(Pose2d pose) {
        io.resetOdometry(pose);
    }

    public Pose2d getPose() {
        return io.getPose();
    }

    public double getDistanceTravelled(CANSparkMax controller, double gearing, double wheelDiameter) {
        return io.getDistanceTravelled(controller, gearing, wheelDiameter);
    }

    public double getDistanceTravelled(CANSparkMax controller) {
        return io.getDistanceTravelled(controller);
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return io.getWheelSpeeds();
    }

    @Override
    public void sendTelemetry(String prefix) {
        SmartDashboard.putData(prefix + "field", io.getField());
        SmartDashboard.putNumber(prefix + "x", getPose().getX());
        SmartDashboard.putNumber(prefix + "y", getPose().getY());
    }
}
