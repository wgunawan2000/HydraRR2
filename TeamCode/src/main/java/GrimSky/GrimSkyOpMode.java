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
    DcMotor intake;
    DcMotor lift;

    CRServo pto;
    CRServo collectionR;
    CRServo collectionL;

    Servo sorter;
    Servo intakePivotR;
    Servo intakePivotL;
    Servo marker;
    Servo gateL;
    Servo gateR;
    Servo pivotL;
    Servo pivotR;

    GrimSkyOpMode opMode;

    public void init() {

        FR = hardwareMap.dcMotor.get("FR");
        FL = hardwareMap.dcMotor.get("FL");
        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");
        ML = hardwareMap.dcMotor.get("ML");
        MR = hardwareMap.dcMotor.get("MR");

        lift = hardwareMap.dcMotor.get("lift");

        marker = hardwareMap.servo.get("Marker");
        pto = hardwareMap.crservo.get("pto");
        intake = hardwareMap.dcMotor.get("intake");
        collectionR = hardwareMap.crservo.get("collectionR");
        intakePivotR = hardwareMap.servo.get("intakePivotR");
        collectionL = hardwareMap.crservo.get("collectionL");
        intakePivotL = hardwareMap.servo.get("intakePivotL");
        pivotL = hardwareMap.servo.get("pivotL");
        pivotR = hardwareMap.servo.get("pivotR");
        gateL = hardwareMap.servo.get("gateL");
        gateR = hardwareMap.servo.get("gateR");

        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        MR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

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
    public void sort(boolean right){
        if (right){}
        else{}
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

    public void pivotParallelForward(boolean right){
        if (right) pivot(.66, .04);
        else pivot(1, .40);
        //pivot(1,.04);
    }

    public void pivotAngleBack(boolean right){
        if (right) pivot(.66, .75);
        else pivot(.32, .40);
        //pivot(.32, .75);
    }

    public void pivotInit(){
        pivot(.66, .40);
    }

    public void pivotIntake(){
        intakePivotR.setPosition(intakePivotR.getPosition()+.01);
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

    public void extend(){
        intake.setPower(1);
    }

    public void retract(){
        intake.setPower(-.2);
    }

    public void intakeMotorStop() {
        intake.setPower(0);
    }

    public void collectionOut(){
        collectionR.setPower(.8);
        collectionL.setPower(-.8);
    }

    public void collectionIn(){
        collectionR.setPower(-.8);
        collectionL.setPower(.8);
    }

    public void collectionStop(){
        collectionR.setPower(0);
        collectionL.setPower(0);
    }

    public void pivotDown(){
        intakePivotR.setPosition(.58);
        intakePivotL.setPosition(0);
    }

    public void pivotMid(){
        intakePivotR.setPosition(.25);
        intakePivotL.setPosition(.33);
    }

    public void intakePivotInit(){
        intakePivotR.setPosition(.18);
        intakePivotL.setPosition(.4);
    }

    public void pivotUp(){
        intakePivotR.setPosition(0);
        intakePivotL.setPosition(.58);
    }

}
