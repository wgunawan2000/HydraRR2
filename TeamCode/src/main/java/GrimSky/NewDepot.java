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


        //=========================== UNHANG =======================================================
        drivetrain.unhang();
        sleep(1000);

        //======================= COLLECT MINERAL AND TURN =========================================
        if (cubePos.equals("left")) {
            drivetrain.turnPD(-27 + offset, .65, .6, 4);
            intake.pivotDown();
            intake.collectionIn();
            intake.move(.8, 22);
            sleep(1000);
            intake.move(.8, 8);
            sleep(1000);
            intake.pivotMid();
            intake.move(-.8, 31);
            sleep(1000);
            drivetrain.turnPD(-46 + offset, .8, .75, 3);

        } else if (cubePos.equals("center")) {
            drivetrain.turnPD(0 + offset, .8, .3, 2);
            intake.pivotDown();
            intake.collectionIn();
            intake.move(.8, 18);
            sleep(1000);
            intake.move(.8, 8);
            sleep(1000);
            intake.pivotMid();
            intake.move(-.8, 23);
            sleep(1000);
            drivetrain.turnPD(-46 + offset, .55, .5, 4);
        } else {
            drivetrain.turnPD(27 + offset, .65, .6, 4);
            intake.pivotDown();
            intake.collectionIn();
            intake.move(.8, 20);
            sleep(1000);
            intake.move(.8, 10);
            sleep(1000);
            intake.pivotMid();
            intake.move(-.8, 29);
            sleep(1000);
            drivetrain.turnPD(-46 + offset, .45, .4, 5);
        }

        //==================================== MARKER DEPOSIT ======================================
        sleep(1000);
        drivetrain.moveGyro(.5, 45, -46 + offset);
        drivetrain.arcturnPD(-135 + offset, .8, .8, 2);
        sleep(1000);
        drivetrain.wallRollR(-.5, 65);
        lift.moveMarker(1, 500);
        lift.markerOut();
        sleep(500);
        lift.basketsInit();
        lift.setPower(0);

        //======================================= PARK =============================================
        drivetrain.wallRollR(1, 80);
        Thread.sleep(500);
        intake.move(1, 25);
        intake.pivotDown();


    }


    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

        }
    }

}