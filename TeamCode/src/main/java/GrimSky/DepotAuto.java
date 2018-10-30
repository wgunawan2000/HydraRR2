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
    private Marker marker;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        sampler = new Sampler(this);
        marker = new Marker(this);
        waitForStart();

        //turn to see right two minerals of sample
        drivetrain.turnPI(10, .7, .05);

        Thread.sleep(2000);
        //scan sample and turn
        cubePos = sampler.getCubePos();

        telemetry.addData("cubePos: ", cubePos);
        telemetry.update();
        if (cubePos.equals("left")){
            drivetrain.turnPI(-32, .7, .05);
        }
        else if(cubePos.equals("center")){
            drivetrain.turnPI(0, .7, .05);
        }
        else{
            drivetrain.turnPI(32, .7, .05);
        }

        drivetrain.move(.4, 950);
//        telemetry.update();
        sleep(1000);

        drivetrain.turnPI(35, .6, .05);
        marker.Down();
        marker.Up();
//        telemetry.update();
        sleep(1000);

        drivetrain.turnPI(80, .3, .1);
//        telemetry.update();
        sleep(1000);

        drivetrain.move(-.4, 700);
        drivetrain.wallRollL(-.4, 800);
    }

}

