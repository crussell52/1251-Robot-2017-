package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Created by Jared on 3/9/2017.
 */
public class TT_MainAuto {

    private static final double BASELINE_DISTANCE = 2.3;

    private static final double LEFT_GEAR_FORWARD = 1.8288;
    private static final double LEFT_GEAR_ROTATION = 60.0;
    private static final double LEFT_GEAR_DISTANCE = 1.65354;
    private static final double LEFT_GEAR_BACKUP = 0.6096;

    private static final double RIGHT_GEAR_FORWARD = 1.8034;
    private static final double RIGHT_GEAR_ROTATION = -60.0;
    private static final double RIGHT_GEAR_DISTANCE = 1.70434;
    private static final double RIGHT_GEAR_BACKUP = 0.6096;

    private static final double MIDDLE_GEAR_DISTANCE = 1.9;
    private static final double MIDDLE_BACKUP_DISTANCE = 1;

    public static int currentReturnVal = 1;
    public static int currentMethodNum = 0;

    public static void autoInit(int autoSelect) {
        switch (autoSelect) {
            case 4:
                TT_Auto.twoGearAutoInit();
                break;
            case 5:
                TT_Auto.twoGearAutoInit();
                break;
            default:
                currentMethodNum = 0;
                TT_Auto.genericInit();
        }
    }
    public static void auto(int autoSelect, RobotDrive baseDrive, DoubleSolenoid baseShifter, SpeedController pivotMotor, DoubleSolenoid claw, Gyro gyro, Encoder lEncoder, Encoder rEncoder) {
        switch(autoSelect){
            case -1:
                baseDrive.tankDrive(0, 0);
                break;

            case 0:
                TT_Auto.breakBaselinePeriodic();

            case 1:
                // left gear auto here
                if (currentReturnVal == 0) {
                    currentMethodNum++;
                }

                if (currentMethodNum < 1) {
                    currentReturnVal = TT_Util.driveForward(baseDrive, baseShifter, lEncoder, rEncoder, LEFT_GEAR_FORWARD);
                } else if (currentMethodNum < 2) {
                    currentReturnVal = TT_Util.rotateBot(LEFT_GEAR_ROTATION, baseDrive, gyro);
                } else if (currentMethodNum < 3) {
                    currentReturnVal = TT_Util.driveForward(baseDrive, baseShifter, lEncoder, rEncoder, LEFT_GEAR_DISTANCE);
                } else if (currentMethodNum < 4) {
                    currentReturnVal = TT_Util.driveBackward(baseDrive, baseShifter, lEncoder, rEncoder, LEFT_GEAR_BACKUP);
                    pivotMotor.set(-0.2);
                    claw.set(DoubleSolenoid.Value.kReverse);
                } else if (currentMethodNum < 5) {
                    currentReturnVal = TT_Util.rotateBot(-LEFT_GEAR_ROTATION, baseDrive, gyro);
                    claw.set(DoubleSolenoid.Value.kForward);
                    pivotMotor.set(0.0);
                } else if (currentMethodNum < 6) {
                    currentReturnVal = TT_Util.driveForward(baseDrive, baseShifter, lEncoder, rEncoder, LEFT_GEAR_BACKUP);
                } else {
                    baseDrive.tankDrive(0, 0);
                }
                break;

            case 2:
                //middle gear auto here
                TT_Auto.middleGear();
                break;

            case 3:
                // right gear auto here
                if (currentReturnVal == 0) {
                    currentMethodNum++;
                }

                if (currentMethodNum < 1) {
                    currentReturnVal = TT_Util.driveForward(baseDrive, baseShifter, lEncoder, rEncoder, RIGHT_GEAR_FORWARD);
                } else if (currentMethodNum < 2) {
                    currentReturnVal = TT_Util.rotateBot(RIGHT_GEAR_ROTATION, baseDrive, gyro);
                } else if (currentReturnVal < 3) {
                    currentReturnVal = TT_Util.driveForward(baseDrive, baseShifter, lEncoder, rEncoder, RIGHT_GEAR_DISTANCE);
                } else if (currentReturnVal < 4) {
                    currentReturnVal = TT_Util.driveBackward(baseDrive, baseShifter, lEncoder, rEncoder, RIGHT_GEAR_BACKUP);
                    pivotMotor.set(-0.2);
                    claw.set(DoubleSolenoid.Value.kReverse);
                } else if (currentReturnVal < 5) {
                    currentReturnVal = TT_Util.rotateBot(-RIGHT_GEAR_ROTATION, baseDrive, gyro);
                    claw.set(DoubleSolenoid.Value.kForward);
                    pivotMotor.set(0.0);
                } else if (currentReturnVal < 6) {
                    currentReturnVal = TT_Util.driveForward(baseDrive, baseShifter, lEncoder, rEncoder, RIGHT_GEAR_BACKUP);
                } else {
                    baseDrive.tankDrive(0, 0);
                }
                break;

            case 4:
                TT_Auto.twoGearAutoRight(Robot.controller);

            case 5:
                // 2 gear auto
                TT_Auto.twoGearAutoPeriodic(Robot.controller);
                break;
            case 6:
                // red loader station
                TT_Auto.redLoaderStationPeriodic(Robot.controller);
                break;
            case 7:
                // red boiler auto
                TT_Auto.redBoilerAutoPeriodic(Robot.controller);
                break;
            case 8:
                // blue boiler auto
                TT_Auto.blueBoilerPeriodic(Robot.controller);
                break;
            case 9:
                // blue loader station
                TT_Auto.blueLoaderStationPeriodic(Robot.controller);
                break;
            case 10:
                if (Robot.controller.getRawButton(Robot.CONTROLLER_A_BUTTON)) {
                    TT_DriveUtil.INSTANCE.trackGear();
                } else {
                    baseDrive.tankDrive(0, 0);
                }
        }
    }
}
