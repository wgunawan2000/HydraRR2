package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "MainTeleOp", group = "opMode")
public class MainTeleOp extends GrimSkyOpMode {

    //keeps track of whether or not the lift is raised
    boolean liftIsUp = false;
    //keeps track of if we are using the right basket
    boolean useRight = true;
    public void loop() {
        //================================= DRIVE ==================================================
        //speed constant allows driver 1 to scale the speed of the robot
        double sC = gamepad1.left_bumper ? .5 : 1;
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

        //================================ PTO =====================================================
        //macro to engage
        if (gamepad2.dpad_right) {
            pto.setPower(-.5);
            startMotors(-.2, -.2);
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            ML.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        //disengage
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
        if (gamepad1.dpad_up) {
            marker.setPosition(0);
        } else {
            marker.setPosition(.85);
        }

        // ========================== INTAKE =======================================================
        //collection
        if (gamepad1.left_trigger > .1) {
            collectionOut();
        } else if (gamepad1.right_trigger > .1) {
            collectionIn();
        }
        else {
            collectionStop();
        }

        //linear slides
        if (gamepad2.right_trigger > .1) {
            extend(gamepad2.right_trigger);
        } else if (gamepad2.left_trigger > .1) {
//            semiGate();
            pivotMid();
            retract(gamepad2.left_trigger);
        } else {
            intakeMotorStop();
        }

        //pivoting
        if (gamepad1.right_bumper) {
            pivotDown();
        }

        if (gamepad2.left_bumper) {
            intakePivotInit();
            semiGate();
        }

        //sorting
//        if (gamepad2.dpad_down) {
//            useRight = !useRight;
//            sort(useRight);
//        }

        if (gamepad1.dpad_right){
            telemetry.addData("right  ", intakePivotR.getPosition());
            telemetry.update();
            telemetry.update();
            pivotIntakeR();
        }

        if(gamepad1.dpad_left){
            telemetry.addData("left  ", intakePivotL.getPosition());
            telemetry.update();
            pivotIntakeL();
        }

        //=========================== OUTPUT ===================================================
        if (gamepad2.right_bumper) {
            if (useRight)
                openSmallR();
            else
                openSmallL();
        }

        if (gamepad2.y){
            pivotAngleBack(useRight);
        }

//        if (gamepad2.a) {
//            pivotParallelForward(useRight);
//        }
        if (gamepad2.b){
            pivotInit();
        }
    }
}
