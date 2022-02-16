import com.maxtech.maxx.subsystems.DriveSubsystem;
import org.junit.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriveTest {
    public static final double DELTA = 1e-2;

    DriveSubsystem drivetrain = new DriveSubsystem();

    @Before
    public void setup() {
    }

    @Test
    public void startsAtZero() {
        assertEquals(0, drivetrain.getWheelSpeeds().leftMetersPerSecond, DELTA);
        assertEquals(0, drivetrain.getWheelSpeeds().rightMetersPerSecond, DELTA);
    }
}
