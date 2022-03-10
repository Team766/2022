package com.team766.frc2022.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.SolenoidController;
import com.team766.hal.CANSpeedController;
import com.team766.hal.RobotProvider;

public class Intake extends Mechanism {
	private SolenoidController m_intakeArm1;
	private SolenoidController m_intakeArm2;
	private CANSpeedController m_frontIntakeWheel;
	private CANSpeedController m_middleIntakeWheel;
	
	public Intake() {
		m_frontIntakeWheel = RobotProvider.instance.getCANMotor("Intake.frontWheel");
		m_middleIntakeWheel = RobotProvider.instance.getCANMotor("Intake.topWheel");
		m_intakeArm1 = RobotProvider.instance.getSolenoid("Intake.intakeArm1");
		m_intakeArm2 = RobotProvider.instance.getSolenoid("Intake.intakeArm2");
		m_frontIntakeWheel.setInverted(true);
	}

	public void startIntake() {
		checkContextOwnership();

		m_intakeArm1.set(true);
		m_intakeArm2.set(false);
		m_frontIntakeWheel.set(1.0);
		m_middleIntakeWheel.set(1.0);
	}

	public void stopIntake() {
		checkContextOwnership();

		m_middleIntakeWheel.set(0.0);
		m_frontIntakeWheel.set(0.0);
		m_intakeArm1.set(false);
		m_intakeArm2.set(true);
	}
}
