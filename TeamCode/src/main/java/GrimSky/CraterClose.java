package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Sensors;

@Autonomous(name = "Crater Close", group = "LinearOpMode")

public class CraterClose extends LinearOpMode{
    private Drivetrain drivetrain;
    private Sensors sensors;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);

        waitForStart();

        drivetrain.move(.3, 600);
        sleep(1000);

        drivetrain.move(-.3, 250);
        sleep(1000);

        drivetrain.turnPI(-90, .5, .05);
        sleep(1000);
        telemetry.update();

        drivetrain.distanceRMove(.4, 65);
        sleep(500);

        drivetrain.turnPI(-110, .5, .05);
        sleep(500);
        telemetry.update();

        drivetrain.distanceMove(.3, 40);

        drivetrain.wallRollL(-.3, 2000);

    }
}

