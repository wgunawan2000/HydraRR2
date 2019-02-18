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

        liftHeight = 1220;

        waitForStart();

        while (!isStarted()) {
            cubePos = sample.getCubePos();
        }

        //=========================== UNHANG =======================================================
        drivetrain.unhang();

        //=================== HIT MINERAL AND GO TO DEPOT ==========================================
        if (cubePos.equals("left")) {
            drivetrain.turnPD(-25 + offset, .38, .39, 4);
            sleep(500);
            drivetrain.move(.3, 26);
            sleep(500);
            drivetrain.move(-.4, 1);
            drivetrain.arcturnBackPD(-90 + offset, .8, 1.2, 4);
            sleep(500);
            drivetrain.moveGyro(.5, 10, -90 + offset);
        } else if (cubePos.equals("center")) {
            drivetrain.moveGyro(.3, 25, offset);
            sleep(500);
            drivetrain.move(-.4, 1);
            drivetrain.arcturnBackPD(-90 + offset, .8, 1.2, 4);
            sleep(500);
            drivetrain.moveGyro(.5, 25, -90 + offset);
        } else {
            drivetrain.turnPD(25 + offset, .38, .39, 4);
            sleep(500);
            drivetrain.move(.3, 26);
            sleep(500);
            drivetrain.arcturnBackPD(-85 + offset, .8, 1.2, 4);
            sleep(500);
            drivetrain.moveGyro(.5, 35, -90 + offset);
        }

        drivetrain.arcturnPD(-45 + offset, .8, 1.2, 3);
        drivetrain.arcturnPD(-130 + offset, .8, 1.2, 4);
        drivetrain.wallRollR(.5, 60);
        sleep(500);
        drivetrain.move(-.3, 4);
        sleep(250);
        marker.Down();
        sleep(250);

        //======================================= PARK =============================================
        drivetrain.wallRollR(-1, 60);
        Thread.sleep(1000);
        drivetrain.wallRollR(-.6, 10);
    }

}

