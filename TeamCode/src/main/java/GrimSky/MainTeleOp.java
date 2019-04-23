package GrimSky;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "MainTeleOp", group = "opMode")
public class MainTeleOp extends GrimSkyOpMode{

    //keeps track of whether or not the lift is raised
    boolean engaged = false;
    boolean tank = false;
    boolean liftIsUp = false;
    boolean controlLift = false;
    boolean atHeight = false;
    boolean reached = false;
    boolean intakeIn = false;
    ElapsedTime pivotTime = new ElapsedTime();
    ElapsedTime liftTime = new ElapsedTime();

    int prevState = 0;
//    String [] intakeStates = {"in", "slow", "out", "stop"};
    int intakeState = 0;
    double rC = 1;

    public void loop() {
        //================================= DRIVE ==================================================
        //speed constant allows driver 1 to scale the speed of the robot
        double sC;
        if (gamepad1.left_bumper) {
            sC = .5;
        } else if (gamepad1.right_trigger > .1){
            sC = .4 ;
        } else {
            sC = 1;
        }
        double left = 0;
        double right = 0;
        double max;
        if (engaged)
            rC = 0;
        else
            rC = 1;

        if(gamepad2.left_stick_button){
            resetLiftEncoder();
        }
        if (gamepad2.dpad_down){
            while (gamepad2.dpad_down);
            liftHeight = 1950;
        }

        if (gamepad2.dpad_up){
            while (gamepad2.dpad_up);
            liftHeight = 2150;
        }

        if (gamepad1.b){
            while(gamepad1.b);
            tank = !tank;
        }

        if (tank) {
            if (Math.abs(gamepad1.left_stick_y) > .1 || (Math.abs(gamepad1.right_stick_y)) > .1){
                startMotors(gamepad1.left_stick_y * sC, gamepad1.right_stick_y * sC);
            } else {
                stopMotors();
            }
        } else {
            if (Math.abs(gamepad1.left_stick_y) > .1 || (Math.abs(gamepad1.right_stick_x)) > .1) {
                left = (gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y)) -
                        (gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x));
                right = (gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y)) +
                        (gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x));
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


        //==================================== LIFT ================================================
        if (Math.abs(gamepad2.left_stick_y) > .1) {
            if (gamepad2.left_stick_y > .1) {
                controlLift = true;
                liftIsUp = false;
                setLift(.5 * gamepad2.left_stick_y * Math.abs(gamepad2.left_stick_y));
            }
            if (gamepad2.left_stick_y < .1) {
                setLift(.9 * gamepad2.left_stick_y * Math.abs(gamepad2.left_stick_y));
                controlLift = true;
            }
        }
        else if (Math.abs(gamepad2.right_stick_y) > .1) {
            if (gamepad2.right_stick_y > .1)
                liftTime.reset();
                liftIsUp = false;
                basketsInit();
                controlLift = false;
            if (gamepad2.right_stick_y < .1) {
                setLift(gamepad2.right_stick_y * .75);
                controlLift = true;
            }
        }
        else if (gamepad2.x) {
            intakeIn = false;
            pivotMid();
            basketsMid();
            controlLift = false;
            liftIsUp = true;
        }

        if (controlLift && Math.abs(gamepad2.left_stick_y) < .1){
            setLift(0);
        }

        else if (liftIsUp && !controlLift) {
            if (getLiftEncoder() < liftHeight) {
                setLift(-1);
            }
            else {
                reached = true;
                setLift(-.2);
            }
        }
        else if (!controlLift) {
            if (getLiftEncoder() > 50 && !(Math.abs(liftTime.milliseconds() - 3000) < 250)) {
                setLift(1);
            } else {
                lift.setPower(0);
            }
        }

        if (reached && gamepad2.dpad_down){
            if (getLiftEncoder() > 2000) {
                setLift(.2);
            }
            else {
                reached = true;
                setLift(-.3);
            }
        }
//        if (Math.abs(getLiftEncoder() - 200) < 75 && liftIsUp){
//            outMidR();
//            outMidL();
//        }
        //================================ PTO =====================================================
        if (gamepad2.dpad_right) {
            pto.setPower(-.15);
            sC = .35;
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            ML.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            engaged = true;
        }
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



        // ========================== INTAKE =======================================================
        if (gamepad2.right_trigger > .1) {
            intakeIn = false;
            extend(gamepad2.right_trigger);
        } else if ((gamepad2.left_trigger > .1)) {
                retract(gamepad2.left_trigger);
        } else {
            intakeMotorStop();
        }

        if (gamepad2.b){
            intakeState = 0;
            pivotDown();
            gateDown();
        }

        if (gamepad2.a){
            intakeState = 1;
            pivotMid();
//            intakeIn = true;
        }

        if (gamepad2.y){
            intakeState = 0;
            pivotUp();
            gateUp();
            pivotTime.reset();
        }

        if (Math.abs(pivotTime.milliseconds() - 750) < 250) {
            intakeState = 3;
        }

//        if (intakeIn) {
//            if (intake.getCurrentPosition() > 100) {
//                if (Math.abs(intake.getCurrentPosition() - 400) < 100){
//                    gateUp();
//                }
//                intake.setPower(-1);
//            }
//            else {
//                intake.setPower(0);
//            }
//        }


        if (intakeState == 2){
            collectionOut();
        } else if (intakeState == 0) {
            collectionIn();
        } else if (intakeState == 3){
            collectionStop();
        } else if (intakeState == 1){
            collectionSlow();
        }

        //=========================== OUTPUT =======================================================
        if (gamepad1.right_bumper && !engaged) {
                outBackL();
                outBackR();
        }

        if (intakeState != 2) {
            prevState = intakeState;
        }
        if (gamepad2.left_bumper){
            if (intakeState != 2)
                prevState = intakeState;
            intakeState = 2;
        } else {
            intakeState = prevState;
        }


        telemetry.addData("encoders: ", getLiftEncoder());
        telemetry.addData("goal: ", liftHeight);
        telemetry.update();
    }
}
