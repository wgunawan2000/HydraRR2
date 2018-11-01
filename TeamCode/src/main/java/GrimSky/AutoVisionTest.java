package GrimSky;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

import GrimSkyLibraries.Sampler;
import OpModeLibraries.Drivetrain;
import OpModeLibraries.Marker;
import OpModeLibraries.Sensors;
import for_camera_opmodes.OpModeCamera;


@Autonomous (name="AutoVisionTest", group="Iterative Opmode")
public class AutoVisionTest extends OpModeCamera {
    int ds2 = 1;
    int numPics = 0;
    double avgX = 0;
    int validPix = 0;
    String pos = "center";
    int red = 0, blue = 0;
    ElapsedTime times;
    OpMode opMode;

    private Drivetrain drivetrain;
    private Sensors sensors;
    private String cubePos;
    private Marker marker;
    private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void init() {
        super.init(); // inits camera functions, starts preview callback
        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        marker = new Marker(this);
        telemetry.addData("init", "done");
        telemetry.update();
    }


    //Loop: Loops once driver hits play after start() runs.
    @Override
    public void loop() {
        //turn to see right two minerals of sample
        drivetrain.turnPI(10, .5, .05);

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

            sleep(2000);
            //scan sample and turn
            telemetry.addData("cubePos: ", pos);
            telemetry.update();

            if (pos.equals("left")){
                drivetrain.turnPI(-32, .5, .05);
            }
            else if(pos.equals("center")){
                drivetrain.turnPI(-2, .5, .05);
            }
            else{
                drivetrain.turnPI(32, .5, .05);
            }

            sleep(1000);
            drivetrain.move(.4, 1000);
            sleep(1000);

            drivetrain.turnPI(35, .4, .05);
            sleep(1000);
            marker.Down();
            sleep(1000);
            marker.Up();
            sleep(1000);

            drivetrain.turnPI(80, .3, .1);
            sleep(1000);

            drivetrain.move(-.4, 700);
            drivetrain.wallRollL(-.4, 800);

    }

    public void sleep(int milis){
        try {
            wait(milis);
        } catch (Exception e){}
    }


    //Stop: Runs once driver hits stop.
    @Override
    public void stop() {
    }

}