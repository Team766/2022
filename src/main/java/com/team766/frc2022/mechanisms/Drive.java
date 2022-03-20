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
    private CANSpeedController m_leftVictor1;
    private CANSpeedController m_leftVictor2;
    private CANSpeedController m_rightVictor1;
    private CANSpeedController m_rightVictor2;
    private CANSpeedController m_leftTalon;
    private CANSpeedController m_rightTalon;
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
        // Initializations
        m_leftVictor1 = RobotProvider.instance.getVictorCANMotor("drive.leftVictor1"); 
        m_rightVictor1 = RobotProvider.instance.getVictorCANMotor("drive.rightVictor1");
        m_leftVictor2 = RobotProvider.instance.getVictorCANMotor("drive.leftVictor2");
        m_rightVictor2 = RobotProvider.instance.getVictorCANMotor("drive.rightVictor2");
        m_leftTalon = RobotProvider.instance.getTalonCANMotor("drive.leftTalon");
        m_rightTalon = RobotProvider.instance.getTalonCANMotor("drive.rightTalon");
        drivePower = ConfigFileReader.getInstance().getDouble("drive.drivePower"); //1

        //m_rightVictor1.follow(m_rightTalon);
        //m_rightVictor2.follow(m_rightTalon);
        //m_leftVictor1.follow(m_leftTalon);
        //m_leftVictor2.follow(m_leftTalon);

        loggerCategory = Category.DRIVE;
        m_gyro = new AHRS(Port.kOnboard);
        m_leftMotor1 = RobotProvider.instance.getCANMotor("drive.leftMotor1");
        m_rightMotor1 = RobotProvider.instance.getCANMotor("drive.rightMotor1");
        m_leftMotor2 = RobotProvider.instance.getCANMotor("drive.leftMotor2");
        m_rightMotor2 = RobotProvider.instance.getCANMotor("drive.rightMotor2");
        m_rightMotor1.setInverted(true);
        m_rightMotor2.setInverted(true);
    }

    public double getEncoderDistance() {
        // TODO: check if this should be reading from a Talon or from a Victor
		double leftValue = m_leftVictor1.getSensorPosition();
		double rightValue = m_rightVictor1.getSensorPosition();
		log("Encoder Distance: " + Double.toString(0.5 * (leftValue + rightValue)));
		double rev = 0.5 * (leftValue + rightValue)/ppr;
		double distance = rev*2*Math.PI*radius;
		return distance;
	}

	public void resetEncoders() {
		checkContextOwnership();

		m_leftVictor1.setPosition(0);;
		m_rightVictor1.setPosition(0);
	}

	public void resetGyro() {
		checkContextOwnership();
		m_gyro.reset();
	}

	public double getGyroAngle() {
        return((m_gyro.getAngle() % 360) );
    }

    public void setDrivePower(double leftPower, double rightPower) {
        loggerCategory = Category.DRIVE;
		checkContextOwnership();

        leftPower *= drivePower.get();
        rightPower *= drivePower.get();
        m_leftTalon.set(leftPower);
        m_leftVictor1.set(leftPower);
        m_leftVictor2.set(leftPower);
        m_rightTalon.set(rightPower);
        m_rightVictor1.set(rightPower);
        m_rightVictor2.set(rightPower);
    }

	public void setDrivePower(double leftPower, double rightPower, boolean voltage) {
		if (voltage){
			checkContextOwnership();

			m_leftMotor1.set(ControlMode.Voltage, leftPower);
			m_rightMotor1.set(ControlMode.Voltage, rightPower);
			m_leftMotor2.set(ControlMode.Voltage, leftPower);
			m_rightMotor2.set(ControlMode.Voltage, rightPower);
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
