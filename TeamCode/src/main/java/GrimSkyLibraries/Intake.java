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
    public CRServo collectionR;
    public Servo pivotR;
    public Servo pivotL;
    LinearOpMode opMode;
    ElapsedTime times;
    public Intake(LinearOpMode opMode) throws InterruptedException {
        this.opMode = opMode;
        intake = this.opMode.hardwareMap.dcMotor.get("intake");
        collectionR = this.opMode.hardwareMap.crservo.get("collectionR");
        collectionL = this.opMode.hardwareMap.crservo.get("collectionL");
        pivotR = this.opMode.hardwareMap.servo.get("pivotR");
        pivotL = this.opMode.hardwareMap.servo.get("pivotL");
        pivotInit();
        times = new ElapsedTime();
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
        while(getEncoder() < inches*25 && times.seconds() < timeout) {
            intake.setPower(power);
        }
        intake.setPower(0);
    }

    public void collectionOut(){
        collectionR.setPower(-.3);
        collectionL.setPower(.3);
    }

    public void collectionIn(){
        collectionR.setPower(.8);
        collectionL.setPower(-.8);
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
        pivotR.setPosition(.52);
        pivotL.setPosition(.52);
    }

    public void pivotInit(){
        pivotR.setPosition(.78);
        pivotL.setPosition(.25);
    }

    public void pivotUp(){
        pivotR.setPosition(.72);
        pivotL.setPosition(.29);
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
