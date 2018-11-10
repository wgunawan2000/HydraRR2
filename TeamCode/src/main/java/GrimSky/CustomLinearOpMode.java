package GrimSky;

import android.graphics.Bitmap;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsUsbServoController;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import for_camera_opmodes.LinearOpModeCamera;

/**
 * Created by Bo on 9/13/2017.
 */
public class CustomLinearOpMode extends LinearOpModeCamera {
    DcMotor BL;
    DcMotor ML;
    DcMotor FL;
    DcMotor BR;
    DcMotor MR;
    DcMotor FR;
    DcMotor lift;
    Servo marker;
    CRServo pto;
    public BNO055IMU gyro;
    LinearOpMode opMode;
    Orientation angles;
    Acceleration gravity;
    BNO055IMU.Parameters parameters;
    Rev2mDistanceSensor distanceL;
    Rev2mDistanceSensor distanceR;
    ElapsedTime times;

    @Override
    public void runOpMode() throws InterruptedException {

    }

    public void initialize() throws InterruptedException {
        times = new ElapsedTime();
        BL = this.opMode.hardwareMap.dcMotor.get("BL");
        ML = this.opMode.hardwareMap.dcMotor.get("ML");
        FL = this.opMode.hardwareMap.dcMotor.get("FL");
        BR = this.opMode.hardwareMap.dcMotor.get("BR");
        MR = this.opMode.hardwareMap.dcMotor.get("MR");
        FR = this.opMode.hardwareMap.dcMotor.get("FR");
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
        MR.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addData("init", "1");
        telemetry.update();
        sleep(2500);
        lift = opMode.hardwareMap.dcMotor.get("lift");
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        marker = this.opMode.hardwareMap.servo.get("Marker");
        pto = this.opMode.hardwareMap.crservo.get("pto");
        distanceL = opMode.hardwareMap.get(Rev2mDistanceSensor.class, "distanceL");
        distanceR = opMode.hardwareMap.get(Rev2mDistanceSensor.class, "distanceR");
        telemetry.addData("init", "2");
        telemetry.update();
        sleep(2500);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        gyro = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        gyro.initialize(parameters);
        telemetry.addData("init", "3");
        telemetry.update();
        sleep(2500);
        markerUp();
        telemetry.addData("init", "done");
        telemetry.update();
    }

    //============================ UNHANG METHODS ==================================================
    public void unhang() throws InterruptedException {
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        resetEncoders();

        times.reset();
        //raise lift
        while (getEncoderL() < 250 && times.milliseconds() < 2000) {
            startMotors(-.35, 0);
        }
        stopMotors();
        Thread.sleep(1000);

        //disengage
        times.reset();
        while(times.milliseconds() < 150) {
            setPTO(1);
        }
        setPTO(0);
        Thread.sleep(1000);

        //move lift to unhang
        moveLift(1, 140);
        Thread.sleep(500);
        move(.2, 1);
        Thread.sleep(500);
        stopMotors();
    }

    public void pinUnhang() throws InterruptedException{
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
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
        while(times.milliseconds() < 150) {
            setPTO(1);
        }
        setPTO(0);
        Thread.sleep(1000);

        //move lift to unhang
        moveLift(1, 140);
        Thread.sleep(500);
        move(.2, 1);
        Thread.sleep(500);
        stopMotors();

    }

    //==================================== DRIVETRAIN ==============================================
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
            opMode.telemetry.addData("lift", getLiftEncoder());
            opMode.telemetry.update();
            startMotors(power, power);
        }
        stopMotors();
    }

    //simple threshold distance move methods
    public void distanceRMove(double power, double distance) {
        while(getDistanceR() > distance){
            startMotors(power, power);
        }
        stopMotors();
    }

    public void distanceLMove(double power, double distance) {
        while(getDistanceL() > distance){
            startMotors(power, power);
        }
        stopMotors();
    }

    public void distanceMove(double power, double distance) {
        while(getAvgDistance() > distance){
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

    //main turning method
    public void turnPI(double angle, double p, double i, double timeout) {
        times.reset();
        double kP = p / 90;
        double kI = i / 100000;
        double currentTime = times.milliseconds();
        double pastTime = 0;
        double P = 0;
        double I = 0;
        double angleDiff = getTrueDiff(angle);
        double changePID = 0;
        while (Math.abs(angleDiff) > 1 && times.seconds() < timeout) {
            pastTime = currentTime;
            currentTime = times.milliseconds();
            double dT = currentTime - pastTime;
            angleDiff = getTrueDiff(angle);
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


    //====================================== SENSORS ===============================================
    public double getDistanceL(){
        double dist = distanceL.getDistance(DistanceUnit.CM);
        while (dist > 1000 || Double.isNaN(dist))
            dist = distanceL.getDistance(DistanceUnit.CM);
        return dist;
    }

    public double getDistanceR(){
        double dist = distanceR.getDistance(DistanceUnit.CM);
        while (dist > 1000 || Double.isNaN(dist))
            dist = distanceR.getDistance(DistanceUnit.CM);
        return dist;
    }

    public double getAvgDistance(){
        return (getDistanceL() + getDistanceR())/2;
    }

    //gyro methods
    public double getGyroYaw() {
        updateValues();
        double yaw = angles.firstAngle * -1;
        if(angles.firstAngle < -180)
            yaw -= 360;
        return yaw;
    }

    public double getGyroPitch() {
        updateValues();
        double pitch = angles.secondAngle;
        return pitch;
    }

    public double getGyroRoll(){
        updateValues();
        double roll = angles.thirdAngle;
        return roll;
    }

    //returns -x if the robot needs to turn x degrees to the left, or x if the robot needs to turn x degrees to the right
    public double getTrueDiff(double origAngle) {
        double currAngle = getGyroYaw();
        if (currAngle >= 0 && origAngle >= 0 || currAngle <= 0 && origAngle <= 0)
            return -(currAngle - origAngle);
        else if (Math.abs(currAngle - origAngle) <= 180)
            return -(currAngle - origAngle);
        else if (currAngle > origAngle)
            return (360 - (currAngle - origAngle));
        else
            return -(360 + (currAngle - origAngle));
    }

    public boolean resetGyro() {
        return gyro.initialize(parameters);
    }

    public void updateValues() {
        angles = gyro.getAngularOrientation();
    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

    //================================= MARKER/PTO/LIFT ============================================
    public void markerUp() {
        marker.setPosition(.53);
    }

    public void markerDown() {
        marker.setPosition(0);
    }

    public void markerBack() {
        marker.setPosition(.85);
    }

    public void setPTO(double power){
        pto.setPower(power);
    }

    public void moveLift(double power, double encoder) throws InterruptedException{
        resetLiftEncoder();
        while (getLiftEncoder() < encoder) {
            lift.setPower(power);
        }
        lift.setPower(0);
    }

    public int getLiftEncoder(){
        return (Math.abs(lift.getCurrentPosition()));
    }

    public void resetLiftEncoder() throws InterruptedException {
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
    }

}