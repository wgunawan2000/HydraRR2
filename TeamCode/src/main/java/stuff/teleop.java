package stuff;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "MainTeleOp", group = "opMode")
public class teleop extends GrimSkyOpMode{

    public void loop() {
        if (Math.abs(gamepad1.left_stick_y) > .1 || (Math.abs(gamepad1.right_stick_y)) > .1){
            startMotors(gamepad1.left_stick_y, gamepad1.right_stick_y);
        } else{
            stopMotors();
        }
    }
}
