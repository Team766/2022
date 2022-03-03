package com.team766.frc2022.mechanisms;

import com.team766.hal.CANSpeedController;
import com.team766.hal.RobotProvider;

public class Shooter {
	private CANSpeedController shooter;

	public Shooter() {
		shooter = RobotProvider.instance.getVictorCANMotor("shooter");
	}

	public void startShoot(){
		shooter.set(1.0);
	}

	public void stopShoot(){
		shooter.set(0.0);
	}
}
