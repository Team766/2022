package com.team766.frc2022.mechanisms;

import com.team766.framework.Mechanism;

public class Drive extends Mechanism {
<<<<<<< Updated upstream
    // Some other test change
=======

    // Declaration of Mechanisms
    private CANSpeedController m_DriveFrontRight;
    private CANSpeedController m_DriveFrontLeft;
	private CANSpeedController m_DriveBackRight;
	private CANSpeedController m_DriveBackLeft;
    private CANSpeedController m_SteerFrontRight;
    private CANSpeedController m_SteerFrontLeft;
	private CANSpeedController m_SteerBackRight;
	private CANSpeedController m_SteerBackLeft;
    private ValueProvider<Double> drivePower;

    //	private GyroReader m_gyro;
	private GyroReader m_gyro;

	// Values for PID Driving Straight
	public double min_drive = -12;
	public double max_drive = 12;

	// Values for PID turning
	public double min_turn = -12;
	public double max_turn = 12;

    public Drive() {
		loggerCategory = Category.DRIVE;
        // Initializations
        m_DriveFrontRight = RobotProvider.instance.getCANMotor("drive.DriveFrontRight"); 
		m_DriveFrontLeft = RobotProvider.instance.getCANMotor("drive.DriveFrontLeft"); 
		m_DriveBackRight = RobotProvider.instance.getCANMotor("drive.DriveBackRight"); 
		m_DriveBackLeft = RobotProvider.instance.getCANMotor("drive.DriveBackLeft"); 
		m_SteerFrontRight = RobotProvider.instance.getCANMotor("drive.SteerFrontRight"); 
		m_SteerFrontLeft = RobotProvider.instance.getCANMotor("drive.SteerFrontLeft"); 
		m_SteerBackRight = RobotProvider.instance.getCANMotor("drive.SteerBackRight"); 
		m_SteerBackLeft = RobotProvider.instance.getCANMotor("drive.SteerBackLeft"); 

		m_DriveFrontRight.setCurrentLimit(20);
		m_DriveFrontLeft.setCurrentLimit(20);
		m_DriveBackRight.setCurrentLimit(20);
		m_DriveBackLeft.setCurrentLimit(20);
		m_SteerFrontRight.setCurrentLimit(20);
		m_SteerFrontLeft.setCurrentLimit(20);
		m_SteerBackRight.setCurrentLimit(20);
		m_SteerBackLeft.setCurrentLimit(20);
    }

	public void resetGyro() {
		checkContextOwnership();
		m_gyro.reset();
	}

	public double getGyroAngle() {
        return((m_gyro.getAngle() % 360) );
    }

    public void setDrivePower(double leftPower, double rightPower) {
		checkContextOwnership();

        m_DriveFrontRight.set(rightPower);
        m_DriveFrontLeft.set(leftPower);
        m_DriveBackRight.set(rightPower);
        m_DriveBackLeft.set(leftPower);
    }


	public void setArcadeDrivePower(double forward, double turn) {
		setDrivePower(turn + forward, -turn + forward);
	}
>>>>>>> Stashed changes
}
