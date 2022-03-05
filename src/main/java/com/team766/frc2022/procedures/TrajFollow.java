package com.team766.frc2022.procedures;

import java.util.List;
import com.team766.framework.Context;
import com.team766.framework.Procedure;
import com.team766.framework.WPILibCommandProcedure;
import com.team766.frc2022.Constants;
import com.team766.frc2022.Robot;
import com.team766.simulator.ui.Trajectory;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import com.team766.frc2022.mechanisms.Drive;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

public class TrajFollow extends Procedure{
    private DifferentialDriveVoltageConstraint autoVoltageConstraint; 
    private TrajectoryConfig config;
    private edu.wpi.first.math.trajectory.Trajectory exampleTrajectory;
    public TrajFollow(){
        // Create a voltage constraint to ensure we don't accelerate too fast
        autoVoltageConstraint =
            new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(
                    Constants.ksVolts,
                    Constants.kvVoltSecondsPerMeter,
                    Constants.kaVoltSecondsSquaredPerMeter),
                    Constants.kDriveKinematics,
                10);

        // Create config for trajectory 
        config =
            new TrajectoryConfig(
                    Constants.kMaxSpeedMetersPerSecond,
                    Constants.kMaxAccelerationMetersPerSecondSquared)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(Constants.kDriveKinematics)
                // Apply the voltage constraint
                .addConstraint(autoVoltageConstraint);

        // An example trajectory to follow.  All units in meters.
        exampleTrajectory = TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(3, 0, new Rotation2d(0)),
                // Pass config
                config);
    }
    @Override
    public void run(Context context) {
        System.out.println("Before ramesete");
       context.takeOwnership(Robot.drive);
        Robot.drive.resetOdometry(exampleTrajectory.getInitialPose());
        RamseteCommand ramseteCommand =
        new RamseteCommand(
            exampleTrajectory,
            Robot.drive::getPose,
            new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta),
            new SimpleMotorFeedforward(
                Constants.ksVolts,
                Constants.kvVoltSecondsPerMeter,
                Constants.kaVoltSecondsSquaredPerMeter),
                Constants.kDriveKinematics,
                Robot.drive::getWheelSpeeds,
            new PIDController(Constants.kPDriveVel, 0, 0),
            new PIDController(Constants.kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            Robot.drive::tankDriveVolts);
            System.out.println();
            System.out.println("After ramesete");
       context.releaseOwnership(Robot.drive);
            new WPILibCommandProcedure(ramseteCommand, Robot.drive).run(context);
        
        //Robot.drive.arcadeDrive(0,0);
    }
 }
