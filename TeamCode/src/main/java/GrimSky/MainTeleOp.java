package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "MainTeleOp", group = "opMode")
public class MainTeleOp extends GrimSkyOpMode{

    public void loop() {
        double direction;
        //tank drive
//        if (Math.abs(gamepad1.left_stick_y) > .1 || (Math.abs(gamepad1.right_stick_y)) > .1){
//            startMotors(gamepad1.left_stick_y, gamepad1.right_stick_y);
//        } else{
//            stopMotors();
//        }

        //arcade drive
        int power = 0;
        if(Math.abs(gamepad1.left_stick_y) > .1 && Math.abs(gamepad1.right_stick_x) > .1) {
            if (gamepad1.right_stick_x > .1) {
                if (gamepad1.left_stick_y < -.1)
                    startMotors(Math.abs(gamepad1.left_stick_y), Range.clip(Math.abs(gamepad1.left_stick_y) - .5 * Math.cbrt(Math.abs(gamepad1.right_stick_x)), 0, 1));
                else
                    startMotors(-Math.abs(gamepad1.left_stick_y), -Range.clip(Math.abs(gamepad1.left_stick_y) - .5 * Math.cbrt(Math.abs(gamepad1.right_stick_x)), 0, 1));
            } else {
                if(gamepad1.left_stick_y < -.1)
                startMotors(Range.clip(Math.abs(gamepad1.left_stick_y) - .5 * Math.cbrt(Math.abs(gamepad1.right_stick_x)), 0, 1), Math.abs(gamepad1.left_stick_y));
                else
                    startMotors(-Range.clip(Math.abs(gamepad1.left_stick_y) - .5 * Math.cbrt(Math.abs(gamepad1.right_stick_x)), 0, 1), -Math.abs(gamepad1.left_stick_y));
            }
        }
        else if (Math.abs(gamepad1.left_stick_y) > .1)
        {
            startMotors(gamepad1.left_stick_y, gamepad1.left_stick_y);
        }
        else if(Math.abs(gamepad1.right_stick_x) > .1)
        {
            if(gamepad1.right_stick_x > .1){
                startMotors(-gamepad1.right_stick_x, gamepad1.right_stick_x);
            }
            else startMotors(-gamepad1.right_stick_x, gamepad1.right_stick_x);

        } else stopMotors();
    }
}
