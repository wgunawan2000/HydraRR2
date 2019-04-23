package GrimSkyLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Intake {
    public DcMotor intake;
    public CRServo collectionL;
    public Servo pivotR;
    public Servo pivotL;
    public Servo gate;
    LinearOpMode opMode;
    ElapsedTime times;
    public Intake(LinearOpMode opMode) throws InterruptedException {
        this.opMode = opMode;
        gate = this.opMode.hardwareMap.servo.get("gate");
        intake = this.opMode.hardwareMap.dcMotor.get("intake");
        collectionL = this.opMode.hardwareMap.crservo.get("collectionL");
        pivotR = this.opMode.hardwareMap.servo.get("pivotR");
        pivotL = this.opMode.hardwareMap.servo.get("pivotL");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        pivotInit();
        times = new ElapsedTime();
    }

    public void initMove(double power){
        intake.setPower(power);
    }
    public void move(double power, double inches, double timeout) throws InterruptedException{
        resetEncoder();
        times.reset();
        while(opMode.opModeIsActive() && getEncoder() < inches*25 && times.seconds() < timeout) {
            intake.setPower(power);
        }
        intake.setPower(0);
    }

    public void transition(){
        pivotUp();
        gateUp();
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

    public void pivotMiddle() {
        pivotR.setPosition(.5);
        pivotL.setPosition(.5);
    }

    public void pivotInit(){
        pivotR.setPosition(.08);
        pivotL.setPosition(.92);
    }

    public void pivotUp(){
        pivotR.setPosition(0.01);
        pivotL.setPosition(.99);
    }
    public int getEncoder(){
        return Math.abs(intake.getCurrentPosition());
    }
    public void resetEncoder(){
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
    }

}
