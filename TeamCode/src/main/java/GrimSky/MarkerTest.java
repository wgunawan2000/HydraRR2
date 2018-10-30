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
        } else {
            servo.setPower(0);
        }

        if(gamepad1.b){
            servo.setPower(-.5);
        } else {
            servo.setPower(0);
        }
    }
}
