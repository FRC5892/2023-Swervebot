// MIT License

// Copyright (c) 2023 FRC 6328

// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:

// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package frc.robot.commands.Vision;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.*;

import java.util.function.Supplier;

public class DriveToPose extends CommandBase {
  private final Swerve s_Swerve;
  private final Supplier<Pose2d> poseSupplier;

  private boolean finish = false;

  private final ProfiledPIDController driveController = new ProfiledPIDController(
      0.0, 0.0, 0.0, new TrapezoidProfile.Constraints(0.0, 0.0), Constants.VisionConstants.loopPeriodSecs);
  private final ProfiledPIDController thetaController = new ProfiledPIDController(
      0.0, 0.0, 0.0, new TrapezoidProfile.Constraints(0.0, 0.0), Constants.VisionConstants.loopPeriodSecs);
  private double driveErrorAbs;
  private double thetaErrorAbs;

  /** Drives to the specified pose under full software control. */
  public DriveToPose(Swerve s_Swerve, Supplier<Pose2d> poseSupplier) {
    this.s_Swerve = s_Swerve;
    this.poseSupplier = poseSupplier;
    addRequirements(s_Swerve);
    thetaController.enableContinuousInput(-Math.PI, Math.PI);
  }

  @Override
  public void initialize() {
    // Reset all controllers
    var currentPose = s_Swerve.getPose();

    driveController.reset(
        currentPose.getTranslation().getDistance(poseSupplier.get().getTranslation()));
    thetaController.reset(currentPose.getRotation().getRadians());
  }

  @Override
  public void execute() {
    finish = false;

    // Get current and target pose
    var currentPose = s_Swerve.getPose();
    var targetPose = poseSupplier.get();

    // Calculate drive speed
    double currentDistance = currentPose.getTranslation().getDistance(poseSupplier.get().getTranslation());
    driveErrorAbs = currentDistance;
    double driveVelocityScalar = driveController.calculate(driveErrorAbs, 0.0);
    if (driveController.atGoal())
      driveVelocityScalar = 0.0;

    // Calculate theta speed
    double thetaVelocity = thetaController.calculate(
        currentPose.getRotation().getRadians(), targetPose.getRotation().getRadians());
    thetaErrorAbs = Math.abs(currentPose.getRotation().minus(targetPose.getRotation()).getRadians());
    if (thetaController.atGoal())
      thetaVelocity = 0.0;

    // Command speeds
    var driveVelocity = new Pose2d(
        new Translation2d(),
        currentPose.getTranslation().minus(targetPose.getTranslation()).getAngle())
        .transformBy(VisionSubsystem.translationToTransform(driveVelocityScalar, 0.0))
        .getTranslation();
    s_Swerve.runVelocity(
        ChassisSpeeds.fromFieldRelativeSpeeds(
            driveVelocity.getX(), driveVelocity.getY(), thetaVelocity, currentPose.getRotation()));
  }

  @Override
  public void end(boolean interrupted) {
    finish = false;
    s_Swerve.stop();
  }

  /** Checks if the robot is stopped at the final pose. */
  public boolean atGoal() {
    return finish && driveController.atGoal() && thetaController.atGoal();
  }

  /**
   * Checks if the robot pose is within the allowed drive and theta tolerances.
   */
  public boolean withinTolerance(double driveTolerance, Rotation2d thetaTolerance) {
    return finish
        && Math.abs(driveErrorAbs) < driveTolerance
        && Math.abs(thetaErrorAbs) < thetaTolerance.getRadians();
  }

  /** Returns whether the command is actively running. */
  public boolean isRunning() {
    return finish;
  }
}