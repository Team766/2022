package com.team766.frc2022.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.EncoderReader;
import com.team766.hal.RobotProvider;
import com.team766.hal.SpeedController;
import com.team766.hal.CANSpeedController;
import com.team766.logging.Category;
import com.team766.hal.GyroReader;

public class Drive extends Mechanism {
	private CANSpeedController m_leftMotor1;
    private CANSpeedController m_leftMotor2;
    private CANSpeedController m_rightMotor1;
	private CANSpeedController m_rightMotor2;
	private GyroReader m_gyro;

	public Drive() {
		loggerCategory = Category.DRIVE;
		m_leftMotor1 = RobotProvider.instance.getTalonCANMotor("drive.leftMotor1");
		m_rightMotor1 = RobotProvider.instance.getTalonCANMotor("drive.rightMotor1");
        m_leftMotor2 = RobotProvider.instance.getVictorCANMotor("drive.leftMotor2");
		m_rightMotor2 = RobotProvider.instance.getVictorCANMotor("drive.rightMotor2");
		m_gyro = RobotProvider.instance.getGyro("drive.gyro");
		m_rightMotor1.setInverted(true);
		m_rightMotor2.setInverted(true);
	}

	public double getEncoderDistance() {
		double leftValue = m_leftMotor1.getSensorPosition();
		double rightValue = m_rightMotor1.getSensorPosition();
		log("Encoder Distance: " + Double.toString(0.5 * (leftValue + rightValue)));
		return 0.5 * (leftValue + rightValue);
	}

	public void resetEncoders() {
		checkContextOwnership();

		m_leftMotor1.setPosition(0);;
		m_rightMotor1.setPosition(0);
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

		m_leftMotor1.set(leftPower);
		m_rightMotor1.set(rightPower);
        m_leftMotor2.set(leftPower);
		m_rightMotor2.set(rightPower);
	}

	public void setArcadeDrivePower(double forward, double turn) {
		setDrivePower(turn + forward, -turn + forward);
	}
}