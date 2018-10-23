package GrimSky;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.beans.IndexedPropertyChangeEvent;
import java.util.ArrayList;

import for_camera_opmodes.OpModeCamera;

/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */

@TeleOp(name = "Vision Test", group = "opMode")
public class VisionTest extends OpModeCamera {

    int ds2 = 1;  // additional downsampling of the image
    int numPics = 0;
    double avgX = 0;
    int redValue = 0;
    int blueValue = 0;
    int numFailLoops = 0;
    int validPix = 0;
    String pos = "";
    int red = 0, blue = 0;
    // set to 1 to disable further downsampling

    /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
//        setCameraDownsampling(2);
        // parameter determines how downsampled you want your images
        // 8, 4, 2, or 1.
        // higher number is more downsampled, so less resolution but faster
        // 1 is original resolution, which is detailed but slow
        // must be called before super.init sets up the camera

        super.init(); // inits camera functions, starts preview callback
        telemetry.addData("Camera init", ": done");
        telemetry.update();
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void loop() {

        long startTime = System.currentTimeMillis();

        if (imageReady()) { // only do this if an image has been returned from the camera

            numPics++;

            // get image, rotated so (0,0) is in the bottom left of the preview window
            Bitmap rgbImage;
            rgbImage = convertYuvImageToRgb(yuvImage, width, height, ds2);

            int cubePixelCount = 0;
            ArrayList<Integer> xValues = new ArrayList<>();
            for (int x = (int) ((2.0/3)* rgbImage.getWidth()); x < rgbImage.getWidth(); x+= 2) {

                for (int y = 0; y < rgbImage.getHeight(); y+= 2) {
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

            else if (avgX >.6 * rgbImage.getHeight()) pos = "center";

            else pos = "right";
        }
        telemetry.addData("cube pos", pos);
        telemetry.update();
        try{
            Thread.sleep(5000);
        }catch (Exception e){

        }

    }

    @Override
    public void stop() {
        super.stop(); // stops camera functions
    }
}