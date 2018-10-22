package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;

@Autonomous(name = "Depot Auto", group = "LinearOpMode")
public class DepotAuto extends LinearOpMode {
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
        drivetrain.move(.4, 850);
        sleep(1000);
        // drop off marker
        drivetrain.turn(.5, 45);
        sleep(1000);
        drivetrain.turn(.5, -115);
        sleep(1000);
        drivetrain.move(.4, 700);
        drivetrain.wallRollR(.4, 700);

        }
    }

