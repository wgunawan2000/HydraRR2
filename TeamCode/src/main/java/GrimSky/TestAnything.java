package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.GoldDetectorVuforia;
import GrimSkyLibraries.Intake;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Sensors;

@Autonomous(name = "Test Anything", group = "LinearOpMode")
public class TestAnything extends LinearOpMode {

    private Drivetrain drivetrain;
    private Sensors sensors;
    private Lift lift;
    private Intake intake;
    private ElapsedTime runtime = new ElapsedTime();
    private GoldDetectorVuforia sample;

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        lift = new Lift(this);
        intake = new Intake(this);
        sample = new GoldDetectorVuforia(this);

        waitForStart();

        intake.move(1, 40, 2);
        intake.pivotMid();
        intake.collectionOut();
        sleep(250);
        intake.collectionStop();
        sleep(500);
        intake.pivotUp();

    }

}

