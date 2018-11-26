package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Sensors;

/**
 * Created by Avi on 11/19/2018.
 */

@Autonomous(name = "Turn Test", group = "LinearOpMode")
public class TurnTest extends LinearOpMode {
    private Drivetrain drivetrain;
    private Sensors sensors;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);

        waitForStart();

        drivetrain.turnPD(-90, .5, .15, 10);
    }
}
