package GrimSky;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.Main;

import java.util.ArrayList;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Intake;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;
import for_camera_opmodes.LinearOpModeCamera;

//C:\Users\Avi\AppData\Local\Android\sdk\platform-tools

@Autonomous(name = "Depot", group = "LinearOpMode")
public class Depot extends LinearOpModeCamera {

    //making sure github still works
    private Drivetrain drivetrain;
    private Sensors sensors;
    private String cubePos;
    private Marker marker;
    private Lift lift;
    private Intake intake;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        marker = new Marker(this);
        lift = new Lift(this);
        intake = new Intake(this);

        startCamera();
        int offset = 5;

        waitForStart();

        //=========================== UNHANG =======================================================
        drivetrain.unhang();

        //=========================== INITIAL TURN AND SCAN ========================================
        drivetrain.turnPI(10 + offset, .27, 0.2, 4);
        sleep(1000);

        //============================ SAMPLE ======================================================
        cubePos = getCubePos();
        telemetry.addData("validPIX", cubePixelCount);
        telemetry.addData("pos", cubePos);
        telemetry.update();

        //=================== HIT MINERAL AND GO TO DEPOT ==========================================
        if (cubePos.equals("left")) {
            drivetrain.turnPD(-25 + offset, .38, .39, 4);
            sleep(500);
            intake.intakeIn();
            drivetrain.move(.3, 46.5);
            //intake.intakeStop();
            sleep(500);
            drivetrain.turnPD(50 + offset, .33, .57, 4);
            sleep(500);
            drivetrain.move(.4, 28);
            sleep(500);
            drivetrain.move(-.2, 2.5);

        } else if (cubePos.equals("center")) {
            drivetrain.turnPI(offset, .27, 0.2, 2);
            sleep(500);
            intake.intakeIn();
            drivetrain.move(.6, 55);
            //intake.intakeStop();
            sleep(500);
            drivetrain.turnPD(50 + offset, .38, .45, 4);

        } else {
            drivetrain.turnPD(35 + offset, .4, .5, 4);
            sleep(250);
            intake.intakeIn();
            drivetrain.move(.3, 46.5);
            //intake.intakeStop();
            sleep(250);
            drivetrain.turnPD(-45 + offset, .35, .55, 4);
            sleep(250);
            drivetrain.move(1, 10);
            sleep(250);
            drivetrain.move(-.2, 2.5);
            drivetrain.turnPD(50 + offset, .32, .6, 4);
        }

        //==================================== MARKER DEPOSIT ======================================
        intake.intakeStop();
        sleep(250);
        marker.Down();
        sleep(250);

        //======================================= PARK =============================================
        drivetrain.wallRollL(-1, 60);
        Thread.sleep(500);
        drivetrain.wallRollL(-.4, 16);

        stopCamera();
    }


    //=================================== LOCAL METHODS ============================================
    int ds2 = 1;
    int numPics = 0;
    double avgX = 0;
    int validPix = 0;
    String pos = "notFound";
    int red = 0, blue = 0;
    int offset;
    int cubePixelCount = 0;

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

        }
    }

    //returns left, right or center from the robot POV
    public String getCubePos() {
        if (imageReady()) { // only do this if an image has been returned from the camera

            numPics++;

            // get image, rotated so (0,0) is in the bottom left of the preview window
            Bitmap rgbImage;
            rgbImage = convertYuvImageToRgb(yuvImage, width, height, ds2);

            cubePixelCount = 0;
            ArrayList<Integer> xValues = new ArrayList<>();
            for (int x = (int) ((3.0 / 4) * rgbImage.getWidth()); x < rgbImage.getWidth(); x += 2) {
                for (int y = 0; y < rgbImage.getHeight(); y += 2) {
                    int pixel = rgbImage.getPixel(x, y);
                    red += red(pixel);
                    blue += blue(pixel);
                    if (red(pixel) >= 140 && green(pixel) > 100 && blue(pixel) <= 50) {
                        validPix++;
                        cubePixelCount++;
                        xValues.add(y);
                    }
                }

            }
            avgX = 0;
            for (int xCoor : xValues) {
                avgX += xCoor;
            }
            avgX /= xValues.size();

            if (cubePixelCount < 400) pos = "left";

            else if (avgX > .45 * rgbImage.getHeight()) pos = "center";

            else pos = "right";
        }
        return pos;
    }
}

