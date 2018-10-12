package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.MarkerKicker;
import GrimSkyLibraries.Sensors;

@Autonomous(name = "Blue Depot Auto", group = "LinearOpMode")
public class BlueDepotAuto extends LinearOpMode{
    private Drivetrain drivetrain;
    private Sensors sensors;
    private MarkerKicker kicker;
    private Lift lift;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        kicker = new MarkerKicker(this);

        waitForStart();

        // Detach
        //lift.detach();
        // Turn -20/0/20 degrees depending on sample location

        // Move 29/27/29 inches depending on sample location
        drivetrain.move(.2, 1500);
        // If 1: Move 7.5 inches, turn to +45, Move 15 inches, Drop Marker off, Move 85 inches
        }

}
