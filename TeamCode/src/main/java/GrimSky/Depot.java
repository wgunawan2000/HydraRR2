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

@Autonomous(name = "Depot", group = "LinearOpMode")
public class Depot extends LinearOpMode {

    private Drivetrain drivetrain;
    private Sensors sensors;
    private Marker marker;
    private Lift lift;
    private Intake intake;
    private ElapsedTime runtime = new ElapsedTime();
    private GoldDetectorVuforia sample;
    private String cubePos = "center";

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        marker = new Marker(this);
        lift = new Lift(this);
        intake = new Intake(this);
        sample = new GoldDetectorVuforia(this);

        //we align at the wall, so we are hanging at approx -45 degrees
        int offset = 135;

        while (!isStarted()) {
            cubePos = sample.getCubePos();
        }

        waitForStart();

        //=========================== UNHANG =======================================================
        drivetrain.unhang();

        //=================== HIT MINERAL AND GO TO DEPOT ==========================================
        if (cubePos.equals("left")) {
            drivetrain.turnPD(-25 + offset, .4, .5, 4);
            sleep(500);
            //intake.intakeIn();
            drivetrain.move(.3, 46.5);
            //intake.intakeStop();
            sleep(500);
            drivetrain.turnPD(50 + offset, .33, .57, 4);
            sleep(500);
            drivetrain.move(.4, 28);
            sleep(500);
            drivetrain.move(-.2, 2.5);

        } else if (cubePos.equals("center")) {
            drivetrain.turnPI(offset, .27, 0.2, 2);
            sleep(500);
            //intake.intakeIn();
            drivetrain.move(.6, 55);
            //intake.intakeStop();
            sleep(500);
            drivetrain.turnPD(50 + offset, .38, .45, 4);

        } else {
            drivetrain.turnPD(35 + offset, .38, .39, 4);
            sleep(250);
            //intake.intakeIn();
            drivetrain.move(.3, 46.5);
            //intake.intakeStop();
            sleep(250);
            drivetrain.turnPD(-45 + offset, .35, .55, 4);
            sleep(250);
            drivetrain.move(1, 10);
            sleep(250);
            drivetrain.move(-.2, 2.5);
            drivetrain.turnPD(50 + offset, .32, .6, 4);
        }

        //==================================== MARKER DEPOSIT ======================================
        //intake.intakeStop();
        sleep(250);
        marker.Down();
        sleep(250);

        //======================================= PARK =============================================
        drivetrain.wallRollL(-1, 60);
        Thread.sleep(500);
        drivetrain.wallRollL(-.4, 16);

    }

    //=================================== LOCAL METHODS ============================================
    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

        }
    }
}
