package GrimSkyLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public DcMotor extend;
    public CRServo intakeL;
    public CRServo intakeR;
    LinearOpMode opMode;

    public Intake(LinearOpMode opMode)throws InterruptedException {

        this.opMode = opMode;
        intakeL = this.opMode.hardwareMap.crservo.get("intakeL");
        intakeR = this.opMode.hardwareMap.crservo.get("intakeR");
    }

    public void intakeOut(){
        intakeL.setPower(.8);
        intakeR.setPower(-.8);
    }

    public void intakeIn(){
        intakeL.setPower(-.8);
        intakeR.setPower(.8);
    }

    public void intakeStop(){
        intakeL.setPower(0);
        intakeR.setPower(0);
    }

}
