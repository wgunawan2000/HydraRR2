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

    boolean bad = true;

    @Override
    public void runOpMode() throws InterruptedException{

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        lift = new Lift(this);
        intake = new Intake(this);

        waitForStart();

        while (bad) {
            telemetry.addLine("FUCK U JUDGES");
            telemetry.addLine("INSPIRE NOW OR I QUIT");
            telemetry.addLine("SUCK MY DICK");
            telemetry.update();

        }

        drivetrain.resetEncoders();
        drivetrain.startMotors(.3, .3);
        sleep(2000);
        drivetrain.stopMotors();
        sleep(5000);
        sleep(1000);sleep(1000);
        drivetrain.startMotors(.25,-.25);
        sleep(1250);
        drivetrain.stopMotors();
        sleep(5000);
        drivetrain.startMotors(.3, .3);
        sleep(3000);
        drivetrain.stopMotors();

    }
}
