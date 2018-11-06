//package GrimSky;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import GrimSkyLibraries.Drivetrain;
//import GrimSkyLibraries.Lift;
//import GrimSkyLibraries.Marker;
//import GrimSkyLibraries.Sensors;
//
//@Autonomous(name = "Turn Test", group = "LinearOpMode")
//public class TurnTest extends LinearOpMode {
//    private Drivetrain drivetrain;
//    private Sensors sensors;
//    private ElapsedTime runtime = new ElapsedTime();
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//        drivetrain = new Drivetrain(this);
//        sensors = new Sensors(this);
//
//        waitForStart();
//
//        drivetrain.turnPI(-90, .5, .05);
//        Thread.sleep(2000);
//        drivetrain.turnPI(0, .5, .05);
//    }
//}
//
