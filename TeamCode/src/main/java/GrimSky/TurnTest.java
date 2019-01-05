package GrimSky;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.ArrayList;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Intake;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;

@Autonomous(name = "TurnTest", group = "LinearOpMode")
public class TurnTest extends LinearOpMode {

    private Drivetrain drivetrain;
    private Sensors sensors;
    private String cubePos;
    private Marker marker;
    private Lift lift;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        marker = new Marker(this);
        lift = new Lift(this);
        double velX = 0;
        double lastVelX;
        double velMax = 0;
        double acc = 0;
        double accMax = 0;

        double lastTime = 0;
        double currTime = 0;
        double timeDiff = 0;

        double count = 0;

        waitForStart();
        runtime.reset();



            while (runtime.seconds() < 15) {

                if(count == 100) {

                    lastTime = currTime;
                    currTime = runtime.seconds();

                    timeDiff = currTime - lastTime;
                }


                drivetrain.startMotors(-1,1);
                lastVelX = velX;
                velX = sensors.gyro.getAngularVelocity().toAngleUnit(AngleUnit.DEGREES).zRotationRate;
                velMax = velMax < velX ? velX : velMax;

                if(count == 100){

                    acc = (velX - lastVelX) / timeDiff;
                    accMax = accMax < acc ? acc : accMax;
                    count = 0;
                }


                telemetry.addData("velX: ", velX);
                telemetry.addData("velMax: ", velMax);


                telemetry.addData("acc: ", acc);
                telemetry.addData("accMax ", accMax);

//                telemetry.addData("accZ: ", accZ);
//
//                telemetry.addData("velX: ", velX);
//                telemetry.addData("velY: ", velY);
//                telemetry.addData("velZ: ", velZ);
                telemetry.update();

                count++;
            }

            drivetrain.stopMotors();

            while (runtime.seconds() < 75) {

                lastTime = currTime;
                currTime = runtime.seconds();

                timeDiff = currTime - lastTime;

                drivetrain.stopMotors();
                lastVelX = velX;
                velX = sensors.gyro.getAngularVelocity().toAngleUnit(AngleUnit.DEGREES).zRotationRate;
                velMax = velMax < velX ? velX : velMax;

                acc = (velX - lastVelX) / timeDiff;
                accMax = accMax < acc ? acc : accMax;


                telemetry.addData("velX: ", velX);
                telemetry.addData("velMax: ", velMax);

                telemetry.addData("acc: ", acc);
                telemetry.addData("accMax ", accMax);

//                telemetry.addData("accZ: ", accZ);
//
//                telemetry.addData("velX: ", velX);
//                telemetry.addData("velY: ", velY);
//                telemetry.addData("velZ: ", velZ);
                telemetry.update();
            }

    }
}

