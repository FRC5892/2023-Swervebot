// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/* 
Summary:
This code is for the robot container and has a joy stick, joystick buttons, swerve subsystem, a sendable chooser for autonomous modes, autonomous modes, and methods for configuring button bindings and smart dashboard options. 
*/

public class RobotContainer {
        /* Controllers */
        public final static Joystick driver = new Joystick(0);

        // Gyro Sensor
        private static Pigeon2 gyro = new Pigeon2(Constants.Swerve.pigeonID);

        /* Drive Controls */
        private static final int translationAxis = XboxController.Axis.kLeftY.value;
        private static final int strafeAxis = XboxController.Axis.kLeftX.value;
        private static final int rotationAxis = XboxController.Axis.kRightX.value;
        private double SPEED_MULTIPLIER = 1.0;

        /* Driver Buttons */
        private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
        private final JoystickButton robotCentric = new JoystickButton(driver,
                        XboxController.Button.kRightBumper.value);

        /* Subsystems */

        /* Commands */
        private final Swerve s_Swerve = new Swerve(gyro);

        // public final static Led ledSubsystem = new Led(gyro, ground_intake);

        /* Pneumatics Commands */

        /* Autonomous Mode Chooser */

        /* Autonomous Modes */

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                CameraServer.startAutomaticCapture();
                // CameraServer.startAutomaticCapture();

                s_Swerve.setDefaultCommand(
                                new TeleopSwerve(
                                                s_Swerve,

                                                () -> -driver.getRawAxis(translationAxis) * SPEED_MULTIPLIER,
                                                () -> -driver.getRawAxis(strafeAxis) * SPEED_MULTIPLIER,
                                                () -> -driver.getRawAxis(rotationAxis) * SPEED_MULTIPLIER,
                                                () -> robotCentric.getAsBoolean()));
                SmartDashboard.putNumber("Speed Multipler", SPEED_MULTIPLIER);

                // Configure the button bindings
                configureButtonBindings();

                // Configure Smart Dashboard options
                configureSmartDashboard();
        }

        /**
         * Use this method to define your button->command mappings. Buttons can be
         * created by
         * instantiating a {@link GenericHID} or one of its subclasses ({@link
         * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
         * it to a {@link
         * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
         */
        private void configureButtonBindings() {
                /* Driver Buttons */
                zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));

        }

        private void configureSmartDashboard() {

        }

        public void disabledInit() {
                s_Swerve.resetToAbsolute();
        }

        /**
         * Use this to pass the autonomous command to the main {@link Robot} class.
         *
         * @return the command to run in autonomous
         */
        // public Command getAutonomousCommand() {
        // // Executes the autonomous command chosen in smart dashboard
        // }
}
