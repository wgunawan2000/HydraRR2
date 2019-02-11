package GrimSkyLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import GrimSky.GrimSkyOpMode;

public class Lift {
    DcMotor lift;

    Servo basketR;
    Servo basketL;

    LinearOpMode opMode;

    ElapsedTime times;
    public Lift(LinearOpMode opMode) {
        this.opMode = opMode;
        lift = opMode.hardwareMap.dcMotor.get("lift");
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        basketL = opMode.hardwareMap.servo.get("basketL");
        basketR = opMode.hardwareMap.servo.get("basketR");
        times = new ElapsedTime();
        basketsInit();
    }

    public void setBrake() {
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setFloat() {
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }


    public void basketsInit(){
        initL();
        initR();
    }

    public void markerOut() {
        basketR.setPosition(.1);
    }

    public void outBackL(){
        basketL.setPosition(.37);
    }

    public void outBackR(){
        basketR.setPosition(.66);
    }

    public void outFrontL(){
        basketL.setPosition(.02);
    }

    public void outFrontR(){
        basketR.setPosition(.98);
    }

    public void initL(){
        basketL.setPosition(.88);
    }

    public void initR(){
        basketR.setPosition(.15);
    }


    public void moveMarker(double power, double encoder) throws InterruptedException{
        resetEncoder();
        while (getEncoder() < encoder) {
            lift.setPower(power);
        }
        lift.setPower(.2);
    }
    public void move(double power, double encoder) throws InterruptedException{
        resetEncoder();
        while (getEncoder() < encoder) {
            lift.setPower(power);
        }
        lift.setPower(0);
    }

    public void moveTime(double power, double millis) throws InterruptedException{
        times.reset();
        while (times.milliseconds() < millis) {
            lift.setPower(power);
        }
        lift.setPower(0);
    }

    public void setPower(double power){
        lift.setPower(power);
    }

//    public void TMPEncoder(double distance) throws InterruptedException{
//        resetEncoder();
//        double dT = .05;
//        double MAX_VELOCITY = 7000;
//        double MAX_ACCEL = 14000;
//        double ACCEL_TIME = MAX_VELOCITY / MAX_ACCEL;
//        double CRUISE_TIME = (distance - MAX_ACCEL * Math.pow(ACCEL_TIME, 2)) / MAX_VELOCITY;
//        //if the profile is triangular
//        if (CRUISE_TIME < 0) {
//            CRUISE_TIME = 0;
//            ACCEL_TIME = Math.sqrt(distance / MAX_ACCEL) / 2;
//        }
//        double TOTAL_TIME = 2 * ACCEL_TIME + CRUISE_TIME;
//        double t = 0;
//        double power = 0;
//        times.reset();
//        while((t = times.seconds()) < TOTAL_TIME){
//            if (t <= ACCEL_TIME){
//                lift.setPower((power += MAX_ACCEL * dT) / MAX_VELOCITY);
//            }
//            else if (t <= ACCEL_TIME + CRUISE_TIME){
//                lift.setPower(1);
//            }
//            else if (t <= TOTAL_TIME){
//                lift.setPower((power -= MAX_ACCEL * dT) / MAX_VELOCITY);
//            }
//            opMode.telemetry.addLine("power " + lift.getPower());
//            opMode.telemetry.update();
//        }
//    }
    public int getEncoder(){
        return (Math.abs(lift.getCurrentPosition()));
    }

    public void resetEncoder() throws InterruptedException {
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
    }
}
