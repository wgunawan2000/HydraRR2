package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;

@Autonomous(name = "Gyro Test", group = "LinearOpMode")
public class GyroTest extends LinearOpMode {
    private Drivetrain drivetrain;
    private Sensors sensors;

    //    private Marker dropper;
//    private Lift lift;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
//        dropper = new Marker(this);

        waitForStart();
        // Detach
        //lift.detach();
//        // Turn -20/0/20 degrees depending on sample location
//
        while(runtime.seconds() < 30){
            telemetry.addData("yaw", sensors.getGyroYaw());
            telemetry.update();
        }
    }
}

