package GrimSkyLibraries;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

import for_camera_opmodes.LinearOpModeCamera;

public class Sampler extends LinearOpModeCamera {

    int ds2 = 1;
    int numPics = 0;
    double avgX = 0;
    int validPix = 0;
    String pos = "";
    int red = 0, blue = 0;
    ElapsedTime times;
    LinearOpMode opMode;
    public Sampler(LinearOpMode opMode) throws InterruptedException {
        this.opMode = opMode;
        times = new ElapsedTime();
        this.opMode.telemetry.addData("line ", "1");
        this.opMode.telemetry.update();
        //can set to 8, 4, 2, or 1 --> 8 is the most downsampled
        setCameraDownsampling(1);
        this.opMode.telemetry.addData("line ", "2");
        this.opMode.telemetry.update();
        startCamera();
        this.opMode.telemetry.addData("line ", "3");
        this.opMode.telemetry.update();
        sleep(1000);

    }

    public String getCubePos() {
        times.reset();
        while((pos.equals("") || times.seconds() < 2) && opModeIsActive()) {
            // only do this if an image has been returned from the camera
            if (imageReady()) {

                numPics++;

                // the image is in portrait mode with (0,0) as bottom right
                Bitmap rgbImage;
                rgbImage = convertYuvImageToRgb(yuvImage, width, height, ds2);

                int cubePixelCount = 0;
                ArrayList<Integer> xValues = new ArrayList<>();

                //nested for loops parse each pixel
                for (int x = (int) ((2.0 / 3) * rgbImage.getWidth()); x < rgbImage.getWidth(); x += 2) {
                    for (int y = 0; y < rgbImage.getHeight(); y += 2) {
                        int pixel = rgbImage.getPixel(x, y);
                        red += red(pixel);
                        blue += blue(pixel);
                        //acts as an rgb filter
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

                //add 1 to denominator to prevent dividing by 0 error
                avgX /= xValues.size() + 1;

                if (cubePixelCount < 30) pos = "left";

                else if (avgX > .6 * rgbImage.getHeight()) pos = "center";

                else pos = "right";
            }
        }
        stopCamera();
        return pos;
    }
}
