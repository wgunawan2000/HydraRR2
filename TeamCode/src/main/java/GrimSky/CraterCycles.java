package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.GoldDetectorVuforia;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Sensors;

import GrimSkyLibraries.Intake;

import static GrimSky.GrimSkyOpMode.liftHeight;

@Autonomous(name = "OP Crater", group = "LinearOpMode")
public class CraterCycles extends LinearOpMode {

    private Drivetrain drivetrain;
    private Sensors sensors;
    private String cubePos;
    private Lift lift;
    private Intake intake;
    private GoldDetectorVuforia sample;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        lift = new Lift(this);
        intake = new Intake(this);
        sample = new GoldDetectorVuforia(this);
        int offset = -135;

        liftHeight = 2150;

        while (!isStarted()) {
            cubePos = sample.getCubePos();
            telemetry.addData("cubePos: ", cubePos);
            telemetry.update();
        }
        telemetry.addData("cubePos: ", cubePos);
        telemetry.update();

        waitForStart();


        //=========================== UNHANG =======================================================
        drivetrain.unhang();
        sleep(500);
        lift.setBrake();
        intake.pivotMid();
        intake.collectionSlow();
        //=================== HIT MINERAL AND GO TO DEPOT ==========================================
        if (cubePos.equals("left")) {
            drivetrain.turnPD(-27 + offset, .75, .65, 2);
            intake.pivotDown();
            intake.move(1, 23, 1.25);
            sleep(500);
            intake.pivotUp();
            intake.collectionStop();
            intake.move(-1, 23, 1.25);
            drivetrain.turnPD(-46 + offset, .8, .75, 2);
        } else if (cubePos.equals("center")) {
            drivetrain.turnPD(0 + offset, .8, .3, 2);
            intake.pivotDown();
            intake.move(1, 23, 1.25);
            sleep(500);
            intake.pivotUp();
            intake.collectionStop();
            intake.move(-1, 23, 1.25);
            drivetrain.turnPD(-46 + offset, .55, .5, 2);
        } else {
            drivetrain.turnPD(27 + offset, .75, .65, 2);
            intake.pivotDown();
            intake.move(1, 23, 1.25);
            sleep(500);
            intake.pivotUp();
            intake.collectionStop();
            intake.move(-1, 23, 1.25);
            drivetrain.turnPD(-46 + offset, .45, .4, 2);
        }
        sleep(500);
        drivetrain.moveGyro(.5, 40, -46 + offset);
        drivetrain.arcturnPD(-130 + offset, .8, .8, 2);
        sleep(500);
        drivetrain.moveGyro(.5, 12, -130 + offset);
        intake.move(1, 40, 2);
        intake.pivotDown();
        intake.collectionOut();
        sleep(500);
        intake.pivotMid();
        intake.move(-1, 40, 2);
        drivetrain.moveGyro(-.5, 8, -130 + offset);
        drivetrain.turnPD(-80 + offset, .45, .45, 2);
        drivetrain.moveGyro(-.65, 32, -80 + offset);
        drivetrain.turnPD(0 + offset, .47, .42, 2);
        intake.move(1, 35, 1.5);
        intake.collectionIn();
        intake.pivotDown();

        //======================================= PARK =============================================
    }

}

