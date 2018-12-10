package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "MainTeleOp", group = "opMode")
public class MainTeleOp extends GrimSkyOpMode {

    //keeps track of whether or not the lift is raised
    boolean liftIsUp = false;
    boolean reverseGates = false;
    public void loop() {
        double sC = gamepad1.left_bumper ? .5 : 1;

        //================================= DRIVE ==================================================
        double left = 0;
        double right = 0;
        double max;

        if (Math.abs(gamepad1.left_stick_y) > .1 || (Math.abs(gamepad1.right_stick_x)) > .1) {
            left = gamepad1.left_stick_y - gamepad1.right_stick_x;
            right = gamepad1.left_stick_y + gamepad1.right_stick_x ;
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
        if (Math.abs(gamepad2.left_stick_y) > .1) {
            if(gamepad2.left_stick_y < .1) liftIsUp = true;
            if(gamepad2.left_stick_y > .1) {
                liftIsUp = false;
                pivotInit();
                closeGates();
            }
            setLift(gamepad2.left_stick_y * 1);
        }
        else if (Math.abs(gamepad2.right_stick_y) > .1){
            if(gamepad2.right_stick_y > .1) {
                liftIsUp = false;
                pivotInit();
                closeGates();
            }
            setLift(gamepad2.right_stick_y * .5);
        }
        else {
            lift.setPower(0);
        }

        //================================ PTO =================================================
        if (gamepad2.dpad_right) {
            pto.setPower(-.5);
            startMotors(-.2, -.2);
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            ML.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else if (gamepad2.dpad_left) {
            pto.setPower(.5);
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            ML.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
        else {
            pto.setPower(0);
        }
        if (gamepad2.dpad_up) {
            marker.setPosition(0);
        }
        else if(touchPTO.getState()) {
            telemetry.addData("ENGAGED", "ENGAGED");
            telemetry.update();
            marker.setPosition(.53);
        }
        else{
            telemetry.clear();
            telemetry.update();
            marker.setPosition(.85);
        }

        // ========================== INTAKE ===================================================
        if (gamepad1.left_trigger > .1) {
            intakeOut();
        } else if (gamepad1.right_trigger > .1) {
            intakeIn();
            semiGate();
        }
        else {
            intakeStop();
        }

        //=========================== OUTPUT ===================================================
        if (gamepad2.left_bumper) {
            if (reverseGates)
                openSmallL();
            else
                openSmallR();
        }

        if (gamepad2.right_bumper) {
            if (reverseGates)
                openSmallR();
            else
                openSmallL();
        }

        if (gamepad2.y){
            reverseGates = false;
            pivotAngleBack();
        }

        if (gamepad2.a) {
            reverseGates = true;
            pivotParallelForward();
        }
        if (gamepad2.b){
            pivotInit();
        }
    }
}
