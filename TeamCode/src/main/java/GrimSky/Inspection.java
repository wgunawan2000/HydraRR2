package GrimSky;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.Main;

import java.util.ArrayList;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;
import for_camera_opmodes.LinearOpModeCamera;

@Autonomous(name = "NoHangAuto", group = "LinearOpMode")
public class Inspection extends LinearOpMode{

    private Drivetrain drivetrain;
    private Marker marker;

    @Override
    public void runOpMode() throws InterruptedException{
        drivetrain = new Drivetrain(this);
        marker = new Marker(this);

        marker.Up();
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
