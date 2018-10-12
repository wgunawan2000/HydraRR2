package GrimSkyLibraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Lift {
    DcMotor lift;
    LinearOpMode opMode;

    public Lift(LinearOpMode opMode) {
        this.opMode = opMode;
        lift = opMode.hardwareMap.dcMotor.get("lift");
    }

    public void detach(){
        liftUp(.009, 2.5);

    }


    public void liftUp(double kP, double rotation){
        double floor = .1;
        int currPos = lift.getCurrentPosition();
        int goalPos = currPos + (int)(rotation * 1400);
        while(currPos < goalPos){
            currPos = lift.getCurrentPosition();
            lift.setPower(floor + kP*(goalPos-currPos));
        }
    }

    public void liftDown(double kP, double floor){
        floor *= -1;
        int currPos = lift.getCurrentPosition();
        int goalPos = currPos - 4200;
        while(currPos < goalPos){
            currPos = lift.getCurrentPosition();
            lift.setPower(floor + kP*(goalPos-currPos));
        }
    }
}
