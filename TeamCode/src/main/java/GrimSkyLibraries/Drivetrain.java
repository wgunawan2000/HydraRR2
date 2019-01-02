package GrimSkyLibraries;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import GrimSky.Depot;


public class Drivetrain {
    public DcMotor BL;
    public DcMotor ML;
    public DcMotor FL;
    public DcMotor BR;
    public DcMotor MR;
    public DcMotor FR;
    Sensors sensor;
    CRServo pto;
    LinearOpMode opMode;
    ElapsedTime times;
    Lift lift;
    private final String LOG_TAG = "DriveTrain";
    public Drivetrain(LinearOpMode opMode)throws InterruptedException {
        sensor = new Sensors(opMode);
        times = new ElapsedTime();
        lift = new Lift(opMode);
        this.opMode = opMode;
        BL = this.opMode.hardwareMap.dcMotor.get("BL");
        ML = this.opMode.hardwareMap.dcMotor.get("ML");
        FL = this.opMode.hardwareMap.dcMotor.get("FL");
        BR = this.opMode.hardwareMap.dcMotor.get("BR");
        MR = this.opMode.hardwareMap.dcMotor.get("MR");
        FR = this.opMode.hardwareMap.dcMotor.get("FR");
        pto = this.opMode.hardwareMap.crservo.get("pto");

        this.opMode.telemetry.addData(LOG_TAG + "init", "finished init");
        this.opMode.telemetry.update();
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
        MR.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
    }


    public void unhang() throws InterruptedException {
        lift.setFloat();
        resetEncoders();

        times.reset();
        //raise lift
        while (getEncoderL() < 390 && times.milliseconds() < 2000) {
            startMotors(-.35, 0);
        }
        stopMotors();
        Thread.sleep(1000);

        //disengage
        times.reset();
        while(times.milliseconds() < 250) {
            pto.setPower(1);
        }
        pto.setPower(0);
        Thread.sleep(1000);

        //move lift to unhang
        lift.move(1, 400);
        Thread.sleep(500);
        move(.2, 1);
        Thread.sleep(500);
        lift.move(-.5, 190);
        Thread.sleep(500);
        stopMotors();
    }

    public void pinUnhang() throws InterruptedException{
        lift.setFloat();
        resetEncoders();

        times.reset();
        //undo pin
        while (getEncoderL() < 175 && times.milliseconds() < 5000) {
            startMotors(.4, 0);
        }
        stopMotors();
        Thread.sleep(1000);
        resetEncoders();
        times.reset();

        while (getEncoderL() < 80 && times.milliseconds() < 3000) {
            startMotors(-.5, 0);
        }
        stopMotors();

        //disengage
        times.reset();
        while(times.milliseconds() < 100) {
            pto.setPower(1);
        }
        pto.setPower(0);
        Thread.sleep(1000);

        //move lift to unhang
        lift.move(1, 180);
        Thread.sleep(500);
        move(.2, 1);
        Thread.sleep(500);
        stopMotors();

    }

    //the right side has slightly more friction
    public void startMotors(double left, double right){
        if((Math.abs(1.07*left) > 1)){
            left /= 1.07;
            right /= 1.07;
        }
        BL.setPower(-1.07*left);
        BR.setPower(-right);
        ML.setPower(-1.07*left);
        MR.setPower(-right);
        FL.setPower(-1.07*left);
        FR.setPower(-right);
    }

