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
        pivotUp();
        times = new ElapsedTime();
    }

    public void gateDown(){
        gate.setPosition(0);
    }

    public void gateUp(){
        gate.setPosition(1);
    }

    public void intakeMotorStop() {
        intake.setPower(0);
    }

    public void initMove(double power){
        intake.setPower(power);
    }
    public void move(double power, double inches, double timeout) throws InterruptedException{
        resetEncoder();
        times.reset();
        while(getEncoder() < inches*25 && times.seconds() < timeout && opMode.opModeIsActive()) {
            intake.setPower(power);
        }
        intake.setPower(0);
    }

    public void collectionOut(){
        collectionL.setPower(.3);
    }

    public void collectionIn(){
        collectionL.setPower(-.8);
    }

    public void collectionStop(){
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
        pivotR.setPosition(.7);
        pivotL.setPosition(.33);
    }

    public void pivotUp(){
        pivotR.setPosition(.76);
        pivotL.setPosition(.25);
    }

    public void pivotOver(){
        pivotR.setPosition(.82);
        pivotL.setPosition(.19);
    }

    public void pivotIntakeL(){
        pivotL.setPosition(pivotL.getPosition()+.01);
    }

    public void pivotIntakeR(){
        pivotR.setPosition(pivotR.getPosition()-.01);
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
