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
    Servo pivotR;
    Servo pivotL;
    Servo marker;
    Servo basketL;
    Servo basketR;


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
        pivotR = hardwareMap.servo.get("pivotR");
        collectionL = hardwareMap.crservo.get("collectionL");
        pivotL = hardwareMap.servo.get("pivotL");

        basketL = hardwareMap.servo.get("basketL");
        basketR = hardwareMap.servo.get("basketR");

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

        marker.setPosition(.85);

        telemetry.addData("init ", "completed");
        telemetry.update();
    }

    //============================= OUTPUT =========================================================
    public void basketsInit(){
        initL();
        initR();
    }

    public void outL(){

    }

    public void outR(){

    }

    public void initL(){
        basketL.setPosition(0);
    }

    public void initR(){
        basketR.setPosition(1);
    }

    public void pivotIntakeL(){
        basketL.setPosition(basketL.getPosition()+.01);
    }

    public void pivotIntakeR(){
        basketR.setPosition(basketR.getPosition()-.01);
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

    public void extend(double power){
        intake.setPower(power);
    }

    public void retract(double power){
        intake.setPower(-power);
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
        pivotR.setPosition(.24);
        pivotL.setPosition(.78);
    }

    public void pivotMid(){
        pivotR.setPosition(.59);
        pivotL.setPosition(.43);
    }

    public void pivotInit(){
        pivotR.setPosition(.92);
        pivotL.setPosition(.1);
    }

    public void pivotUp(){
        pivotR.setPosition(1);
        pivotL.setPosition(0);
    }

}
