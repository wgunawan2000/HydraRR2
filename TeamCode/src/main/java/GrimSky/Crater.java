package GrimSky;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.Main;

import java.util.ArrayList;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.GoldDetectorVuforia;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;

import GrimSkyLibraries.Intake;

import static GrimSky.GrimSkyOpMode.liftHeight;

@Autonomous(name = "Crater", group = "LinearOpMode")
public class Crater extends LinearOpMode {

    private Drivetrain drivetrain;
    private Sensors sensors;
    private String cubePos;
    private Marker marker;
    private Lift lift;
    private Intake intake;
    private GoldDetectorVuforia sample;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        marker = new Marker(this);
        lift = new Lift(this);
        intake = new Intake(this);
        sample = new GoldDetectorVuforia(this);
        int offset = -135;

        liftHeight = 1300;

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
        sleep(500);

        //=================== HIT MINERAL AND GO TO DEPOT ==========================================


        if (cubePos.equals("left")) {
            drivetrain.turnPD(-27 + offset, .75, .65, 2);
            intake.pivotDown();
            intake.collectionIn();
            intake.move(1, 22, 1.25);
            sleep(500);
            intake.move(1, 25, 1.5);
            sleep(500);
            intake.pivotMid();
            intake.move(-1, 45, 2);
            drivetrain.turnPD(-46 + offset, .8, .75, 2);

        } else if (cubePos.equals("center")) {
            drivetrain.turnPD(0 + offset, .8, .3, 2);
            intake.pivotDown();
            intake.collectionIn();
            intake.move(1, 18, 1.25);
            sleep(500);
            intake.move(1, 16, 1);
            sleep(500);
            intake.pivotMid();
            intake.move(-1, 36, 2);
            drivetrain.turnPD(-46 + offset, .55, .5, 2);
        } else {
            drivetrain.turnPD(27 + offset, .75, .65, 2);
            intake.pivotDown();
            intake.collectionIn();
            intake.move(1, 22, 1.25);
            sleep(500);
            intake.move(1, 25, 1.5);
            sleep(500);
            intake.pivotMid();
            intake.move(-1, 45, 2);
            drivetrain.turnPD(-46 + offset, .45, .4, 2);
        }

        sleep(500);
        drivetrain.moveGyro(.65, 55, -46 + offset);
        sleep(500);
        drivetrain.turnPD(50 + offset, .4, .4, 2);
        sleep(500);
        drivetrain.wallRollL(-1, 80);
        lift.out();
        sleep(750);

        //======================================= PARK =============================================
        drivetrain.wallRollL(1, 80);
        sleep(500);
        intake.move(1, 25, 2);
        intake.pivotDown();
    }

}

