package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import GrimSkyLibraries.Drivetrain;
@Autonomous(name = "Blue Crater Close", group = "LinearOpMode")

public class BlueCraterClose extends LinearOpMode{
    private Drivetrain drivetrain;
    //    private Sensors sensors;
//    private Marker dropper;
//    private Lift lift;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
//        sensors = new Sensors(this);
//        dropper = new Marker(this);

        waitForStart();
        // Detach
        //lift.detach();
        drivetrain.move(.3, 600);
        sleep(1000);
        //back up
        drivetrain.move(-.3, 850);
        //turn to depot
        drivetrain.turn(.3, -90);
        sleep(1000);
        drivetrain.distanceRMove(.5, 20);
        sleep(500);
        drivetrain.turn(.3, -110);
        sleep(500);
        drivetrain.distanceMove(.3, 40);
        //deposit marker

        //move back to close crater
        drivetrain.move(-.3, 3000);
    }
}

