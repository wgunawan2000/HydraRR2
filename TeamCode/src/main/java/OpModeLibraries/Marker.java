package OpModeLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Marker {
    private OpMode opMode;
    Servo marker;

    public Marker(OpMode opMode) {
        this.opMode = opMode;
        marker = this.opMode.hardwareMap.servo.get("Marker");
        this.opMode.telemetry.addData("init", "init finished");
        this.opMode.telemetry.update();
        Up();
    }

    public void Up() {
        marker.setPosition(1);
    }

    public void Down() {
        marker.setPosition(.5);
    }
}
