package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;

@Autonomous(name = "Blue Depot Auto", group = "LinearOpMode")
public class BlueDepotAuto extends LinearOpMode{
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
        // testing gitHub - arya
        // Detach
        //lift.detach();
        // Turn -20/0/20 degrees depending on sample location

        // Move 29/27/29 inches depending on sample location
        drivetrain.move(.3, 1700);
        sleep(1000);
        drivetrain.turn(0, -20);
//        drivetrain.move(.2, 1000);
//        // If 1: Move 7.5 inches, turn to +45, Move 15 inches, Drop Marker off, Move 85 inches
//        drivetrain.turn(.5, -25);
//        drivetrain.move(.3, 700);
        }
}
