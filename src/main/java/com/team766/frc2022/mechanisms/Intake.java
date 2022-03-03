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
		m_frontIntakeWheel = RobotProvider.instance.getVictorCANMotor("Intake.upperWheel");
		m_middleIntakeWheel = RobotProvider.instance.getVictorCANMotor("Intake.upperWheel");
		m_intakeArm1 = RobotProvider.instance.getSolenoid("Intake.intakeArm");
		m_intakeArm2 = RobotProvider.instance.getSolenoid("Intake.intakeArm");
	}

	public void startIntake() {
		checkContextOwnership();

		m_intakeArm1.set(true);
		m_intakeArm2.set(true);
		m_frontIntakeWheel.set(1.0);
		m_middleIntakeWheel.set(1.0);
	}

	public void stopIntake() {
		checkContextOwnership();

		m_middleIntakeWheel.set(0.0);
		m_frontIntakeWheel.set(0.0);
		m_intakeArm1.set(false);
		m_intakeArm2.set(false);
	}
}
