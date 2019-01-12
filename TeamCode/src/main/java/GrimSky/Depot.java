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
    private String cubePos = "null";

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
            telemetry.addData("cubePos: ", cubePos);
            telemetry.update();
        }

        telemetry.addData("cubePos: ", cubePos);
        telemetry.update();

        waitForStart();

        while(cubePos.equals("null")) {
            cubePos = sample.getCubePos();
        }
        telemetry.addData("cubePos", cubePos);
        telemetry.update();

        sleep(1000);
        //=========================== UNHANG =======================================================
        drivetrain.unhang();

        //=================== HIT MINERAL AND GO TO DEPOT ==========================================
        if (cubePos.equals("left")) {
            drivetrain.turnPD(-28 + offset, .55, .6, 3);
            intake.retract();
            sleep(250);
            intake.intakeMotorStop();
            intake.pivotDown();
            intake.collectionIn();
            sleep(250);
            drivetrain.moveGyro(.3, 30, 30 + offset);
            intake.collectionStop();
            intake.pivotMid();
            drivetrain.arcturnPD(-55 + offset, .8, 1.2, 4);
            drivetrain.moveGyro(.5, 5, -45 + offset);
            drivetrain.arcturnPD(45 + offset, .8, 1.2, 4);
            drivetrain.moveGyro(.5, 10, 45 + offset);
            sleep(500);
            drivetrain.move(-.3, 4);
        } else if (cubePos.equals("center")) {
            intake.retract();
            sleep(250);
            intake.intakeMotorStop();
            intake.pivotDown();
            sleep(250);
            intake.collectionIn();
            drivetrain.moveGyro(.6, 55, offset);
            intake.collectionStop();
            intake.pivotMid();
            sleep(500);
            drivetrain.turnPD(50 + offset, .38, .45, 4);
        } else {
            drivetrain.turnPD(28 + offset, .55, .6, 3);
            intake.retract();
            sleep(250);
            intake.intakeMotorStop();
            intake.pivotDown();
            intake.collectionIn();
            sleep(250);
            drivetrain.moveGyro(.3, 42, 30 + offset);
            intake.collectionStop();
            intake.pivotMid();
            drivetrain.arcturnPD(-55 + offset, .8, 1.2, 4);
            drivetrain.moveGyro(.5, 5, -45 + offset);
            drivetrain.arcturnPD(45 + offset, .8, 1.2, 4);
            sleep(500);
            drivetrain.move(-.3, 4);
        }

        //==================================== MARKER DEPOSIT ======================================
        sleep(250);
        marker.Down();
        sleep(250);

        //======================================= PARK =============================================
        drivetrain.wallRollL(-1, 60);
        Thread.sleep(500);
        drivetrain.wallRollL(-.4, 16);
    }
}

