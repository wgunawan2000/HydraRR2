package GrimSky;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Intake;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;
import for_camera_opmodes.LinearOpModeCamera;

@Autonomous(name = "TurnTest", group = "LinearOpMode")
public class TurnTest extends LinearOpModeCamera {

    private Drivetrain drivetrain;
    private Sensors sensors;
    private String cubePos;
    private Marker marker;
    private Lift lift;
    private Intake intake;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        marker = new Marker(this);
        lift = new Lift(this);
        intake = new Intake(this);

        waitForStart();

        drivetrain.turnPD(90, .4, .2, 10);
    }

}

