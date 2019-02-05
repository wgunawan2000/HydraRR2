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

@Autonomous(name = "NoHangAuto", group = "LinearOpMode")
public class Inspection extends LinearOpMode{

    private Drivetrain drivetrain;
    private Sensors sensors;
    private Marker marker;
    private Lift lift;
    private Intake intake;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException{

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        marker = new Marker(this);
        lift = new Lift(this);
        intake = new Intake(this);

        marker.Up();

//        while (!isStarted()){
//            intake.initMove(-.3);
//        }
//
//        intake.intakeMotorStop();

        waitForStart();

        drivetrain.resetEncoders();
            drivetrain.startMotors(.3, .3);
            sleep(2000);
            drivetrain.stopMotors();
            sleep(5000);
            marker.Down();
            sleep(1000);
            marker.Back();
            sleep(1000);
            drivetrain.startMotors(.25,-.25);
            sleep(1250);
            drivetrain.stopMotors();
            sleep(5000);
            drivetrain.startMotors(.3, .3);
            sleep(3000);
            drivetrain.stopMotors();

    }
}
