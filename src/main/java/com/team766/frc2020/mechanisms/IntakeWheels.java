package com.team766.frc2020.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.RobotProvider;
import com.team766.hal.SpeedController;

public class IntakeWheels extends Mechanism {
	private SpeedController m_intakeWheels;

	public IntakeWheels() {
		m_intakeWheels = RobotProvider.instance.getMotor("intakeWheels");
	}

	public void setWheels(double power) {
		checkContextOwnership();
		m_intakeWheels.set(power);
	}
}