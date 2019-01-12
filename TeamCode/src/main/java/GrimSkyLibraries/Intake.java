package GrimSkyLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public DcMotor intake;
    public CRServo collectionL;
    public CRServo collectionR;
    public Servo intakePivotR;
    public Servo intakePivotL;
    LinearOpMode opMode;

    public Intake(LinearOpMode opMode) throws InterruptedException {
        this.opMode = opMode;
        intake = this.opMode.hardwareMap.dcMotor.get("intake");
        collectionR = this.opMode.hardwareMap.crservo.get("collectionR");
        collectionL = this.opMode.hardwareMap.crservo.get("collectionL");
        intakePivotR = this.opMode.hardwareMap.servo.get("intakePivotR");
        intakePivotL = this.opMode.hardwareMap.servo.get("intakePivotL");
        intakePivotInit();
    }

    public void extend(){
        intake.setPower(1);
    }

    public void retract(){
        intake.setPower(-.3);
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

    public void pivotMid(){
//        intakePivotR.setPosition(.25);
        intakePivotL.setPosition(.43);
    }

    public void intakePivotInit(){
//        intakePivotR.setPosition(.18);
        intakePivotL.setPosition(.1);
    }

    public void pivotUp(){
        intakePivotR.setPosition(1);
        intakePivotL.setPosition(0);
    }

    public void pivotDown(){
        intakePivotR.setPosition(.22);
        intakePivotL.setPosition(.78);
    }

}
