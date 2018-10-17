package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "MainTeleOp", group = "opMode")
public class MainTeleOp extends GrimSkyOpMode {

    public void loop() {
        double direction;
        //tank drive
        if (Math.abs(gamepad1.left_stick_y) > .1 || (Math.abs(gamepad1.right_stick_y)) > .1) {
            startMotors(Math.sqrt(Math.abs(gamepad1.left_stick_y))*gamepad1.left_stick_y, Math.sqrt(Math.abs(gamepad1.right_stick_y)*gamepad1.right_stick_y));
        } else {
            stopMotors();
        }


        //arcade drive
//        int power = 0;
//        if(Math.abs(gamepad1.left_stick_y) > .1 && Math.abs(gamepad1.right_stick_x) > .1) {
//            if (gamepad1.right_stick_x > .1) {
//                if (gamepad1.left_stick_y < -.1)
//                    startMotors(Math.abs(gamepad1.left_stick_y), Range.clip(Math.abs(gamepad1.left_stick_y) - .5 * Math.cbrt(Math.abs(gamepad1.right_stick_x)), 0, 1));
//                else
//                    startMotors(-Math.abs(gamepad1.left_stick_y), -Range.clip(Math.abs(gamepad1.left_stick_y) - .5 * Math.cbrt(Math.abs(gamepad1.right_stick_x)), 0, 1));
//            } else {
//                if(gamepad1.left_stick_y < -.1)
//                startMotors(Range.clip(Math.abs(gamepad1.left_stick_y) - .5 * Math.cbrt(Math.abs(gamepad1.right_stick_x)), 0, 1), Math.abs(gamepad1.left_stick_y));
//                else
//                    startMotors(-Range.clip(Math.abs(gamepad1.left_stick_y) - .5 * Math.cbrt(Math.abs(gamepad1.right_stick_x)), 0, 1), -Math.abs(gamepad1.left_stick_y));
//            }
//        }
//        else if (Math.abs(gamepad1.left_stick_y) > .1)
//        {
//            startMotors(gamepad1.left_stick_y, gamepad1.left_stick_y);
//        }
//        else if(Math.abs(gamepad1.right_stick_x) > .1)
//        {
//            if(gamepad1.right_stick_x > .1){
//                startMotors(-gamepad1.right_stick_x, gamepad1.right_stick_x);
//            }
//            else startMotors(-gamepad1.right_stick_x, gamepad1.right_stick_x);
//
//        } else stopMotors();
//    }
//
//        double drive = 0;
//        double turn = 0;
//        double left = 0;
//        double right = 0;
//        double max;
//
//        double sC = gamepad1.left_bumper ? .5 : 1;
//
//        if (Math.abs(gamepad1.left_stick_y) > .1 || (Math.abs(gamepad1.right_stick_x)) > .1) {
//                left = gamepad1.left_stick_y - gamepad1.right_stick_x;
//                right = gamepad1.left_stick_y + gamepad1.right_stick_x;
//
//            max = Math.max(Math.abs(left), Math.abs(right));
//            if (max > 1.0)
//            {
//                left /= max;
//                right /= max;
//            }
//
//            startMotors(left * sC, right * sC);
//
//        } else {
//            stopMotors();
//        }
//    }
    }
}