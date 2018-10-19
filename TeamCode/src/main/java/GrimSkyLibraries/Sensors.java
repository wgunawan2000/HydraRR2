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
//        distanceL = (Rev2mDistanceSensor) opMode.hardwareMap.opticalDistanceSensor.get("distanceL");
//        distanceR = opMode.hardwareMap.i2cDevice.get("distanceR");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        gyro = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        gyro.initialize(parameters);
    }

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

    public double getGyroYaw() {
        updateValues();
        double yaw = angles.secondAngle * -1;
        if(angles.firstAngle < -180)
            yaw -= 360;
        return yaw;
    }

    public double getGyroTrueDiff(double origAngle) {
        updateValues();
        double currAngle = getGyroYaw();
        if (currAngle >= 0 && origAngle >= 0 || currAngle <= 0 && origAngle <= 0)
            return currAngle - origAngle;
        else if (Math.abs(currAngle - origAngle) <= 180)
            return currAngle - origAngle;
        else if (currAngle > origAngle)
            return -(360 - (currAngle - origAngle));
        else
            return (360 + (currAngle - origAngle));
    }

    public double getGyroPitch() {
        updateValues();
        double pitch = angles.firstAngle;
        return pitch;
    }

    public double getGyroRoll(){
        updateValues();
        double roll = angles.secondAngle;
        return roll;
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
