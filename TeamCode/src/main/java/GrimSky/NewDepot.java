package GrimSky;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.Main;

import java.util.ArrayList;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.GoldDetectorVuforia;
import GrimSkyLibraries.Intake;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;

//C:\Users\Avi\AppData\Local\Android\sdk\platform-tools

@Autonomous(name = "NewDepot", group = "LinearOpMode")
public class NewDepot extends LinearOpMode {

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

        int offset = 135;

        while (!isStarted()) {
            cubePos = sample.getCubePos();
            telemetry.addData("cubePos: ", cubePos);
            telemetry.update();
        }

        telemetry.addData("cubePos: ", cubePos);
        telemetry.update();

        waitForStart();

        cubePos = "left";


        //=========================== UNHANG =======================================================
        drivetrain.unhang();

        //=========================== INITIAL TURN AND SCAN ========================================
        sleep(1000);

        //=================== HIT MINERAL AND GO TO DEPOT ==========================================
        if (cubePos.equals("left")) {
            drivetrain.turnPD(-25 + offset, .55, .6, 4);
            intake.pivotDown();
            intake.collectionIn();
            intake.move(.8, 25);
//            sleep(500);
//            drivetrain.move(.3, 46.5);
//            intake.collectionStop();
//            intake.pivotMid();
//            //intake.intakeStop();
//            sleep(500);
//            drivetrain.turnPD(50 + offset, .33, .57, 4);
//            sleep(500);
//            drivetrain.move(.4, 28);
//            sleep(500);
//            drivetrain.move(-.2, 3);
//        } else if (cubePos.equals("center")) {
            sleep(2000);
            intake.move(-.4, -15);

//            drivetrain.turnPI(offset, .27, 0.02, 2);
//            intake.pivotDown();
//            intake.collectionOut();
//            sleep(500);
//            drivetrain.move(.6, 55);
//            intake.collectionStop();
//            intake.pivotMid();
//            //intake.intakeStop();
//            sleep(500);
//            drivetrain.turnPD(50 + offset, .38, .45, 4);

        } else {
            drivetrain.turnPD(35 + offset, .4, .5, 4);
            intake.pivotDown();
            intake.collectionOut();
            sleep(250);
            drivetrain.move(.3, 46.5);
            intake.collectionStop();
            intake.pivotMid();
            sleep(250);
            drivetrain.turnPD(-45 + offset, .35, .55, 4);
            sleep(250);
            drivetrain.move(1, 10);
            sleep(250);
            drivetrain.move(-.2, 1.5);
            drivetrain.turnPD(50 + offset, .32, .6, 4);
        }

        //==================================== MARKER DEPOSIT ======================================
        sleep(250);
        marker.Down();
        sleep(250);

        //======================================= PARK =============================================
//        drivetrain.wallRollL(-1, 55);
//        Thread.sleep(500);
//        drivetrain.wallRollL(-.4, 14);

    }


    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

        }
    }

}
