package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@TeleOp(name = "MainTeleOp", group = "opMode")
public class MainTeleOp extends GrimSkyOpMode {

    public void loop() {
        boolean tank = true;
        if (gamepad1.b) {
            //prevents from looping more than once
            while (gamepad1.b){
            }
            tank = !tank;

        }
        telemetry.addData("mode", tank ? "tank" : "arcade");
        telemetry.update();
        double sC = gamepad1.left_bumper ? .5 : 1;

        if (tank) {
            //tank drive

            if (Math.abs(gamepad1.left_stick_y) > .1 || (Math.abs(gamepad1.right_stick_y)) > .1) {
                startMotors(gamepad1.left_stick_y * sC, gamepad1.right_stick_y * sC);
            } else {
                stopMotors();
            }

            if (gamepad2.right_trigger > .1) {
                liftUp(gamepad2.right_trigger);
            } else if (gamepad2.left_trigger > .1){
                liftDown(gamepad2.left_trigger);
            } else {
                lift.setPower(0);
            }

            if (gamepad2.dpad_right) {
                pto.setPower(-.5);
            } else if (gamepad2.dpad_left) {
                pto.setPower(.5);
            } else {
                pto.setPower(0);
            }

        } else {
            double left = 0;
            double right = 0;
            double max;

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
