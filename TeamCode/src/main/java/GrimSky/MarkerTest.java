package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import GrimSkyLibraries.Marker;

@TeleOp(name = "Servo Test", group = "opMode")
public class MarkerTest extends OpMode{
    CRServo servo;

    public void init() {
        servo = hardwareMap.crservo.get("Servo");
    }

    public void loop(){
        if(gamepad1.a){
            servo.setPower(.5);
        }
        if (gamepad1.x){
            servo.setPower(1);
        }

        if(gamepad1.y){
            servo.setPower(0);
        }
        if (gamepad1.dpad_up){
            servo.setPower(.25);
        }
        if (gamepad1.dpad_down) {
            servo.setPower(.75);
        }
    }
}
