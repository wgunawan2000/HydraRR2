package GrimSky;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

import GrimSkyLibraries.Sensors;
import for_camera_opmodes.LinearOpModeCamera;


public class GrimSkyLinearOpMode extends LinearOpModeCamera {
    int ds2 = 1;
    double avgX = 0;
    int validPix = 0;
    String pos = "";
    int red = 0, blue = 0;

    public DcMotor BL;
    public DcMotor ML;
    public DcMotor FL;
    public DcMotor BR;
    public DcMotor MR;
    public DcMotor FR;
    Sensors sensor;
    LinearOpMode opMode;
    ElapsedTime times;

    @Override
    public void runOpMode() throws InterruptedException {

    }

    public void customInit() throws InterruptedException{
        sensor = new Sensors(opMode);
        times = new ElapsedTime();
        opMode = this.opMode;
        BL = this.opMode.hardwareMap.dcMotor.get("BL");
        ML = this.opMode.hardwareMap.dcMotor.get("ML");
        FL = this.opMode.hardwareMap.dcMotor.get("FL");
        BR = this.opMode.hardwareMap.dcMotor.get("BR");
        MR = this.opMode.hardwareMap.dcMotor.get("MR");
        FR = this.opMode.hardwareMap.dcMotor.get("FR");
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
        MR.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addLine("!camera init finished");
        telemetry.update();
        }

    public String getPos(){
        setCameraDownsampling(1);

        telemetry.addLine("Wait for camera to finish initializing!");
        telemetry.update();
        String pos = "";
        startCamera();  // can take a while.
        // best started before waitForStart
        telemetry.addLine("Camera ready!");
        telemetry.update();

            while (pos.equals("")) {
                if (imageReady()) { // only do this if an image has been returned from the camera
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
