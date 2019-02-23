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

    double L = 1;
    double R = .1;
    private boolean liftIsUp = false;
    public static double liftHeight = 1100;


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
        basketsInit();
        pivotDown();

        telemetry.addData("init ", "completed");
        telemetry.update();
    }

    //============================= OUTPUT =========================================================
    public void basketsInit(){
        initL();
        initR();
    }

    public void outBackL(){
        basketL.setPosition(.37);
    }

    public void outBackR(){
        basketR.setPosition(.66);
    }

    public void outMidL(){
        basketL.setPosition(.61);
    }

    public void outMidR(){
        basketR.setPosition(.4);
    }

    public void outFrontL(){
        basketL.setPosition(.02);
    }

    public void outFrontR(){
        basketR.setPosition(.98);
    }

    public void transitionL(){
        basketL.setPosition(.91);
    }

    public void transitionR(){
        basketR.setPosition(.12);
    }

    public void initL(){
        basketL.setPosition(.86);
    }

    public void initR(){
        basketR.setPosition(.17);
    }

    public void pivotBasketL(){
        pivotL.setPosition(pivotL.getPosition() - .01);
        pivotR.setPosition(pivotR.getPosition() + .01);

    }

    public void pivotBasketR(){

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
        lift.setPower(power);
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
        collectionR.setPower(-.5);
        collectionL.setPower(.5);
    }

    public void collectionIn(){
        collectionR.setPower(.5);
        collectionL.setPower(-.5);
    }

    public void collectionSlow(){
        collectionR.setPower(.2);
        collectionL.setPower(-.2);
    }

    public void collectionStop(){
        collectionR.setPower(0);
        collectionL.setPower(0);
    }

    public void pivotDown(){
        pivotR.setPosition(.2);
        pivotL.setPosition(.82);
    }

    public void pivotMid(){
        pivotR.setPosition(.38);
        pivotL.setPosition(.65);
//        collectionL.setPower(-.2);
//        collectionR.setPower(.2);
    }

    public void pivotInit(){
        pivotR.setPosition(.78);
        pivotL.setPosition(.25);
    }

    public void pivotUp(){
        pivotR.setPosition(.73);
        pivotL.setPosition(.28);
    }

    public void pivotOver(){
        pivotR.setPosition(.80);
        pivotL.setPosition(.21);
    }

    public int getLiftEncoder(){
        return (Math.abs(lift.getCurrentPosition()));
    }

    void activateLift() {
        setLift(1);
    }

    void unactivateLift() {
        setLift(0);
    }

    private Runnable moveLift = new Runnable() {

        @Override
        public void run() {
            while(liftIsUp) {
                while(getLiftEncoder() < 1400) {
                    setLift(1);
                }
                setLift(.2);

                if(Thread.currentThread().isInterrupted())
                    liftIsUp = false;
            }
            Thread.currentThread().interrupt();
        }
    };

}
