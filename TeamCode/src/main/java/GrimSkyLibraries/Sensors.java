package GrimSkyLibraries;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.Locale;

public class Sensors{

    public BNO055IMU gyro;
    LinearOpMode opMode;
    Orientation angles;
    Acceleration gravity;
    BNO055IMU.Parameters parameters;
    Rev2mDistanceSensor distanceL;
    Rev2mDistanceSensor distanceR;

    public Sensors(LinearOpMode opMode) throws InterruptedException {
        this.opMode = opMode;
        distanceL = opMode.hardwareMap.get(Rev2mDistanceSensor.class, "distanceL");
        distanceR = opMode.hardwareMap.get(Rev2mDistanceSensor.class, "distanceR");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        gyro = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        gyro.initialize(parameters);
    }

    //distance sensor methods
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

}