    //simple threshold encoder move method, 25 ticks = ~1 inches//
    public void move(double power, double inches) throws InterruptedException{
        resetEncoders();
        while(getEncoderAvg() < inches*25 ) {
            opMode.telemetry.addData("ML", ML.getCurrentPosition());
            opMode.telemetry.addData("FL", FL.getCurrentPosition());
            opMode.telemetry.addData("MR", MR.getCurrentPosition());
            opMode.telemetry.addData("FR", FR.getCurrentPosition());
            opMode.telemetry.addData("lift", lift.getEncoder());
            opMode.telemetry.update();
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

    //wall roll methods apply more power to the side opposite the wall in order to wall roll
        public void wallRollR(double power, double inches) throws InterruptedException{
            resetEncoders();
            while(getEncoderAvg() < inches*25) {
            if(power * 1.2 > 1){
                power /= 1.2;
            }
            startMotors(power * 1.2, power);
        }
        stopMotors();
    }

    public void wallRollL(double power, double inches) throws InterruptedException{
        resetEncoders();
        while(getEncoderAvg() < inches*25) {
            if(power * 1.15 > 1){
                power /= 1.15;
            }
            startMotors(power, power * 1.15);
        }
        stopMotors();
    }

    public void turnPD(double angle, double p, double d, double timeout){
        times.reset();
        double kP = p / 90;
        double kD = d;
        double currentTime = times.milliseconds();
        double pastTime = 0;
        double prevAngleDiff = sensor.getTrueDiff(angle);
        double angleDiff = prevAngleDiff;
        double changePID = 0;
        while (Math.abs(angleDiff) > .5 && times.seconds() < timeout) {
            pastTime = currentTime;
            currentTime = times.milliseconds();
            double dT = currentTime - pastTime;
            angleDiff = sensor.getTrueDiff(angle);
            changePID = (angleDiff * kP) + ((angleDiff - prevAngleDiff) / dT * kD);
            if (changePID < 0) {
                startMotors(changePID - .10, -changePID + .10);
            } else {
                startMotors(changePID + .10, -changePID - .10);
            }
            opMode.telemetry.addData("P", (angleDiff * kP));
            opMode.telemetry.addData("D", ((Math.abs(angleDiff) - Math.abs(prevAngleDiff)) / dT * kD));
            opMode.telemetry.update();
            prevAngleDiff = angleDiff;
        }
        stopMotors();
    }

    public void turnPI(double angle, double p, double i, double timeout) {
        times.reset();
        double kP = p / 90;
        double kI = i / 100000;
        double currentTime = times.milliseconds();
        double pastTime = 0;
        double P = 0;
        double I = 0;
        double angleDiff = sensor.getTrueDiff(angle);
        double changePID = 0;
        while (Math.abs(angleDiff) > 1 && times.seconds() < timeout) {
            pastTime = currentTime;
            currentTime = times.milliseconds();
            double dT = currentTime - pastTime;
            angleDiff = sensor.getTrueDiff(angle);
            P = angleDiff * kP;
            I += dT * angleDiff * kI;
            changePID = P;
            changePID += I;
            opMode.telemetry.addData("PID: ", changePID);
            opMode.telemetry.addData("diff", angleDiff);
            opMode.telemetry.addData("P", P);
            opMode.telemetry.addData("I", I);
            opMode.telemetry.update();
            if (changePID < 0) {
                startMotors(changePID - .10, -changePID + .10);
            } else {
                startMotors(changePID + .10, -changePID - .10);
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

    //encoder methods, divide by more than the motor count to prevent divide by zero exception
    public int getEncoderR(){
        int count = 3;
        if ((FR.getCurrentPosition()) == 0){
            count--;
        }
        if ((MR.getCurrentPosition()) == 0) {
            count--;
        }
        return (Math.abs(FR.getCurrentPosition()) + Math.abs(MR.getCurrentPosition())) / count;
    }

    public int getEncoderL(){
        int count = 3;
        if ((FL.getCurrentPosition()) == 0){
            count--;
        }
        if ((ML.getCurrentPosition()) == 0) {
            count--;
        }
        return (Math.abs(FL.getCurrentPosition()) + Math.abs(ML.getCurrentPosition())) / count;
    }

    public int getEncoderAvg() {
        int count = 5;
        if ((FR.getCurrentPosition()) == 0){
            count--;
        }
        if ((FL.getCurrentPosition()) == 0){
            count--;
        }
        if ((MR.getCurrentPosition()) == 0){
            count--;
        }
        if ((ML.getCurrentPosition()) == 0){
            count--;
        }
        return (Math.abs(FR.getCurrentPosition()) +  Math.abs(FL.getCurrentPosition())
                + Math.abs(MR.getCurrentPosition())
                + Math.abs(ML.getCurrentPosition())) / count;
    }

}

