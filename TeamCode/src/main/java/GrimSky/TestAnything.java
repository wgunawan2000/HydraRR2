package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.GoldDetectorVuforia;
import GrimSkyLibraries.Intake;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;

@Autonomous(name = "Test Anything", group = "LinearOpMode")
public class TestAnything extends LinearOpMode {

    private Drivetrain drivetrain;
    private Sensors sensors;
    private Marker marker;
    private Lift lift;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        marker = new Marker(this);
        lift = new Lift(this);

        waitForStart();

        int offset = 0;

        runtime.reset();

        drivetrain.turnPD(25 + offset, .55, .6, 3);
        sleep(250);
        drivetrain.moveGyro(.3, 30, 25 + offset);
        drivetrain.arcturnPD(-55 + offset, .8, 1.2, 4);
        drivetrain.moveGyro(.5, 5, -45 + offset);
        drivetrain.arcturnPD(45 + offset, .8, 1.2, 4);
        sleep(500);
        drivetrain.move(-.3, 4);

    }

}

