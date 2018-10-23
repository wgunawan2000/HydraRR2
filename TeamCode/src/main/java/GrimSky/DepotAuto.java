package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;

@Autonomous(name = "Depot Auto", group = "LinearOpMode")
public class DepotAuto extends LinearOpMode {
    private Drivetrain drivetrain;
    private Sensors sensors;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);

        composeTelemetry();

        waitForStart();

        drivetrain.move(.4, 850);
        telemetry.update();
        sleep(1000);

        drivetrain.turnRight(.5, 45);
        telemetry.update();
        sleep(1000);

        drivetrain.turnLeft(.5, -115);
        telemetry.update();
        sleep(1000);

        drivetrain.move(.4, 700);
        drivetrain.wallRollR(.4, 700);

        }

    private void composeTelemetry() {
        telemetry.addData("yaw", sensors.getGyroYaw());
    }
}

