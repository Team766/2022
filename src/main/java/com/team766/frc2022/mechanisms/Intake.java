package com.team766.frc2022.mechanisms;

import com.team766.config.ConfigFileReader;
import com.team766.framework.Mechanism;
import com.team766.hal.SolenoidController;
import com.team766.hal.CANSpeedController;
import com.team766.hal.RobotProvider;
import com.team766.hal.wpilib.DigitalInput;

public class Intake extends Mechanism {
	private SolenoidController m_intakeArm1;
	private SolenoidController m_intakeArm2;
	private CANSpeedController m_frontIntakeWheel;
	private CANSpeedController m_middleIntakeWheel;
	private DigitalInput m_sensor;
	
	public Intake() {
		m_frontIntakeWheel = RobotProvider.instance.getCANMotor("Intake.frontWheel");
		m_middleIntakeWheel = RobotProvider.instance.getCANMotor("Intake.topWheel");
		m_intakeArm1 = RobotProvider.instance.getSolenoid("Intake.intakeArm1");
		m_intakeArm2 = RobotProvider.instance.getSolenoid("Intake.intakeArm2");
		m_sensor = new DigitalInput(ConfigFileReader.getInstance().getInt("intake.channel").valueOr(0));
	}

	public boolean getSensor(){
		return m_sensor.get();
	}
	
	public void startIntake() {
		checkContextOwnership();
		double power = ConfigFileReader.getInstance().getDouble("Intake.intakePower").get();
		
		startArms();
		m_frontIntakeWheel.set(power);
		m_middleIntakeWheel.set(power);
	}

	public void stopIntake() {
		checkContextOwnership();
		
		m_middleIntakeWheel.set(0.0);
		m_frontIntakeWheel.set(0.0);
		stopArms();
	}

	public void startArms(){
		checkContextOwnership();

		m_intakeArm1.set(true);
		m_intakeArm2.set(false);
	}

	public void stopArms(){
		checkContextOwnership();

		m_intakeArm1.set(false);
		m_intakeArm2.set(true);
	}

	public void reverseIntake(){
		checkContextOwnership();

		m_frontIntakeWheel.set(-1.0);
		m_middleIntakeWheel.set(-1.0);

		startArms();
	}
}
