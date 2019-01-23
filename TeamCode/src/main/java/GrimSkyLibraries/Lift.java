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

    public void outL(){
        basketL.setPosition(.33);
    }

    public void outR(){
        basketR.setPosition(.65);
    }

    public void initL(){
        basketL.setPosition(0);
    }

    public void initR(){
        basketR.setPosition(1);
    }

    public void move(double power, double encoder) throws InterruptedException{
        resetEncoder();
        while (getEncoder() < encoder) {
            lift.setPower(power);
        }
        lift.setPower(0);
    }

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
