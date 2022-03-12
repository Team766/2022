package com.team766.frc2022.mechanisms;

import edu.wpi.first.wpilibj.I2C.Port;

import com.kauailabs.navx.frc.*;
import com.team766.framework.Mechanism;
import com.team766.hal.EncoderReader;
import com.team766.hal.RobotProvider;
import com.team766.hal.SpeedController;
import com.team766.hal.CANSpeedController;
import com.team766.logging.Category;
import com.team766.hal.GyroReader;

public class Drive extends Mechanism {
    private CANSpeedController m_leftMotor1;
    private CANSpeedController m_rightMotor1;
    private CANSpeedController m_leftMotor2;
    private CANSpeedController m_rightMotor2;

    //	private GyroReader m_gyro;
	private AHRS m_gyro;

	// Values for PID Driving Straight
	public double P_drive = 1;
	public double I_drive = 1;
	public double D_drive = 1;
	public double threshold_drive = 0.1;
	public double min_drive = 0.5;
	public double max_drive = 0.1;

	// Values for PID turning
	public double P_turn = 1;
	public double I_turn = 1;
	public double D_turn = 1;
	public double threshold_turn = 0.5;
	public double min_turn = 0.1;
	public double max_turn = 0.3;

	//Encoder Value (CHANGE THESE VALUES LATER)
	public double ppr = 256; //pulses per revolution
	public double radius = 0.075; //radius of the wheel in m


    public Drive() {
        loggerCategory = Category.DRIVE;
        m_leftMotor1 = RobotProvider.instance.getCANMotor("drive.leftMotor1");
        m_rightMotor1 = RobotProvider.instance.getCANMotor("drive.rightMotor1");
        m_leftMotor2 = RobotProvider.instance.getCANMotor("drive.leftMotor2");
        m_rightMotor2 = RobotProvider.instance.getCANMotor("drive.rightMotor2");
        m_gyro = new AHRS(Port.kOnboard);
        m_rightMotor1.setInverted(true);
        m_rightMotor2.setInverted(true);
    }

    public double getEncoderDistance() {
		double leftValue = m_leftMotor1.getSensorPosition();
		double rightValue = m_rightMotor1.getSensorPosition();
		log("Encoder Distance: " + Double.toString(0.5 * (leftValue + rightValue)));
		double rev = 0.5 * (leftValue + rightValue)/ppr;
		double distance = rev*2*Math.PI*radius;
		return distance;
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
        return((m_gyro.getAngle() % 360) );
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
