package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.GoldDetectorVuforia;
import GrimSkyLibraries.Intake;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Sensors;

import static GrimSky.GrimSkyOpMode.liftHeight;

//C:\Users\Avi\AppData\Local\Android\sdk\platform-tools

@Autonomous(name = "OP Depot", group = "LinearOpMode")
public class DepotCycles extends LinearOpMode {

    private Drivetrain drivetrain;
    private Sensors sensors;
    private Lift lift;
    private Intake intake;
    private ElapsedTime runtime = new ElapsedTime();
    private GoldDetectorVuforia sample;
    private String cubePos = "null";

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        lift = new Lift(this);
        intake = new Intake(this);
        sample = new GoldDetectorVuforia(this);

        int offset = 135;

        while (!isStarted()) {
            cubePos = sample.getCubePos();
            telemetry.addData("cubePos: ", cubePos);
            telemetry.update();
            intake.initMove(-.2);
        }

        telemetry.addData("cubePos: ", cubePos);
        telemetry.update();

        liftHeight = 2000;

        waitForStart();


        //=========================== UNHANG =======================================================
        intake.initMove(0);
        drivetrain.unhang();
        lift.setBrake();

        //======================= DROP OFF MARKER IN DEPOT =========================================

        drivetrain.turnPD(0 + offset, .8, .3, 2);
        intake.pivotUp();
        intake.move(1, 40, 2);
        intake.pivotMid();
        intake.collectionOut();
        sleep(500);
        intake.collectionStop();
        sleep(500);
        intake.pivotUp();
        sleep(500);
        intake.move(-1, 45, 2);
        intake.pivotMid();

        //===================== COLLECT MINERAL, SCORE, AND TURN ===================================
        if (cubePos.equals("left")) {
            drivetrain.turnPD(-26 + offset, .85, .65, 2);
            intake.pivotDown();
            intake.collectionIn();
            intake.move(1, 25, 1.25);
            sleep(500);
            intake.pivotUp();
            intake.move(-1, 30, 2.5);
            intake.gateUp();
            intake.move(-.2, 10, 1);
            sleep(1000);
            intake.move(.2, 1, 1);
//            drivetrain.turnPD(0 + offset, .8, .3, 2);
            lift.move(1, 2100); //how to keep up at 1 power for a little bit or idk
            lift.out();
            sleep(1000);
            lift.basketsInit();
            lift.move(-1, 2100);
            drivetrain.turnPD(-50 + offset, .9, .75, 2);

        } else if (cubePos.equals("center")) {
            intake.pivotDown();
            sleep(500);
            intake.collectionIn();
            intake.move(1, 20, 1.25);
            sleep(500);
            intake.pivotUp();
            intake.move(-1, 25, 2.5);
            intake.gateUp();
            intake.move(-.2, 10, 1);
            sleep(1000);
            intake.move(.2, 1, 1);
            lift.move(1, 2100); //how to keep up at 1 power for a little bit or idk
            lift.out();
            sleep(1000);
            lift.basketsInit();
            lift.move(-1, 2100);
            drivetrain.turnPD(-50 + offset, .65, .5, 2);
        } else {
            drivetrain.turnPD(29.5 + offset, .9, .65, 2.5);
            intake.pivotDown();
            intake.collectionIn();
            intake.move(1, 25, 1.25);
            sleep(500);
            intake.pivotUp();
            intake.move(-1, 30, 2.5);
            intake.gateUp();
            intake.move(-.2, 10, 1);
            sleep(1000);
            intake.move(.2, 1, 1);
//            drivetrain.turnPD(0 + offset, .8, .3, 2);
            drivetrain.turnPD(0 + offset, .9, .65, 2);
            lift.move(1, 2100); //how to keep up at 1 power for a little bit or idk
            lift.out();
            sleep(1000);
            lift.basketsInit();
            lift.move(-1, 2100);
            drivetrain.turnPD(-50 + offset, .65, .5, 2);
        }

        //==================================== MARKER DEPOSIT ======================================
        sleep(500);
        drivetrain.moveGyro(.5, 37, -50 + offset);
        drivetrain.arcturnPD(-130 + offset, .8, .8, 2);
        sleep(500);
//        drivetrain.moveGyro(.5, 3\0, -110 + offset);
//        drivetrain.wallRollR(-1, 55);


        //======================================= PARK =============================================
        intake.move(1, 25, 2);
        intake.pivotDown();
        intake.gateDown();

    }

    public void sleep(int millis) {
        if (opModeIsActive()) {
            try {
                Thread.sleep(millis);
            } catch (Exception e) {

            }
        }
    }
}
