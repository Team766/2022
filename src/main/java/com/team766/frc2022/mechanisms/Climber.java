package com.team766.frc2022.mechanisms;

import com.ctre.phoenix.motorcontrol.MotorCommutation;
import com.ctre.phoenix.motorcontrol.can.MotControllerJNI;
import com.team766.framework.Procedure;
import com.team766.hal.RobotProvider;
import com.team766.hal.SolenoidController;
import com.team766.hal.SpeedController;
import com.team766.logging.Category;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
//import sun.tools.tree.CheckContext;
import com.team766.framework.Mechanism;
import com.team766.hal.CANSpeedController;
import com.team766.hal.EncoderReader;
import com.team766.hal.GyroReader;

public class Climber extends Mechanism{
	
	private CANSpeedController m_elevatorMotor;
	private GyroReader m_gyro;
	

	public Climber(){
		loggerCategory = Category.CLIMBER;
		
		m_elevatorMotor = RobotProvider.instance.getTalonCANMotor("climber.elevator");
		m_gyro = RobotProvider.instance.getGyro("drive.gyro");
		
	}

	public void setElevatorMotor(double speed){
		checkContextOwnership();

		m_elevatorMotor.set(speed);
	}

	

	public double getGyroAngle(){
		double pitch = m_gyro.getPitch();
		log("Gyro: " + pitch);
		return pitch;
	}

	public void resetGyro(){
		checkContextOwnership();
		m_gyro.reset();
	}

	public double getEncoderDistance(){
		log("Encoder: " + m_elevatorMotor.getSensorPosition());
		return m_elevatorMotor.getSensorPosition();
	}

	
}