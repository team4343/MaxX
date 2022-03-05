package com.maxtech.maxx.subsystems.drivetrain;

import com.maxtech.lib.wrappers.rev.CANSparkMax;
import com.maxtech.maxx.Constants;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public interface DriveIO {
    default void periodic() {};
    default void simulationPeriodic() {};

    /**
     * Drive by tank-style parameters.
     *
     * @param ls left side speed
     * @param rs right side speed
     * @see DifferentialDrive
     */
    void tank(double ls, double rs);

    /**

    /**
     * Drive by arcade-style parameters.
     *
     * @param s speed to drive at
     * @param r rotation to drive at
     * @see DifferentialDrive
     */
    void arcade(double s, double r);

    /**
     * Resets the odometry to a given pose.
     *
     * @param pose the pose to which to set the odometry.
     * @see DifferentialDriveOdometry
     * */
    void resetOdometry(Pose2d pose);

    /**
     * Get the current pose, according to odometry.
     *
     * @return the pose
     * */
    Pose2d getPose();

    /**
     * Get the current field.
     *
     * @return the field
     * */
    Field2d getField();

    /** Get the distance travelled of one Spark Max motor controller. */
    double getDistanceTravelled(CANSparkMax controller, double gearing, double wheelDiameter);

    /** Raw Neo Encoder **/
    double getDistanceTravelled();

    /** Get the distance travelled of one Spark Max controller with default values. */
    default double getDistanceTravelled(CANSparkMax controller) {
        return getDistanceTravelled(controller, Constants.Drive.gearing, Constants.Drive.wheelDiameter);
    };

    /**
     * Get the total wheel speeds.
     *
     * @return the wheel speeds
     * @see DifferentialDriveWheelSpeeds
     * */
    DifferentialDriveWheelSpeeds getWheelSpeeds();
}
