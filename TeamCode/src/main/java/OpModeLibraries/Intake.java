package OpModeLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public DcMotor extend;
    public Servo pivot;
    public Servo downCollect;
    public Servo upCollect;
    OpMode opMode;

    public Intake(OpMode opMode)throws InterruptedException {

        this.opMode = opMode;
        downCollect = this.opMode.hardwareMap.servo.get("downCollect");
        upCollect = this.opMode.hardwareMap.servo.get("upCollect");
    }

    public void extend(){
    }

}
