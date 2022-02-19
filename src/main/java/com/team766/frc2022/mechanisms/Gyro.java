package com.team766.frc2022.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.EncoderReader;
import com.team766.hal.RobotProvider;
import com.team766.hal.SpeedController;
import com.team766.hal.CANSpeedController;
import com.team766.logging.Category;
import com.team766.hal.GyroReader;

public class Gyro extends Mechanism {
	private GyroReader m_gyro;

	public Gyro() {
		loggerCategory = Category.DRIVE;

		m_gyro = RobotProvider.instance.getGyro("drive.gyro");
	}

	public void resetGyro() {
		checkContextOwnership();

		m_gyro.reset();
	}

	public double getGyroPitch() {
		double angle = m_gyro.getPitch();
		return angle;
	}
}