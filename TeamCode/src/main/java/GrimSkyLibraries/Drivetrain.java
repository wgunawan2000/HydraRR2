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
        sensor = new Sensors(opMode);
        times = new ElapsedTime();
        this.opMode = opMode;
        nullValue = 0;
        BL = this.opMode.hardwareMap.dcMotor.get("BL");
        ML = this.opMode.hardwareMap.dcMotor.get("ML");
        FL = this.opMode.hardwareMap.dcMotor.get("FL");
        BR = this.opMode.hardwareMap.dcMotor.get("BR");
        MR = this.opMode.hardwareMap.dcMotor.get("MR");
        FR = this.opMode.hardwareMap.dcMotor.get("FR");
        this.opMode.telemetry.addData(LOG_TAG + "init", "finished init");
        this.opMode.telemetry.update();
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
        MR.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void startMotors(double left, double right){
        BL.setPower(-left);
        ML.setPower(-left);
        FL.setPower(-left);
        BR.setPower(-right);
        MR.setPower(-right);
        FR.setPower(-right);
    }

    public void move(double power, int encoder) throws InterruptedException{
        resetEncoders();
        while(getEncoderAvg() < encoder) {
            startMotors(power, power);
        }
        stopMotors();
    }

    public void distanceRMove(double power, double distance) {
        while(sensor.getDistanceR() > distance){
            startMotors(power, power);
        }
        stopMotors();
    }

    public void distanceLMove(double power, double distance) {
        while(sensor.getDistanceL() > distance){
            startMotors(power, power);
        }
        stopMotors();
    }

    public void distanceMove(double power, double distance) {
        while(sensor.getAvgDistance() > distance){
            startMotors(power, power);
        }
        stopMotors();
    }

    public void turn(double power, double angle)
    {
        times.reset();
        double kP = .6/90;
        double changePID = 0;
        double angleDiff = sensor.getGyroTrueDiff(angle);

        while (Math.abs(angleDiff) > 3 && opMode.opModeIsActive() && times.seconds() < 5)
        {
            angleDiff = sensor.getGyroTrueDiff(angle);
            changePID = angleDiff * kP;
            if (changePID > 0)
            {
                startMotors(Range.clip(-changePID - .1, -power, power), Range.clip(changePID + .1, -power, power));
            }
            else
            {
                startMotors(Range.clip(changePID + .1, -power, power), Range.clip(-changePID - .1, -power, power));
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
        int count = 4;
        if ((FR.getCurrentPosition()) == -1){
            count--;
        }
        if ((FL.getCurrentPosition()) == -1){
            count--;
        }
        if ((MR.getCurrentPosition()) == -1){
            count--;
        }
        if ((ML.getCurrentPosition()) == -1){
            count--;
        }
        return (Math.abs(FR.getCurrentPosition()) +  Math.abs(FL.getCurrentPosition())
                + Math.abs(MR.getCurrentPosition())
                + Math.abs(ML.getCurrentPosition())) / count;
    }
}

