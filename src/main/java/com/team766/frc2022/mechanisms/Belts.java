package com.team766.frc2022.mechanisms;

import com.team766.config.ConfigFileReader;
import com.team766.framework.Mechanism;
import com.team766.hal.CANSpeedController;
import com.team766.hal.RobotProvider;

public class Belts extends Mechanism {
	private CANSpeedController m_leftStorageBelt;
	private CANSpeedController m_rightStorageBelt;
	
	public Belts() {
		m_leftStorageBelt = RobotProvider.instance.getCANMotor("belt.left");
		m_rightStorageBelt = RobotProvider.instance.getCANMotor("belt.right");
		m_rightStorageBelt.setInverted(true);
		//loggerCategory = 
	}

	public void startBelts() {
		checkContextOwnership();
		double power = ConfigFileReader.getInstance().getDouble("belt.beltPower").valueOr(1.0);
		log("setting them to config value");
		m_leftStorageBelt.set(power);
		m_rightStorageBelt.set(power);
	}

	public void stopBelts() {
		checkContextOwnership();
		log("stopping htem 0.0");
		m_leftStorageBelt.set(0.0);
		m_rightStorageBelt.set(0.0);
	}

	public void reverseBelts(){
		checkContextOwnership();

		m_leftStorageBelt.set(-1.0);
		m_rightStorageBelt.set(-1.0);
	}
}
