package com.team766.frc2020.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.RobotProvider;
import com.team766.hal.SolenoidController;

public class IntakeArm extends Mechanism{
	private SolenoidController m_intakeArm;

	public IntakeArm() {
		m_intakeArm = RobotProvider.instance.getSolenoid("intakeArm");
	}
	
	public void setPusher(boolean extended) {
		checkContextOwnership();

		m_intakeArm.set(extended);
	}
}