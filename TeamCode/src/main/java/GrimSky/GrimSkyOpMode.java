package GrimSky;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class GrimSkyOpMode extends OpMode{

    DcMotor FR;
    DcMotor MR;
    DcMotor BR;
    DcMotor FL;
    DcMotor ML;
    DcMotor BL;

    DcMotor lift;

    CRServo pto;
    CRServo intakeL;
    CRServo intakeR;

    Servo marker;

    Servo gateL;
    Servo gateR;
    Servo pivotL;
    Servo pivotR;

    DigitalChannel touchPTO;
    GrimSkyOpMode opMode;

    public void init() {

        FR = hardwareMap.dcMotor.get("FR");
        FL = hardwareMap.dcMotor.get("FL");
        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");
        ML = hardwareMap.dcMotor.get("ML");
        MR = hardwareMap.dcMotor.get("MR");

        lift = hardwareMap.dcMotor.get("lift");

        touchPTO = hardwareMap.digitalChannel.get("touchPTO");

        marker = hardwareMap.servo.get("Marker");
        pto = hardwareMap.crservo.get("pto");
        intakeL = hardwareMap.crservo.get("intakeL");
        intakeR = hardwareMap.crservo.get("intakeR");
        pivotL = hardwareMap.servo.get("pivotL");
        pivotR = hardwareMap.servo.get("pivotR");
        gateL = hardwareMap.servo.get("gateL");
        gateR = hardwareMap.servo.get("gateR");

        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        MR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ML.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        MR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ML.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        ML.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        MR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pivotInit();
        closeGates();
        marker.setPosition(.85);

        telemetry.addData("init ", "completed");
        telemetry.update();
    }

    //============================= OUTPUT =========================================================
    public void openBigL(){
        gateL.setPosition(.40);
    }

    public void openBigR(){
        gateR.setPosition(.62);
    }

    public void openSmallL(){
        gateL.setPosition(.32);
    }

    public void openSmallR(){
        gateR.setPosition(.70);
    }

    public void closeL(){
        gateL.setPosition(.01);
    }

    public void closeR(){
        gateR.setPosition(.99);
    }

    public void closeGates(){
        closeL();
        closeR();
    }

    public void pivotParallelForward(){
        pivot(1,.04);
    }

    public void pivotAngleBack(){
        pivot(.32, .75);
    }

    public void pivotAngleForward(){
        pivot(.87, .16);
    }

    public void pivotInit(){
        pivot(.66, .40);
    }

    public void pivotR(){
        pivotR.setPosition(pivotR.getPosition()+.01);
    }

    public void pivotL(){
        pivotL.setPosition(pivotL.getPosition()-.01);
    }

    public void semiGate(){
        gateL.setPosition(.13);
        gateR.setPosition(.89);
    }

    public void pivot(double l, double r) {
        pivotL.setPosition(l);
        pivotR.setPosition(r);
    }

    //================================== DRIVETRAIN/LIFT/INTAKE ====================================
    public void startMotors(double l, double r){
        FR.setPower(r);
        MR.setPower(r);
        BR.setPower(r);
        FL.setPower(l);
        ML.setPower(l);
        BL.setPower(l);
    }


    public void stopMotors(){
        FR.setPower(0);
        MR.setPower(0);
        BR.setPower(0);
        FL.setPower(0);
        ML.setPower(0);
        BL.setPower(0);
    }

    public void setLift(double power){
        if(power < 0) {
            lift.setPower(power);
        }
        else {
            lift.setPower(power);
        }
    }

    public void intakeOut(){
        intakeL.setPower(.6);
        intakeR.setPower(.6);
    }

    public void intakeIn(){
        intakeL.setPower(-.6);
        intakeR.setPower(-.6);
    }

    public void intakeStop(){
        intakeL.setPower(0);
        intakeR.setPower(0);
    }
}
