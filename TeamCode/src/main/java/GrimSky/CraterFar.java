package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;

@Autonomous(name = "Crater Far", group = "LinearOpMode")
public class CraterFar extends LinearOpMode{
    private Drivetrain drivetrain;
    private Sensors sensors;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);

        composeTelemetry();

        waitForStart();

        drivetrain.move(.3, 600);
        sleep(1000);

        drivetrain.move(-.3, 250);
        sleep(1000);

        drivetrain.turnLeft(.3, -90);
        sleep(1000);
        telemetry.update();

        drivetrain.distanceRMove(.4, 65);
        sleep(500);

        drivetrain.turnLeft(.3, -110);
        sleep(500);
        telemetry.update();

        drivetrain.distanceMove(.3, 40);

        drivetrain.turnLeft(.3, -205);
        drivetrain.wallRollR(.3, 1600);
    }

    private void composeTelemetry() {
        telemetry.addData("yaw", sensors.getGyroYaw());
    }
}