package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;

@Autonomous(name = "Blue Crater Auto", group = "LinearOpMode")
public class BlueCraterAuto extends LinearOpMode{
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
        // Turn -20/0/20 degrees depending on sample location

        // Move 29/27/29 inches depending on sample location
        drivetrain.move(.3, 1700);
        sleep(1000);
        //turn to depot
        drivetrain.turn(.3, -175);
        //move along the wall toward the depot
        drivetrain.move(.2, 1000);
        // turn toward enemy crater
        drivetrain.turn(.5, -45);
        // move to the enemy crater
        drivetrain.move(.3, 1000);
    }
}