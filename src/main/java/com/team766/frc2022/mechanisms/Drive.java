package com.team766.frc2022.mechanisms;
import com.kauailabs.navx.frc.*;
import com.team766.framework.Mechanism;
import com.team766.hal.CANSpeedController;
import com.team766.hal.EncoderReader;
import com.team766.hal.RobotProvider;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.I2C.Port;

public class Drive extends Mechanism {
    // Some other test change
    private CANSpeedController m_leftVictor1;
    private CANSpeedController m_leftVictor2;
    private CANSpeedController m_rightVictor1;
    private CANSpeedController m_rightVictor2;
    private  CANSpeedController m_leftTalon;
    private  CANSpeedController m_rightTalon;
    private EncoderReader m_leftEncoder;
    private EncoderReader m_rightEncoder;
    private AHRS m_gyro;
    private final DifferentialDriveOdometry m_odometry;
    private double offSetR = 0.0; // pulses
    private double offSetL = 0.0; // pulses
    private int counter = 0;
    private double distancePerPulse = -0.001870229376/4.0 ; // in meters
    public Drive() {
        // Initialize victors
        m_leftVictor1 = RobotProvider.instance.getVictorCANMotor("drive.leftVictor1"); 

        m_rightVictor1 = RobotProvider.instance.getVictorCANMotor("drive.rightVictor1");
        
        m_leftVictor2 = RobotProvider.instance.getVictorCANMotor("drive.leftVictor2"); 
        
        m_rightVictor2 = RobotProvider.instance.getVictorCANMotor("drive.rightVictor2");

        // Initialize second victors if they exist
        // if (ConfigFileReader.getInstance().getInt("drive.leftVictor2").get() >= 0) {
        //     m_secondVictor = true;
        //     m_leftVictor2 = RobotProvider.instance.getVictorCANMotor("drive.leftVictor2");
        //     m_rightVictor2 = RobotProvider.instance.getVictorCANMotor("drive.rightVictor2");    
        // } else {
        //    m_secondVictor = false;
        // }

        // Initializes talons
        m_leftTalon = RobotProvider.instance.getTalonCANMotor("drive.leftTalon");
        m_rightTalon = RobotProvider.instance.getTalonCANMotor("drive.rightTalon");
        // Initialize encoders
        m_rightEncoder = RobotProvider.instance.getEncoder("drive.rightEncoder");
        m_leftEncoder = RobotProvider.instance.getEncoder("drive.leftEncoder");
        
        // Initialize gyro
        // m_gyro = RobotProvider.instance.getGyro("drive.gyro");
        // DON'T WORRY ABOUT THE ERRORS, the robots should still be using the external gyro fine
        m_gyro = new AHRS(Port.kOnboard);
 
       resetEncoders();                     
       m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());
       //m_gyroDirection = ConfigFileReader.getInstance().getDouble("drive.gyroDirection").get();
    }
    public double getGyroAngle() {
        return((m_gyro.getAngle() % 360) );
    }
    @Override
    public void run(){
        m_odometry.update(
            m_gyro.getRotation2d(), getLeftDistance(), getRightDistance());
    if(counter%100 == 1){
      //System.out.println(getPose()); 
      log("leftDis %f  rightDis %f", getLeftDistance(), getRightDistance());
      log("leftSpe %f  rightSpe %f", 10*distancePerPulse*m_leftTalon.getSensorVelocity(), -10*distancePerPulse*m_rightTalon.getSensorVelocity());       
       
    }
    counter++;
  }
    public void setDrivePower(double leftPower, double rightPower){
      checkContextOwnership();
     // m_rightTalon.set(rightPower);
     // m_leftTalon.set(leftPower);
     // m_leftVictor1.set(leftPower);
     // m_leftVictor2.set(leftPower);
     // m_rightVictor1.set(rightPower);
     // m_rightVictor2.set(rightPower);
    }
    //Inverted left encoder
    public double getLeftDistance(){
      return -1*(m_leftTalon.getSensorPosition() - offSetL)*distancePerPulse;
    }
    public double getRightDistance(){
      return(m_rightTalon.getSensorPosition() - offSetR)*distancePerPulse;
    }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(10*distancePerPulse*m_leftTalon.getSensorVelocity(), -10*distancePerPulse*m_rightTalon.getSensorVelocity());
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    zeroHeading();
    resetEncoders();
    m_odometry.resetPosition(pose, m_gyro.getRotation2d());
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    double leftMotorPower = rot + fwd;
		double rightMotorPower = -rot + fwd;
		setDrivePower(leftMotorPower, rightMotorPower);
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    checkContextOwnership();
    if(leftVolts <= 0 ){leftVolts = Math.max(leftVolts, -6.0);} else {leftVolts = Math.min(leftVolts, 6.0);}
    if(rightVolts <= 0 ){rightVolts = Math.max(rightVolts, -6.0);} else {rightVolts = Math.min(rightVolts, 6.0);}
    System.out.println("Left voltage: " + leftVolts + " | right voltage: " + rightVolts);
    m_rightTalon.setVoltage(rightVolts);
    m_leftTalon.setVoltage(leftVolts);
    m_rightVictor1.setVoltage(rightVolts);
    m_leftVictor1.setVoltage(leftVolts);
    m_rightVictor2.setVoltage(rightVolts);
    m_leftVictor2.setVoltage(leftVolts);
  }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    checkContextOwnership();
    offSetL += m_leftTalon.getSensorPosition();
    offSetR += m_rightTalon.getSensorPosition();
    
    //m_leftEncoder.reset();
    //m_rightEncoder.reset();
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (getLeftDistance()) + getRightDistance() / 2.0;
  }
  
  /**
   * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }*/

  /** Zeroes the heading of the robot. */
  public void zeroHeading() {
    m_gyro.reset();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public double getHeading() {
    return m_gyro.getRotation2d().getDegrees();
  }

  /*
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return -m_gyro.getRate();
  }
}




