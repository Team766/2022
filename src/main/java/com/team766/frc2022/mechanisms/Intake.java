package com.team766.frc2022.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.SolenoidController;
import com.team766.hal.CANSpeedController;
import com.team766.hal.RobotProvider;

public class Intake extends Mechanism {
	private SolenoidController m_intakeArm;
	private CANSpeedController m_frontIntakeWheel;
	private CANSpeedController m_middleIntakeWheel;
	
	public Intake() {
		m_frontIntakeWheel = RobotProvider.instance.getTalonCANMotor("Intake.upperWheel");
		m_middleIntakeWheel = RobotProvider.instance.getTalonCANMotor("Intake.upperWheel");
		m_intakeArm = RobotProvider.instance.getSolenoid("Intake.intakeArm");
	}

	public void startIntake() {
		checkContextOwnership();

		m_intakeArm.set(true);
		m_frontIntakeWheel.set(1.0);
		m_middleIntakeWheel.set(1.0);
	}

	public void stopIntake() {
		checkContextOwnership();

		m_middleIntakeWheel.set(0.0);
		m_frontIntakeWheel.set(0.0);
		m_intakeArm.set(false);
	}
}
