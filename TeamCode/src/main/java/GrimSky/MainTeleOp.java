package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
@TeleOp(name = "MainTeleOp", group = "opMode")
public class MainTeleOp extends GrimSkyOpMode {

    public void loop() {
        boolean tank = true;
        boolean lastState = false;
        if (gamepad1.right_bumper ^ lastState) {
            tank = !tank;
            lastState = !lastState;

        }

        if (tank) {
            //tank drive
            if (Math.abs(gamepad1.left_stick_y) > .1 || (Math.abs(gamepad1.right_stick_y)) > .1) {
                startMotors(gamepad1.left_stick_y, gamepad1.right_stick_y);
            } else {
                stopMotors();
            }
        } else {
            double left = 0;
            double right = 0;
            double max;

            double sC = gamepad1.left_bumper ? .5 : 1;

            if (Math.abs(gamepad1.left_stick_y) > .1 || (Math.abs(gamepad1.right_stick_x)) > .1) {
                left = gamepad1.left_stick_y - gamepad1.right_stick_x;
                right = gamepad1.left_stick_y + gamepad1.right_stick_x;

                max = Math.max(Math.abs(left), Math.abs(right));
                if (max > 1.0) {
                    left /= max;
                    right /= max;
                }

                startMotors(left * sC, right * sC);

            } else {
                stopMotors();
            }
        }
    }
}
