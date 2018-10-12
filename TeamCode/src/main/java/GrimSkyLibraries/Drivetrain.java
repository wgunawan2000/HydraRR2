package GrimSkyLibraries;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


public class Drivetrain {
    public DcMotor BL;
    public DcMotor ML;
    public DcMotor FL;
    public DcMotor BR;
    public DcMotor MR;
    public DcMotor FR;
    Sensors sensor;
    LinearOpMode opMode;
    ElapsedTime times;
    int nullValue;
    double angleError;

    public boolean reversed;

    private final String LOG_TAG = "DriveTrain";
    public Drivetrain(LinearOpMode opMode)throws InterruptedException {
        this.opMode = opMode;
        nullValue = 0;
        BL = this.opMode.hardwareMap.dcMotor.get("BL");
        ML = this.opMode.hardwareMap.dcMotor.get("ML");
        FL = this.opMode.hardwareMap.dcMotor.get("FL");
        BR = this.opMode.hardwareMap.dcMotor.get("BR");
        MR = this.opMode.hardwareMap.dcMotor.get("MR");
        FR = this.opMode.hardwareMap.dcMotor.get("FR");
        this.opMode.telemetry.addData(LOG_TAG + "init", "finished drivetrain init");
        this.opMode.telemetry.update();
        this.opMode.telemetry.addData(LOG_TAG + "init", "init finished");
        this.opMode.telemetry.update();
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        ML.setDirection(DcMotorSimple.Direction.REVERSE);
        FL.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void startMotors(double left, double right){
        BL.setPower(left);
        ML.setPower(left);
        FL.setPower(left);
        BR.setPower(right);
        MR.setPower(right);
        FR.setPower(right);
    }

    public void move (double l, double r) {
        startMotors(l, r);
    }

    public void move(double power, int encoder){
        while(getEncoderAvg() < encoder) {
            move(power, power);
        }
    }

    public void turn(double power, double angle){
        double kP = .7/90;
        double changePID;
        double angleDiff = sensor.getGyroTrueDiff(angle);
        while(Math.abs(angleDiff) > 0.5 && opMode.opModeIsActive() && times.seconds() < 2) {
            angleDiff = sensor.getGyroTrueDiff(angle);
            changePID = angleDiff * kP;

            if (changePID < 0) {
                move(changePID - .1, changePID + .1);
            } else {
                move(changePID + .1, changePID - .1);
            }
        }
        stopMotors();
    }

    public void stopMotors() {
        BL.setPower(0);
        ML.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        MR.setPower(0);
        FR.setPower(0);
    }

    public void resetEncoders() throws InterruptedException {
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        MR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        ML.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();


        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        MR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        ML.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
    }

    public int getEncoderAvg() {
        return ((Math.abs(BR.getCurrentPosition())) +
                Math.abs(FR.getCurrentPosition()) +
                (Math.abs(BL.getCurrentPosition())) +
                Math.abs(FL.getCurrentPosition()) +
                Math.abs(MR.getCurrentPosition()) +
                Math.abs(ML.getCurrentPosition())) / 6;
    }

//    public void movepid(double power, int distance, double floor, double kP, double kI, double kD, int accuracy, double rotation) throws InterruptedException {
//
//        double error;
//        double inte = 0;
//        double der;
//        double previousRunTime;
//
//        resetEncoders();
//
//        double previousError = distance - getEncoderAvg();
//
//        ElapsedTime runtime = new ElapsedTime();
//
////        opMode.telemetry.addData("distance left", distance + "");
////        opMode.telemetry.addData("current Encoder", getEncoderAvg() + "");
////        opMode.telemetry.update();
//
//        runtime.reset();
//
//        while(getEncoderAvg() < (distance - accuracy)) {
//            error = Math.abs(distance) - Math.abs(getEncoderAvg());
//            previousRunTime = runtime.seconds();
//            power = (power * (error) * kP) + floor;
//            inte += ((runtime.seconds() - previousRunTime) * error * kI);
//            der = (error - previousError) / (runtime.seconds() - previousRunTime) * kD;
//
//            power = power + inte - der;
//
//            Range.clip(power, -1, 1);
//            move(-power, (-power);
//
//            opMode.telemetry.addData("error", error);
//            opMode.telemetry.addData("PID", power);
//            opMode.telemetry.addData("integral", inte);
//            opMode.telemetry.addData("Encoder", getEncoderAvg());
//
//            opMode.telemetry.update();
////            opMode.telemetry.addData("integral", inte);
//            previousError = error;
//            opMode.idle();
//        }
//
//        opMode.telemetry.update();
//        stopMotors();
//    }
}