package com.team766.frc2020.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.EncoderReader;
import com.team766.hal.RobotProvider;
import com.team766.hal.SpeedController;
import com.team766.logging.Category;
import com.team766.hal.GyroReader;

public class Drive extends Mechanism {
	private SpeedController m_leftMotor;
	private SpeedController m_rightMotor;
	private EncoderReader m_leftEncoder;
	private EncoderReader m_rightEncoder;
	private GyroReader m_gyro;

	public Drive() {
		loggerCategory = Category.DRIVE;
		m_leftMotor = RobotProvider.instance.getMotor("drive.leftMotor");
		m_rightMotor = RobotProvider.instance.getMotor("drive.rightMotor");
		m_leftEncoder = RobotProvider.instance.getEncoder("drive.leftEncoder");
		m_rightEncoder = RobotProvider.instance.getEncoder("drive.rightEncoder");
		m_gyro = RobotProvider.instance.getGyro("drive.gyro");
	}

	public double getEncoderDistance() {
		double leftValue = m_leftEncoder.getDistance();
		double rightValue = m_rightEncoder.getDistance();
		log("Encoder Distance: " + Double.toString(0.5 * (leftValue + rightValue)));
		return 0.5 * (leftValue + rightValue);
	}

	public void resetEncoders() {
		checkContextOwnership();

		m_leftEncoder.reset();
		m_rightEncoder.reset();
	}

	public void resetGyro() {
		checkContextOwnership();

		m_gyro.reset();
	}

	public double getGyroAngle() {
		double angle = m_gyro.getAngle();
		log ("Gyro: " + angle);
		return angle;
	}

	public void setDrivePower(double leftPower, double rightPower) {
		checkContextOwnership();

		m_leftMotor.set(leftPower);
		m_rightMotor.set(rightPower);
	}

	public void setArcadeDrivePower(double forward, double turn) {
		setDrivePower(turn + forward, -turn + forward);
	}
}