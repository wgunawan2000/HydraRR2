package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "MainTeleOp", group = "opMode")
public class MainTeleOp extends GrimSkyOpMode {
    public void loop() {
        double sC = gamepad1.left_bumper ? .5 : 1;
        double tC = gamepad1.right_bumper ? 1 : 2;

        //================================= DRIVE ==================================================
        double left = 0;
        double right = 0;
        double max;

        if (Math.abs(gamepad1.left_stick_y) > .1 || (Math.abs(gamepad1.right_stick_x)) > .1) {
            left = gamepad1.left_stick_y - gamepad1.right_stick_x / tC;
            right = gamepad1.left_stick_y + gamepad1.right_stick_x / tC;
            max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1.0) {
                left /= max;
                right /= max;
            }
            startMotors(left * sC, right * sC);
        }
        else {
            stopMotors();
        }

        //==================================== LIFT ================================================
        if (Math.abs(gamepad2.right_stick_y) > .1) {
            setLift(gamepad2.right_stick_y * .75);
        } else {
            lift.setPower(0);
        }

        //================================ PTO =================================================
        if (gamepad2.dpad_right) {
            pto.setPower(-.5);
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            ML.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            } else if (gamepad2.dpad_left) {
                pto.setPower(.5);
                lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                ML.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            } else {
                pto.setPower(0);
            }

            // =========================== MARKER ==================================================
            if (gamepad2.dpad_down)
                marker.setPosition(0);
            else if (gamepad2.dpad_up)
                marker.setPosition(.85);

            //=========================== OUTPUT ===================================================
            if (gamepad2.right_bumper) {
                openSmallR();
            } else {
                closeR();
            }

            if (gamepad2.left_bumper) {
                openSmallL();

            } else {
                closeL();
            }

//            if (gamepad2.right_stick_x > .1){
//                while (gamepad2.right_stick_x > .1);
//                pivotR();
//            }
//
//            if (gamepad2.left_stick_x > .1){
//                while (gamepad2.left_stick_x > .1);
//                pivotL();
//            }

            if (gamepad2.y){
                pivotParallelForward();
            }

            if (gamepad2.x){
                if(gamepad2.left_trigger > .1)
                    pivotAngleForward();
                else
                pivotAngleBack();
            }

            telemetry.addData("R", pivotR.getPosition());
            telemetry.addData("L", pivotL.getPosition());
            telemetry.update();


    }

}
