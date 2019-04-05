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
    CRServo collectionL;

    Servo sorter;
    Servo pivotR;
    Servo pivotL;
    Servo gate;

    Servo basketL;
    Servo basketR;

    double L = 1;
    double R = .1;
    private boolean liftIsUp = false;
    public static double liftHeight = 2000;


    public void init() {
        FR = hardwareMap.dcMotor.get("FR");
        FL = hardwareMap.dcMotor.get("FL");
        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");
        ML = hardwareMap.dcMotor.get("ML");
        MR = hardwareMap.dcMotor.get("MR");

        lift = hardwareMap.dcMotor.get("lift");

        pto = hardwareMap.crservo.get("pto");
        intake = hardwareMap.dcMotor.get("intake");
        pivotR = hardwareMap.servo.get("pivotR");
        collectionL = hardwareMap.crservo.get("collectionL");
        pivotL = hardwareMap.servo.get("pivotL");
        gate = hardwareMap.servo.get("gate");

        basketL = hardwareMap.servo.get("basketL");
        basketR = hardwareMap.servo.get("basketR");

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
        basketsInit();
        pivotDown();
        gateDown();

        telemetry.addData("init ", "completed");
        telemetry.update();
    }

    //============================= OUTPUT =========================================================
    public void basketsInit(){
        initL();
        initR();
    }

    public void basketsMid(){
        outMidL();
        outMidR();
    }

    public void outDepot(){
        outDepotL();
        outDepotR();
    }

    public void outDepotL(){
        basketL.setPosition(.62);
    }

    public void outDepotR(){
        basketR.setPosition(.41);
    }

    public void outBackL(){
        basketL.setPosition(.58);
    }

    public void outBackR(){
        basketR.setPosition(.45);
    }

    public void outMidL(){
        basketL.setPosition(.25);
    }

    public void outMidR(){
        basketR.setPosition(.78);
    }

    public void transitionL(){
        basketL.setPosition(.91);
    }

    public void transitionR(){
        basketR.setPosition(.12);
    }

    public void initL(){
        basketL.setPosition(.11);
    }

    public void initR(){
        basketR.setPosition(.92);
    }

    public void pivotBasketL(){
        pivotL.setPosition(pivotL.getPosition() - .01);

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
        intake.setPower(-power );
    }

    public void intakeMotorStop() {
        intake.setPower(0);
    }

    public void collectionOut(){
        collectionL.setPower(.85);
    }

    public void collectionIn(){
        collectionL.setPower(-.9);
    }

    public void collectionSlow(){
        collectionL.setPower(-.3);
    }

    public void collectionStop(){
        collectionL.setPower(0);
    }

    public void gateUp(){
        gate.setPosition(.86);
    }

    public void gateDown(){
        gate.setPosition(1);
    }

    public void gateMid(){
        gate.setPosition(.5);
    }

    public void pivotDown(){
        pivotR.setPosition(.99);
        pivotL.setPosition(.01);
    }

    public void pivotTransition(){
        pivotR.setPosition(.8);
        pivotL.setPosition(.2);
    }

    public void pivotMid(){
        pivotR.setPosition(.6);
        pivotL.setPosition(.4);
    }

    public void pivotInit(){
        pivotR.setPosition(.78);
        pivotL.setPosition(.25);
    }

    public void pivotUp(){
        pivotR.setPosition(0.01);
        pivotL.setPosition(.99);
    }

    public void pivotOver(){
        pivotR.setPosition(.80);
        pivotL.setPosition(.21);
    }

    public int getLiftEncoder(){
        return (Math.abs(lift.getCurrentPosition()));
    }

    public void resetLiftEncoder(){
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
