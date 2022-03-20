package com.team766.frc2022.mechanisms;

import edu.wpi.first.wpilibj.I2C.Port;

import com.kauailabs.navx.frc.*;
import com.team766.framework.Mechanism;
import com.team766.hal.EncoderReader;
import com.team766.hal.RobotProvider;
import com.team766.hal.SpeedController;
import com.team766.hal.CANSpeedController.ControlMode;
import com.team766.hal.CANSpeedController;
import com.team766.library.ValueProvider;
import com.team766.logging.Category;
import com.team766.hal.GyroReader;
import com.team766.config.ConfigFileReader;

public class Drive extends Mechanism {

    // Declaration of Mechanisms
    private CANSpeedController m_leftTalon1;
    private CANSpeedController m_leftTalon2;
	private CANSpeedController m_leftTalon3;
    private CANSpeedController m_rightTalon1;
    private CANSpeedController m_rightTalon2;
    private CANSpeedController m_rightTalon3;
    private ValueProvider<Double> drivePower;

    //	private GyroReader m_gyro;
	private GyroReader m_gyro;

	// Values for PID Driving Straight
	public double P_drive = ConfigFileReader.getInstance().getDouble("drive.drive.P").valueOr(0.0);
	public double I_drive = ConfigFileReader.getInstance().getDouble("drive.drive.I").valueOr(0.0);
	public double D_drive = ConfigFileReader.getInstance().getDouble("drive.drive.D").valueOr(0.0);
	public double FF_drive = ConfigFileReader.getInstance().getDouble("drive.drive.FF").valueOr(0.0);
	public double threshold_drive = ConfigFileReader.getInstance().getDouble("drive.drive.threshold").valueOr(0.0);
	public double minpower_drive = ConfigFileReader.getInstance().getDouble("drive.drive.minpower").valueOr(0.0);
	public double min_drive = -12;
	public double max_drive = 12;

	// Values for PID turning
	public double P_turn = ConfigFileReader.getInstance().getDouble("drive.turn.P").valueOr(0.0);
	public double I_turn = ConfigFileReader.getInstance().getDouble("drive.turn.I").valueOr(0.0);
	public double D_turn = ConfigFileReader.getInstance().getDouble("drive.turn.D").valueOr(0.0);
	public double threshold_turn = ConfigFileReader.getInstance().getDouble("drive.turn.thereshold").valueOr(0.0);
	public double minpower_turn = ConfigFileReader.getInstance().getDouble("drive.turn.minpower").valueOr(0.0);
	public double min_turn = -12;
	public double max_turn = 12;

	//Encoder Value (CHANGE THESE VALUES LATER)
	public double ppr = 256; //pulses per revolution
	public double radius = 0.075; //radius of the wheel in m

    public Drive() {
		loggerCategory = Category.DRIVE;
        // Initializations
        m_leftTalon1 = RobotProvider.instance.getCANMotor("drive.leftTalon1"); 
		m_leftTalon2 = RobotProvider.instance.getCANMotor("drive.leftTalon2"); 
		m_leftTalon3 = RobotProvider.instance.getCANMotor("drive.leftTalon3"); 
		m_rightTalon1 = RobotProvider.instance.getCANMotor("drive.rightTalon1"); 
		m_rightTalon2 = RobotProvider.instance.getCANMotor("drive.rightTalon2"); 
		m_rightTalon3 = RobotProvider.instance.getCANMotor("drive.rightTalon3"); 

		m_rightTalon1.setInverted(true);
        m_rightTalon2.setInverted(true);
		m_rightTalon3.setInverted(true);
        //m_rightVictor1.follow(m_rightTalon);
        //m_rightVictor2.follow(m_rightTalon);
        //m_leftVictor1.follow(m_leftTalon);
        //m_leftVictor2.follow(m_leftTalon);
    }

    public double getEncoderDistance() {
        // TODO: check if this should be reading from a Talon or from a Victor
		double leftValue = m_leftTalon1.getSensorPosition();
		double rightValue = m_rightTalon1.getSensorPosition();
		log("Encoder Distance: " + Double.toString(0.5 * (leftValue + rightValue)));
		double rev = 0.5 * (leftValue + rightValue)/ppr;
		double distance = rev*2*Math.PI*radius;
		return distance;
	}

	public void resetEncoders() {
		checkContextOwnership();

		m_leftTalon1.setPosition(0);;
		m_rightTalon1.setPosition(0);
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

        m_leftTalon1.set(leftPower);
        m_leftTalon2.set(leftPower);
        m_leftTalon3.set(leftPower);
        m_rightTalon1.set(rightPower);
        m_rightTalon2.set(rightPower);
        m_rightTalon3.set(rightPower);
    }

	public void setDrivePower(double leftPower, double rightPower, boolean voltage) {
		if (voltage){
			checkContextOwnership();
			m_leftTalon1.set(ControlMode.Voltage,leftPower);
			m_leftTalon2.set(ControlMode.Voltage,leftPower);
			m_leftTalon3.set(ControlMode.Voltage,leftPower);
			m_rightTalon1.set(ControlMode.Voltage,rightPower);
			m_rightTalon2.set(ControlMode.Voltage,rightPower);
			m_rightTalon3.set(ControlMode.Voltage,rightPower);
		}
	}

	public void setArcadeDrivePower(double forward, double turn) {
		setDrivePower(turn + forward, -turn + forward);
	}

	public void setArcadeDrivePower(double forward, double turn, boolean voltage) {
		if (voltage){
			setDrivePower(turn + forward, -turn + forward,true);
		}
	}
}
