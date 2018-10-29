package GrimSkyLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Marker {
    private LinearOpMode opMode;
    Servo marker;

    public Marker(LinearOpMode opMode) throws InterruptedException {
        this.opMode = opMode;
        marker = this.opMode.hardwareMap.servo.get("Marker");
        this.opMode.telemetry.addData("init", "init finished");
        this.opMode.telemetry.update();
        markerIn();
    }

    public void markerIn() {
        marker.setPosition(0);
    }

    public void markerOut()
    {
        marker.setPosition(0);
    }
}

