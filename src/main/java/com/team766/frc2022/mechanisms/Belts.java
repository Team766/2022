package com.team766.frc2022.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.CANSpeedController;
import com.team766.hal.RobotProvider;

public class Belts extends Mechanism {
	private CANSpeedController m_leftStorageBelt;
	private CANSpeedController m_rightStorageBelt;
	
	public Belts() {
		m_leftStorageBelt = RobotProvider.instance.getCANMotor("belt.left");
		m_rightStorageBelt = RobotProvider.instance.getCANMotor("belt.right");
	}

	public void startBelts() {
		checkContextOwnership();

		m_leftStorageBelt.set(1.0);
		m_rightStorageBelt.set(1.0);
	}

	public void stopBelts() {
		checkContextOwnership();

		m_leftStorageBelt.set(0.0);
		m_rightStorageBelt.set(0.0);
	}
}
