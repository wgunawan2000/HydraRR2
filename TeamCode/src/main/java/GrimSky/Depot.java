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

import static GrimSky.GrimSkyOpMode.liftHeight;

//C:\Users\Avi\AppData\Local\Android\sdk\platform-tools

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

        int offset = 135;

        while (!isStarted()) {
            cubePos = sample.getCubePos();
            telemetry.addData("cubePos: ", cubePos);
            telemetry.update();
        }

        telemetry.addData("cubePos: ", cubePos);
        telemetry.update();

        liftHeight = 1100;

        waitForStart();


        //=========================== UNHANG =======================================================
        drivetrain.unhang();
        sleep(1000);

        //======================= COLLECT MINERAL AND TURN =========================================
        if (cubePos.equals("left")) {
            drivetrain.turnPD(-27 + offset, .75, .65, 2);
            intake.pivotDown();
            intake.collectionIn();
            intake.move(1, 22, 1.25);
            sleep(1000);
            intake.move(1, 25, 1.5);
            sleep(1000);
            intake.pivotMid();
            intake.move(-1, 45, 2);
            drivetrain.turnPD(-46 + offset, .8, .75, 3);

        } else if (cubePos.equals("center")) {
            drivetrain.turnPD(0 + offset, .8, .3, 2);
            intake.pivotDown();
            intake.collectionIn();
            intake.move(1, 18, 1.25);
            sleep(1000);
            intake.move(1, 16, 1);
            sleep(1000);
            intake.pivotMid();
            intake.move(-1, 36, 2);
            drivetrain.turnPD(-46 + offset, .55, .5, 3);
        } else {
            drivetrain.turnPD(27 + offset, .75, .65, 2);
            intake.pivotDown();
            intake.collectionIn();
            intake.move(1, 22, 1.25);
            sleep(1000);
            intake.move(1, 25, 1.5);
            sleep(1000);
            intake.pivotMid();
            intake.move(-1, 45, 2);
            drivetrain.turnPD(-46 + offset, .45, .4, 3);
        }

        //==================================== MARKER DEPOSIT ======================================
        sleep(1000);
        drivetrain.moveGyro(.5, 45, -46 + offset);
        drivetrain.arcturnPD(-135 + offset, .8, .8, 2);
        sleep(1000);
        drivetrain.wallRollR(-.5, 58);
        lift.out();
        sleep(1000);
        lift.basketsInit();

        //======================================= PARK =============================================
        drivetrain.wallRollR(1, 80);
        intake.move(1, 25, 2);
        intake.pivotDown();
    }

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

        }
    }

}
