package GrimSky;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.Main;

import java.lang.reflect.Array;
import java.util.ArrayList;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.GoldDetectorVuforia;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;

import GrimSkyLibraries.Intake;

@Autonomous(name = "MotionProfileTest", group = "LinearOpMode")
public class MotionProfileTest extends LinearOpMode {

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

        waitForStart();

        runtime.reset();
        lift.resetEncoder();
        double prevTime, currTime = 0;
        double maxVelocity = 0; //7000
        double currVelocity = 0;
        double prevVelocity = 0;
        double maxAccel = 0; //14000
        double currAccel = 0;
        int currEncoder = 0;
        int prevEncoder = 0;
        double accelTime = 0;
        double prevAccel = 0;
        double dT =.05;

        ArrayList<Double> dTAvg= new ArrayList<Double>();


        while(runtime.seconds() < 1.001){
            prevTime = currTime;
            lift.setPower(1);
            prevEncoder = currEncoder;
            currEncoder = lift.getEncoder();
            prevAccel = currAccel;

            prevVelocity = currVelocity;
            currVelocity = (currEncoder - prevEncoder) / dT;
            currAccel = (currVelocity - prevVelocity) / dT;

            if (currVelocity > maxVelocity)
                maxVelocity = currVelocity;

            if (currAccel > maxAccel)
                maxAccel = currAccel;
            currTime = runtime.seconds();
            dTAvg.add(dT = currTime - prevTime);
        }
        lift.setPower(0);
        telemetry.addLine("Vmax " + maxVelocity);
        telemetry.addLine("Amax " + maxAccel);
        telemetry.addData("time", dTAvg.size());
        telemetry.update();

    }

}

