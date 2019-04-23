package GrimSkyLibraries;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.*;
import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import java.util.ArrayList;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;



public class GoldDetectorVuforia extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    VuforiaLocalizer vuforia;
    int ds2 = 1;
    int numPics = 0;
    double avgX = 0;
    double avgY = 0;
    int red, blue = 0;
    String pos = "notFound";
    int offset;
    int cubePixelCount = 0;
    LinearOpMode opMode;

    public GoldDetectorVuforia(LinearOpMode opMode) {
        this.opMode = opMode;
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters();
        params.vuforiaLicenseKey = "AQvLCbX/////AAABmTGnnsC2rUXvp1TAuiOSac0ZMvc3GKI93tFoRn4jPzB3uSMiwj75PNfUU6MaVsNZWczJYOep8LvDeM/3hf1+zO/3w31n1qJTtB2VHle8+MHWNVbNzXKLqfGSdvXK/wYAanXG2PBSKpgO1Fv5Yg27eZfIR7QOh7+J1zT1iKW/VmlsVSSaAzUSzYpfLufQDdE2wWQYrs8ObLq2kC37CeUlJ786gywyHts3Mv12fWCSdTH5oclkaEXsVC/8LxD1m+gpbRc2KC0BXnlwqwA2VqPSFU91vD8eCcD6t2WDbn0oJas31PcooBYWM6UgGm9I2plWazlIok72QG/kOYDh4yXOT4YXp1eYh864e8B7mhM3VclQ";
        params.cameraName = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(params);
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true); //enables RGB565 format for the image
        vuforia.setFrameQueueCapacity(4); //tells VuforiaLocalizer to only store one frame at a time
    }

    public Bitmap getImage() throws InterruptedException {
        VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();
        long numImages = frame.getNumImages();
        Image rgb = null;
        for (int i = 0; i < numImages; i++) {
            Image img = frame.getImage(i);
            int fmt = img.getFormat();
            if (fmt == PIXEL_FORMAT.RGB565) {
                rgb = frame.getImage(i);
                break;
            }
        }
        Bitmap bm = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565);
        bm.copyPixelsFromBuffer(rgb.getPixels());
        return bm;
    }
    public String getCubePos() throws InterruptedException {
        Bitmap rgbImage = getImage();
        cubePixelCount = 0;
        ArrayList<Integer> xValues = new ArrayList<>();
        ArrayList<Integer> yValues = new ArrayList<>();
        // (0,0) = top left
        for (int y = 0; y < (int) ((rgbImage.getHeight() / 2)); y++) {
            for (int x = 0; x < rgbImage.getWidth(); x++) {
                int pixel = rgbImage.getPixel(x, y);
                red += red(pixel);
                blue += blue(pixel);
                if (red(pixel) >= 140 && green(pixel) > 100 && blue(pixel) <= 75) {
                    cubePixelCount++;
                    yValues.add(y);
                    xValues.add(x);
                }
            }
        }
        avgX = 0;
        avgY = 0;
        for (int xCoor : xValues) {
            avgX += xCoor;
        }
        for (int yCoor : yValues) {
            avgY += yCoor;
        }
        avgX /= xValues.size();
        avgY /= yValues.size();

        opMode.telemetry.addData("cubePixelCount", cubePixelCount);
        opMode.telemetry.addData("avgX", avgX);
        opMode.telemetry.addData("pos", pos);
        opMode.telemetry.update();
        sleep(1000);

        if (avgX < 200 && cubePixelCount > 10){
            pos = "right";
        }
        else if (avgX < 500 && cubePixelCount > 10){
            pos = "center";
        }
        else{
            pos = "left";
        }

        return pos;
        }

    public String getCubePosDepot() throws InterruptedException {
        Bitmap rgbImage = getImage();
        cubePixelCount = 0;
        ArrayList<Integer> xValues = new ArrayList<>();
        ArrayList<Integer> yValues = new ArrayList<>();
        // (0,0) = top left
        for (int y = 0; y < (int) ((rgbImage.getHeight() / 2)); y++) {
            for (int x = 0; x < rgbImage.getWidth(); x++) {
                int pixel = rgbImage.getPixel(x, y);
                red += red(pixel);
                blue += blue(pixel);
                if (red(pixel) >= 140 && green(pixel) > 100 && blue(pixel) <= 75) {
                    cubePixelCount++;
                    yValues.add(y);
                    xValues.add(x);
                }
            }
        }
        avgX = 0;
        avgY = 0;
        for (int xCoor : xValues) {
            avgX += xCoor;
        }
        for (int yCoor : yValues) {
            avgY += yCoor;
        }
        avgX /= xValues.size();
        avgY /= yValues.size();

        opMode.telemetry.addData("cubePixelCount", cubePixelCount);
        opMode.telemetry.addData("avgX", avgX);
        opMode.telemetry.addData("pos", pos);
        opMode.telemetry.update();
        sleep(1000);

        if (avgX < 200 && cubePixelCount > 10){
            pos = "right";
        }
        else if (avgX < 500 && cubePixelCount > 10){
            pos = "center";
        }
        else{
            pos = "left";
        }
        return pos;
    }
}

