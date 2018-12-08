package GrimSky;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.Main;

import java.util.ArrayList;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;
import for_camera_opmodes.LinearOpModeCamera;
import GrimSkyLibraries.Intake;

@Autonomous(name = "Crater", group = "LinearOpMode")
public class Crater extends LinearOpModeCamera {
    int ds2 = 1;
    int numPics = 0;
    double avgX = 0;
    int validPix = 0;
    String pos = "notFound";
    int red = 0, blue = 0;
    int offset;
    int distInc;
    int cubePixelCount = 0;
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
        int offset = 7;

        waitForStart();

        //=========================== UNHANG =======================================================
        drivetrain.unhang();

        //=========================== INITIAL TURN AND SCAN ========================================
        drivetrain.turnPI(10 + offset, .27, 0.8, 4);
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
            drivetrain.move(.3, 23);
            sleep(500);
            drivetrain.move(-.4, 3);
            drivetrain.turnPD(-90, .34, .5, 4);
        } else if (cubePos.equals("center")) {
            distInc += 4;
            drivetrain.turnPI(offset - 1, .27, 0.8, 2);
            sleep(500);
            intake.intakeIn();
            drivetrain.move(.3, 18);
            sleep(500);
            drivetrain.move(-.4, 2);
            drivetrain.turnPD(-90, .32, .6, 4);
        } else {
            distInc += 8;
            drivetrain.turnPD(35 + offset, .4, .5, 4);
            sleep(500);
            intake.intakeIn();
            drivetrain.move(.3, 23);
            sleep(500);
            drivetrain.move(-.4, 3);
            drivetrain.turnPD(-90, .3, .65, 4);
        }
        sleep(500);
        drivetrain.move(1, 25 + distInc);
        intake.intakeStop();
        sleep(500);
        drivetrain.turnPD(-120, .38, .38, 3);
        sleep(500);

        //==================================== MARKER DEPOSIT ======================================
        drivetrain.wallRollR(.8, 30);
        sleep(500);
        drivetrain.move(-.2, 3);
        marker.Down();
        sleep(1000);

        //======================================= PARK =============================================
        drivetrain.wallRollR(-1, 55);
        Thread.sleep(1000);
        drivetrain.wallRollR(-.6, 10);
        stopCamera();
    }

    //=================================== LOCAL METHODS ============================================
    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

        }
    }

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

