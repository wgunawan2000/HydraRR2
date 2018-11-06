package GrimSky;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

import GrimSkyLibraries.Drivetrain;
import GrimSkyLibraries.Lift;
import GrimSkyLibraries.Marker;
import GrimSkyLibraries.Sensors;
import for_camera_opmodes.LinearOpModeCamera;

@Autonomous(name = "Depot Auto", group = "LinearOpMode")
public class DepotAuto extends LinearOpModeCamera {
    int ds2 = 1;
    int numPics = 0;
    double avgX = 0;
    int validPix = 0;
    String pos = "notFound";
    int red = 0, blue = 0;
    int offset;
    private Drivetrain drivetrain;
    private Sensors sensors;
    private String cubePos;
    private Marker marker;
    private Lift lift;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        marker = new Marker(this);
        lift = new Lift(this);
        startCamera();
        //offset = difference between angle of lander and lander tape (assuming lander tape is straight)
        offset = 5;

        waitForStart();

        //=========================== UNHANG =======================================================
        drivetrain.unhang();

        //=========================== REPOSITION ===================================================
        lift.move(-4, 160);

        //=========================== INITIAL TURN AND SCAN ========================================
        drivetrain.turnPI(10 + offset, .8, 1.0, 2);
        sleep(1000);
        telemetry.addData("first turn",  " done");
        telemetry.update();

        //============================ SAMPLE ======================================================
        cubePos = getCubePos();

        //=================== HIT MINERAL AND GO TO DEPOT ==========================================
        if (cubePos.equals("left")) {
            drivetrain.turnPI(-32 + offset, .26, 0.18, 4);
            sleep(500);
            drivetrain.move(.4, 46.5);
            sleep(500);
            drivetrain.turnPI(45 + offset, .15, 0.20, 4);
            sleep(500);
            drivetrain.move(.6, 25.5);
        } else if (cubePos.equals("center")) {
            drivetrain.turnPI(0 + offset, .6, 1.1, 2);
            sleep(500);
            drivetrain.move(.4, 63);
            sleep(500);
            drivetrain.turnPI(45 + offset, .25, 0.2, 4);

        } else {
            drivetrain.turnPI(33 + offset, .6, 0.12, 2);
            sleep(500);
            drivetrain.move(.6, 35.5);
            sleep(500);
            drivetrain.turnPI(-45 + offset, .16, 0.1, 4);
            sleep(500);
            drivetrain.move(.6, 28.5);
            sleep(500);
            drivetrain.turnPI(50 + offset, .28, .21, 4);
        }

        //==================================== MARKER DEPOSIT ======================================
        sleep(500);
        marker.Down();
        sleep(500);
        marker.Up();
        sleep(500);

        //======================================= PARK =============================================
        drivetrain.wallRollL(-.5, 80);
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

            int cubePixelCount = 0;
            ArrayList<Integer> xValues = new ArrayList<>();
            for (int x = (int) ((2.0 / 3) * rgbImage.getWidth()); x < rgbImage.getWidth(); x += 2) {
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

            if (cubePixelCount < 30) pos = "left";

            else if (avgX > .6 * rgbImage.getHeight()) pos = "center";

            else pos = "right";
        }
        return pos;
    }
}

