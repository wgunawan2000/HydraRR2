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
        Back();
    }

    public void Up() {
        marker.setPosition(.53);
    }

    public void Down() {
        marker.setPosition(0);
    }

    public void Back() {
        marker.setPosition(.85);
    }
}
