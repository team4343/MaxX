package com.maxtech.maxx.subsystems.drivetrain;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.wrappers.rev.CANSparkMax;
import com.maxtech.maxx.RobotContainer;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.maxtech.maxx.RobotContainer.decideIO;

public class Drive extends Subsystem {
    private static Drive instance;

    private final DriveIO io = decideIO(DriveIOMax.class, DriveIOMax.class);

    private static final RobotLogger logger = RobotLogger.getInstance();
    private boolean inverted = false;

    public static Drive getInstance() {
        if (instance == null) {
            instance = new Drive();
        }

        return instance;
    }

    public Drive() {
        var tab = Shuffleboard.getTab("Drive");
        tab.addBoolean("dyslexic", () -> this.inverted);
    }

    @Override
    public void periodic() {
        io.periodic();
    }

    @Override
    public void simulationPeriodic() {
        io.simulationPeriodic();
    }

    public void arcade(double s, double r) {
        if (!inverted) {
            io.arcade(s, r);
        } else {
            io.arcade(-s, r);
        }
    }

    public void tank(double ls, double rs) {
        if (!inverted) {
            io.tank(ls, rs);
        } else {
            io.tank(-ls, -rs);
        }
    }

    public void tankDriveVolts(double lv, double rv) {
        if (!inverted) {
            io.tankDriveVolts(lv, rv);
        } else {
            io.tankDriveVolts(-lv, -rv);
        }
    }

    public void stop() {
        tankDriveVolts(0, 0);
    }

    public void setStartingPosition(Pose2d pose) {
        io.setStartingPosition(pose);
    }

    public void resetOdometry(Pose2d pose) {
        io.resetOdometry(pose);
    }

    public Pose2d getPose() {
        return io.getPose();
    }

    public Field2d getField() {
        return io.getField();
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

    public void toggleDirection() {
        inverted = !inverted;
    }

    public void setDirection(boolean direction) {
        inverted = direction;
    }
}
