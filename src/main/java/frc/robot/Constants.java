// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.sensors.Pigeon2;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import frc.lib.config.SwerveModuleConstants;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
      public static final class Swerve {
            public Swerve(Pigeon2 gyro) {
            }

            public static final double stickDeadband = 0.1;
            public static final int pigeonID = 13;
            public static final boolean invertGyro = false; // Always ensure Gyro is CCW+ CW-

            /* Drivetrain Constants */
            public static final double trackWidth = Units.inchesToMeters(17);
            public static final double wheelBase = Units.inchesToMeters(15);
            public static final double wheelDiameter = Units.inchesToMeters(4.0);
            public static final double wheelCircumference = wheelDiameter * Math.PI;

            public static final double openLoopRamp = 2;
            public static final double closedLoopRamp = 0.0; // seem to be useless

            public static final double driveGearRatio = (18 / 45); // 6.75:1 for L2

            public static final double angleGearRatio = (28.1 / 1); // 12.8:1 for all L's

            public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
                        new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
                        new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
                        new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
                        new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0));

            /* Swerve Compensation */
            public static final double voltageComp = 12.0;

            /* Swerve Current Limiting */
            public static final int angleContinuousCurrentLimit = 20;
            public static final int driveContinuousCurrentLimit = 50;

            /* TODO: test Angle Motor PID Values (these are default, may tune if needed) */
            public static final double angleKP = 0.02;
            public static final double angleKI = 0.0;
            public static final double angleKD = 0.0;
            public static final double angleKFF = 0.0;

            /* Drive Motor PID Values */
            public static final double driveKP = 0.0; // TODO: Tune after charactarization
            public static final double driveKI = 0.0; // leave
            public static final double driveKD = 0.0; // leave
            public static final double driveKFF = 0.0; // leave

            /* TODO: Charactarize drivetrain Drive Motor Characterization Values */
            public static final double driveKS = 0.11979;
            public static final double driveKV = 2.3823;
            public static final double driveKA = 0.30034;

            /* Drive Motor Conversion Factors */
            public static final double driveConversionVelocityFactor = ((wheelDiameter * Math.PI) / driveGearRatio)
                        / 60.0;
            public static final double angleConversionFactor = 360.0 / angleGearRatio;

            /* TODO: set Swerve Profiling Values */
            // private static final double SPEED_MULTIPLIER = 0.2;
            public static final double maxSpeed = 5.5; // meters per second
            public static final double maxAngularVelocity = 5; // TODO: Tune

            /* Neutral Modes */
            public static final IdleMode angleNeutralMode = IdleMode.kBrake;
            public static final IdleMode driveNeutralMode = IdleMode.kBrake;

            public static final boolean driveInvert = false;
            public static final boolean angleInvert = false;

            public static final boolean canCoderInvert = false;

            /* Module Specific Constants */
            /* Front Left Module - Module 0 */
            public static final class Mod0 {
                  public static final int driveMotorID = 1;
                  public static final int angleMotorID = 2;
                  public static final int canCoderID = 12;
                  public static final double offsetDegree = 0; // done
                  public static final Rotation2d angleOffset = Rotation2d.fromDegrees(offsetDegree);
                  public static final Rotation2d balanceOffset = Rotation2d.fromDegrees(offsetDegree + 45);
                  public static final double conversionFactor = 0.060509807;

                  public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID,
                              angleMotorID,
                              canCoderID, angleOffset);
            }

            /* Front Right Module - Module 1 */
            public static final class Mod1 {
                  public static final int driveMotorID = 7;
                  public static final int angleMotorID = 8;
                  public static final int canCoderID = 11;
                  public static final double offsetDegree = 0; // not done
                  public static final Rotation2d angleOffset = Rotation2d.fromDegrees(offsetDegree);
                  public static final Rotation2d balanceOffset = Rotation2d.fromDegrees(offsetDegree - 45);
                  public static final double conversionFactor = 0.060509807;

                  public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID,
                              angleMotorID,
                              canCoderID, angleOffset);
            }

            /* Back Left Module - Module 2 */
            public static final class Mod2 {
                  public static final int driveMotorID = 3;
                  public static final int angleMotorID = 4;
                  public static final int canCoderID = 10;
                  public static final double offsetDegree = 0; // done
                  public static final Rotation2d angleOffset = Rotation2d.fromDegrees(offsetDegree);
                  public static final Rotation2d balanceOffset = Rotation2d.fromDegrees(offsetDegree + 45);
                  public static final double conversionFactor = 0.060509807;

                  public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID,
                              angleMotorID,
                              canCoderID, angleOffset);
            }

            /* Back Right Module - Module 3 */
            public static final class Mod3 {
                  public static final int driveMotorID = 5;
                  public static final int angleMotorID = 6;
                  public static final int canCoderID = 9;
                  public static final double offsetDegree = 0; // done
                  public static final Rotation2d angleOffset = Rotation2d.fromDegrees(offsetDegree);
                  public static final Rotation2d balanceOffset = Rotation2d.fromDegrees(offsetDegree - 45);
                  public static final double conversionFactor = 0.060509807;

                  public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID,
                              angleMotorID,
                              canCoderID, angleOffset);
            }
      }
}
