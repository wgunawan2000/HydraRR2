package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "UnoDriver", group = "opMode")
public class Driver1TeleOp extends GrimSkyOpMode{

    //keeps track of whether or not the lift is raised
    boolean liftIsUp = false;
    boolean engaged = false;
    double rC = 1;
    double iC = 1;

    public void loop() {
        //================================= DRIVE ==================================================
        //speed constant allows driver 1 to scale the speed of the robot
        double sC = gamepad1.left_bumper ? .5 : 1;
        double left = 0;
        double right = 0;
        double max;

        if (engaged) {
            rC = 0;
            sC = .5;
        }
        else rC = 1;
        if (Math.abs(gamepad1.left_stick_y) > .1 || (Math.abs(gamepad1.right_stick_x)) > .1) {
            left = gamepad1.left_stick_y - gamepad1.right_stick_x;
            right = gamepad1.left_stick_y + gamepad1.right_stick_x ;
            max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1.0) {
                left /= max;
                right /= max;
            }
            startMotors(left * sC, right * sC * rC);
        }
        else {
            stopMotors();
        }

        //==================================== LIFT ================================================

        if(gamepad1.dpad_up) {
            setLift(1);
        } else if(gamepad1.dpad_down) {
            setLift(-.5);
            basketsInit();
        } else {
            lift.setPower(0);
        }

        //================================ PTO =====================================================
        //macro to engage
        if (gamepad2.dpad_right) {
            pto.setPower(-.2);
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            ML.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            engaged = true;
        }
        //disengage
        else if (gamepad2.dpad_left) {
            pto.setPower(.5);
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            ML.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            engaged = false;
        }
        else {
            pto.setPower(0);
        }
//        if (gamepad1.dpad_up) {
//            marker.setPosition(0);
//        } else {
//            marker.setPosition(.85);
//        }

        // ========================== INTAKE =======================================================
        if (gamepad2.right_trigger > .1) {
            extend(gamepad2.right_trigger);
        } else if (gamepad2.left_trigger > .1) {
            retract(gamepad2.left_trigger);
        } else {
            intakeMotorStop();
        }

        if (gamepad2.y){
            pivotUp();
            iC = .5;
        }

        if (gamepad2.b){
            pivotDown();
            iC = 1;
        }

        if (gamepad2.a){
            pivotMid();
        }

        if (gamepad1.right_trigger > .1) {
            collectionIn(iC);
        }
        else if (gamepad1.left_trigger > .1) {
            collectionOut();
        }
        else {
            collectionStop();
        }

        if (gamepad1.dpad_left) {
            while(gamepad1.dpad_left);
            pivotIntakeL();
            telemetry.addData("left  ", pivotL.getPosition());
            telemetry.update();
        }

        if(gamepad1.dpad_right){
            while(gamepad1.dpad_right);
            pivotIntakeR();
            telemetry.addData("right  ", pivotR.getPosition());
            telemetry.update();
        }

        //=========================== OUTPUT =======================================================

        if (gamepad2.right_bumper) {
            outBackL();
        } else {
            initL();
        }

        if (gamepad2.left_bumper){
            outBackR();
        } else {
            initR();
        }
    }

}
