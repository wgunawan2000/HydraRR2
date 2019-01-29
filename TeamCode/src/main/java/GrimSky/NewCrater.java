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

        cubePos = "left";


        //=========================== UNHANG =======================================================
        drivetrain.unhang();

        //=========================== INITIAL TURN AND SCAN ========================================
        sleep(1000);

        //=================== HIT MINERAL AND GO TO DEPOT ==========================================
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
            drivetrain.turnPD(0 + offset, .25, .3, 4);
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
        drivetrain.moveGyro(.5, 45, -45 + offset);
        drivetrain.arcturnPD(-135 + offset, .8, .8, 4);
        sleep(1000);
        drivetrain.wallRollR(-.5, 65);
        lift.move(1, 500);
        lift.outBackR();
        sleep(1001);
        lift.basketsInit();
        drivetrain.wallRollR(1, 80);
        Thread.sleep(500);
        intake.move(1, 25);
        intake.pivotDown();
    }
}
