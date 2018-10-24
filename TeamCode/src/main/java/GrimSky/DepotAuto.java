package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sampler;
import GrimSkyLibraries.Sensors;

@Autonomous(name = "Depot Auto", group = "LinearOpMode")
public class DepotAuto extends LinearOpMode {
    private Drivetrain drivetrain;
    private Sensors sensors;
    private Sampler sampler;
    private String cubePos;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        sampler = new Sampler(this);

        composeTelemetry();

        waitForStart();

        //turn to see right two minerals of sample
        drivetrain.turnPI(10);

        //scan sample and turn
        cubePos = sampler.getCubePos();
        telemetry.update();
        if (cubePos.equals("left")){
            drivetrain.turnPI(-20);
        }
        if(cubePos.equals("center")){
            drivetrain.turnPI(0);
        }
        else{
            drivetrain.turnPI(20);
        }

        drivetrain.move(.4, 850);
        telemetry.update();
        sleep(1000);

        drivetrain.turnPI(45);
        telemetry.update();
        sleep(1000);

        drivetrain.turnPI(-115);
        telemetry.update();
        sleep(1000);

        drivetrain.move(.4, 700);
        drivetrain.wallRollR(.4, 700);
    }

    //adds relevant telemetry statements
    private void composeTelemetry() {
        telemetry.addData("yaw", sensors.getGyroYaw());
        telemetry.addData("cubePos", cubePos);
    }

}

