package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeoutException;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Intake;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Sensors;

@Autonomous(name = "NoHangAuto", group = "LinearOpMode")
public class Inspection extends LinearOpMode{

    private Drivetrain drivetrain;
    private Sensors sensors;
    private Lift lift;
    private Intake intake;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException{

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        lift = new Lift(this);
        intake = new Intake(this);

        waitForStart();
        drivetrain.unhang();
        sleep(1000);
        intake.extend(1);
        sleep(500);
        intake.extend(0);
        intake.pivotMid();
        intake.collectionOut();
        sleep(500);
        intake.collectionStop();
        intake.retract(1);
        sleep(500);
        drivetrain.startMotors(.3, .3);
        sleep(2000);
        drivetrain.stopMotors();
        sleep(1000);
        drivetrain.startMotors(.25,-.25);
        sleep(1000);
        drivetrain.stopMotors();
        sleep(5000);
        drivetrain.startMotors(.3, .3);
        sleep(3000);
        drivetrain.stopMotors();

    }
}
