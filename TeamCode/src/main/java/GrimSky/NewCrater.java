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

@Autonomous(name = "NewCrater", group = "LinearOpMode")
public class NewCrater extends LinearOpMode {

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

        int offset = -135;

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
            intake.move(.8, 22, 3);
            sleep(1000);
            intake.move(.8, 15, 3);
            sleep(1000);
            intake.pivotMid();
        } else if (cubePos.equals("center")) {
            drivetrain.turnPD(0 + offset, .8, .3, 2);
            intake.pivotDown();
            intake.collectionIn();
            intake.move(.8, 18, 3);
            sleep(1000);
            intake.move(.8, 16, 3);
            sleep(1000);
            intake.pivotMid();
        } else {
            drivetrain.turnPD(27 + offset, .65, .6, 4);
            intake.pivotDown();
            intake.collectionIn();
            intake.move(.8, 22, 3);
            sleep(1000);
            intake.move(.8, 15, 3);
            sleep(1000);
            intake.pivotMid();
        }
        drivetrain.move(.4, 15);
    }
}
