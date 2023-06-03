package com.team766.frc2022.mechanisms;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;
import com.team766.framework.Mechanism;
import com.team766.hal.EncoderReader;
import com.team766.hal.RobotProvider;
import com.team766.hal.SpeedController;
import com.team766.library.RateLimiter;
import com.team766.hal.CANSpeedController;
import com.team766.logging.Category;
//import edu.wpi.first.wpilibj.I2C.Port;
import com.team766.hal.GyroReader;
import com.kauailabs.navx.frc.*;

public class Gyro extends Mechanism {
	private AHRS m_gyro;
	private RateLimiter m_loggingRate = new RateLimiter(0.05);

	public Gyro() {
		loggerCategory = Category.DRIVE;

		//m_gyro = new AHRS(SerialPort.Port.kUSB);
		m_gyro = new AHRS(I2C.Port.kOnboard);
	}

	public void resetGyro() {
		checkContextOwnership();

		m_gyro.reset();
	}

	public double getGyroPitch() {
		double angle = m_gyro.getPitch();
		return angle;
		
	}

	public double getGyroYaw() {
		double angle = m_gyro.getYaw();
		return angle;
	}

	public double getGyroRoll() {
		double angle = m_gyro.getRoll();
		return angle;
	}

	@Override
	public void run() {
		// if (m_loggingRate.next()) {
		// 	log("" + getGyroPitch());
		// }
	}
}