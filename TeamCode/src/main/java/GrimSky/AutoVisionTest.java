package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "AutoVisionTest", group = "LinearOpMode")
public class AutoVisionTest extends GrimSkyLinearOpMode {
    public void runOpMode() throws InterruptedException {

        customInit();

        waitForStart();

        String cube = getPos();
        telemetry.addData("cubePos: ", cube);
        telemetry.update();
    }
}
