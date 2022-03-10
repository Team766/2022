package com.team766.frc2022.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.CANSpeedController;
import com.team766.hal.RobotProvider;

public class Shooter extends Mechanism{
	private CANSpeedController shooter;

	public Shooter() {
		shooter = RobotProvider.instance.getCANMotor("shooter");
	}

	public void startShoot(){
		shooter.set(1.0);
	}

	public void stopShoot(){
		shooter.set(0.0);
	}
}
