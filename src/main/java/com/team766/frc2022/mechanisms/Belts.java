package com.team766.frc2022.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.CANSpeedController;
import com.team766.hal.RobotProvider;

public class Belts extends Mechanism {
	private CANSpeedController m_storageBelts;
	
	public Belts() {
		m_storageBelts = RobotProvider.instance.getTalonCANMotor("Belts");
	}

	public void startBelts() {
		checkContextOwnership();

		m_storageBelts.set(1.0);
	}

	public void stopBelts() {
		checkContextOwnership();

		m_storageBelts.set(0.0);
	}
}
