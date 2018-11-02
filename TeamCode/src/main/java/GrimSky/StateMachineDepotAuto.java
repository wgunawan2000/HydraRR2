package GrimSky;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

import OpModeLibraries.Drivetrain;
import OpModeLibraries.Marker;
import OpModeLibraries.Sensors;
import for_camera_opmodes.OpModeCamera;


@Autonomous (name = "StateMachineAuto", group = "OpMode")
public class StateMachineDepotAuto extends OpModeCamera {
    int ds2 = 1;
    int numPics = 0;
    double avgX = 0;
    int validPix = 0;
    String pos = "";
    int red = 0, blue = 0;
    ElapsedTime times;
    OpMode opMode;

    private enum State {
        FIND_SAMPLE,
        MARKER,
        LEFT,
        CENTER,
        RIGHT,
        HIT_MINERAL,
        PARK,
    }

    private Drivetrain drivetrain;
    private Sensors sensors;
    private String cubePos;
    private Marker marker;

    private State mState; //current state

    private void setState(State nS) {
        mState = nS;
    }


    @Override
    public void init() {
        super.init(); // inits camera functions, starts preview callback
        telemetry.addData("camera init", "done");
        telemetry.update();
        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        marker = new Marker(this);
        setState(State.FIND_SAMPLE);
        telemetry.addData("init", "done");
        telemetry.update();
    }

    @Override
    public void init_loop() {
    }


    @Override
    public void start() {

    }

    @Override
    public void loop() {
        telemetry.addData("state: ", mState.toString());

        switch (mState) {
            case FIND_SAMPLE:
                    drivetrain.turnPI(10, .2, .05);
                    getCubePos();
                    if (pos.equals("left"))
                        setState(State.LEFT);
                    if (pos.equals("center"))
                        setState(State.CENTER);
                    if (pos.equals("right"))
                        setState(State.RIGHT);
                    break;

            case LEFT:
                drivetrain.turnPI(-32, .2, .05);
                setState(State.HIT_MINERAL);
                break;

            case CENTER:
                drivetrain.turnPI(0, .2, .05);
                setState(State.HIT_MINERAL);
                break;

            case RIGHT:
                drivetrain.turnPI(0, .2, .05);
                setState(State.HIT_MINERAL);
                break;

            case HIT_MINERAL:
                sleep(500);
                drivetrain.move(.4, 1000);
                sleep(500);
                setState(State.MARKER);
                break;

            case MARKER:
                drivetrain.turnPI(25, .4, .05);
                sleep(500);
                marker.Down();
                marker.Up();
                setState(State.PARK);
                break;

            case PARK:
                drivetrain.turnPI(25, .4, .05);
                sleep(500);
                marker.Down();
                marker.Up();
                drivetrain.turnPI(80, .3, .1);
                sleep(1000);
                drivetrain.move(-.4, 700);
                sleep(1000);
                drivetrain.wallRollL(-.4, 800);
                break;
        }
    }

    public void sleep(int millis){
        try{
            Thread.sleep(millis);
        }catch(Exception e){

        }
    }

    public void getCubePos()
    {
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
    }
    @Override
    public void stop() {
        super.stop();
    }

}