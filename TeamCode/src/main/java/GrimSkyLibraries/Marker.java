package GrimSkyLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Marker {
    private LinearOpMode opMode;
    Servo kickerL;
    Servo kickerR;

    public Marker(LinearOpMode opMode)throws InterruptedException {

        this.opMode = opMode;
        kickerL = this.opMode.hardwareMap.servo.get("LeftKicker");
        kickerR = this.opMode.hardwareMap.servo.get("RightKicker");
        this.opMode.telemetry.addData("init", "init finished");
        this.opMode.telemetry.update();
        kickersIn();

    }

    public void kickerRIn() {
        kickerR.setPosition(0);
    }

    public void kickerLIn() {
        kickerL.setPosition(0);
    }

    public void kickerROut() {
        kickerR.setPosition(1);
    }

    public void kickerLOut() {
        kickerL.setPosition(1);
    }

    public void kickersIn() {
        kickerRIn();
        kickerLIn();
    }
}

