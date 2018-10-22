package GrimSkyLibraries;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;

public class Sampler {

    ElapsedTime time = new ElapsedTime();
    VuforiaBitmap sample = new VuforiaBitmap();
    LinearOpMode opMode;

    public Sampler(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    public int getCubePos() {

        time.reset();

        while (time.seconds() < 2 && opMode.opModeIsActive()) {

            // get image, rotated so (0,0) is in the bottom left of the preview window
            Bitmap rgbImage = sample.getBitMap();
            int cubePixelCount = 0;
            ArrayList<Integer> xValues = new ArrayList<>();
            for (int x = (int) (1 * rgbImage.getWidth()); x < rgbImage.getWidth(); x++) {
                for (int y = 0; y < (int) ((1/3) * rgbImage.getHeight()); y++) {
                    int pixel = rgbImage.getPixel(x, y);
                    if (red(pixel) >= 115 && blue(pixel) <= 70) {
                        cubePixelCount++;
                        xValues.add(x);
                    }
                }
            }
            int avgX = 0;
            for (int xCoor : xValues) {
                avgX += xCoor;
            }
            avgX /= xValues.size();

            if (cubePixelCount < 250) return 1;

            if (avgX < .6*rgbImage.getWidth()) return 2;

            return 3;
        }
        return 3;
    }
}
