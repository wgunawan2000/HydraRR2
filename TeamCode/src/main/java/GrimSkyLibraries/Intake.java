package GrimSkyLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public DcMotor intake;
    public CRServo collection;
    public Servo pivot;
    LinearOpMode opMode;

    public Intake(LinearOpMode opMode)throws InterruptedException {

        this.opMode = opMode;
        intake = this.opMode.hardwareMap.dcMotor.get("intake");
        collection = this.opMode.hardwareMap.crservo.get("collection");
        pivot = this.opMode.hardwareMap.servo.get("pivot");
    }

    public void extend(){
        intake.setPower(1);
    }

    public void retract(){
        intake.setPower(-1);
    }

    public void collectOut(){
        collection.setPower(.8);
    }

    public void collectionIn(){
        collection.setPower(-.8);
    }

    public void collectionStop(){
        collection.setPower(0);
    }

    public void pivotUp(){
        pivot.setPosition(.7);
    }

    public void pivotMid(){
        pivot.setPosition(.3);
    }

    public void pivotDown(){
        pivot.setPosition(0);
    }

}
