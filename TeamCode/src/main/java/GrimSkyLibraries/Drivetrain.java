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

    //the left side has slightly more friction
    public void startMotors(double left, double right){
        if((Math.abs(1.12*left) > 1)){
            left /= 1.12;
            right /= 1.12;
        }
        BL.setPower(-1.12*left);
        ML.setPower(-1.12*left);
        FL.setPower(-1.12*left);
        BR.setPower(-right);
        MR.setPower(-right);
        FR.setPower(-right);
    }

    //simple threshold encoder move method
    public void move(double power, int encoder) throws InterruptedException{
        resetEncoders();
        while(getEncoderAvg() < encoder) {
            startMotors(power, power);
        }
        stopMotors();
    }

    //simple threshold distance move methods
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

    //these methods apply more power to the side opposite the wall in order to wall roll well
    public void wallRollR(double power, int encoder) throws InterruptedException{
        resetEncoders();
        while(getEncoderAvg() < encoder) {
            if(power * 1.3 > 1){
                power /= 1.3;
            }
            startMotors(power * 1.3, power);
        }
        stopMotors();
    }

    public void wallRollL(double power, int encoder) throws InterruptedException{
        resetEncoders();
        while(getEncoderAvg() < encoder) {
            if(power * 1.3 > 1){
                power /= 1.3;
            }
            startMotors(power, power * 1.3);
        }
        stopMotors();
    }

    public void turnPI(double angle)
        {
            times.reset();
            double kP = .33/100;
            double kI = .5/100000000;
            double currentTime = System.currentTimeMillis()*1000;
            double pastTime = 0;
            double P = 0;
            double I = 0;
            double angleDiff = sensor.getGyroTrueDiff(angle);
            double changePID = 0;
            while (Math.abs(angleDiff) > .5 && opMode.opModeIsActive() && times.seconds() < 5)
            {
                pastTime = currentTime;
                currentTime = System.currentTimeMillis()*1000;
                double dT = currentTime - pastTime;
                angleDiff = sensor.getGyroTrueDiff(angle);
                P = angleDiff * kP;
                I += dT * angleDiff * kI;
                changePID = P + I;
                opMode.telemetry.addData("P", P);
                opMode.telemetry.addData("I", I);
                opMode.telemetry.addData("PID: ", changePID);
                opMode.telemetry.addData("Gyro", sensor.getGyroYaw());
                opMode.telemetry.update();
                if (changePID < 0) {
                    startMotors(-changePID - .15, -changePID + .15);
                }
                else{
                    startMotors(-changePID + .15, changePID - .15);
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

    public int getEncoderR(){
        int count = 2;
        if ((FR.getCurrentPosition()) == -1){
            count--;
        }
        if ((MR.getCurrentPosition()) == -1) {
            count--;
        }
        return (Math.abs(FR.getCurrentPosition()) + Math.abs(MR.getCurrentPosition())) / count;
    }

    public int getEncoderL(){
        int count = 2;
        if ((FL.getCurrentPosition()) == -1){
            count--;
        }
        if ((ML.getCurrentPosition()) == -1) {
            count--;
        }
        return (Math.abs(FL.getCurrentPosition()) + Math.abs(ML.getCurrentPosition())) / count;
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

