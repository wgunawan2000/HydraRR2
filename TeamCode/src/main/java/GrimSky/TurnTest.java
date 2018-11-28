package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Sensors;

/**
 * Created by Avi on 11/19/2018.
 */

@Autonomous(name = "Turn Test", group = "LinearOpMode")
public class TurnTest extends LinearOpMode {
    private Drivetrain drivetrain;
    private Sensors sensors;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);

        waitForStart();

        double Ku = 1.0;
        double Tu = .5;
        //Ku = 1.0, Tu = .5
//            drivetrain.turnZN(90, 1.0, 0);
//        //P
//        drivetrain.turnPID(90, .8, 0, 0, 10);
//
//        //PI
//        drivetrain.turnPID(90, .72, .6, 0, 10);
//
//        //PD
//        drivetrain.turnPID(90, 1.28, 0, .09, 10);
//
//        //classic PID
//        drivetrain.turnPID(90, .96, .36, .09, 10);
//
//        //Pessen Integral Rule
//        drivetrain.turnPID(90, 1.12, .29, .11, 10);
//
//        //Some overshoot
//        drivetrain.turnPID(90, .53, .36, .24, 10);
//
//        //No overshoot
//          drivetrain.turnPID(-90, .32, .36, .24, 10);

    }
}
