package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import GrimSkyLibraries.Marker;

@TeleOp(name = "Servo Test", group = "opMode")
public class MarkerTest extends OpMode{
    Servo servo;

    public void init() {
        servo = hardwareMap.servo.get("Marker");
    }

    public void loop(){
        if(gamepad1.a){
            servo.setPosition(.53);
        }
        if (gamepad1.x){
            servo.setPosition(1);
        }

        if(gamepad1.y){
            servo.setPosition(0);
        }
        if (gamepad1.dpad_up){
            servo.setPosition(.25);
        }
        if (gamepad1.dpad_down) {
            servo.setPosition(.85);
        }
    }
}
