package com.team766.frc2022.mechanisms;

import com.team766.config.ConfigFileReader;
import com.team766.framework.Mechanism;
import com.team766.hal.CANSpeedController;
import com.team766.hal.RobotProvider;
import com.team766.hal.CANSpeedController.ControlMode;
import com.team766.logging.Category;
import com.team766.frc2022.Robot;

public class Shooter extends Mechanism{
	private CANSpeedController shooter;

	public Shooter() {
		shooter = RobotProvider.instance.getCANMotor("shooter");
		loggerCategory = Category.PROCEDURES;
	}

	public void setVelocity(double power){
		checkContextOwnership();
		log("Power is: "+power);
		shooter.set(ControlMode.Velocity, power);
	}

	public void basicShoot(){
		checkContextOwnership();
		double power = ConfigFileReader.getInstance().getDouble("shooter.velocity").valueOr(1.0);
		setVelocity(power);
	}
	

	public void stopShoot(){
		checkContextOwnership();
		shooter.set(0.0);
	}

	public void setPIDValues(){
		shooter.setP(ConfigFileReader.getInstance().getDouble("shooter.p").valueOr(0.0));
		shooter.setI(ConfigFileReader.getInstance().getDouble("shooter.i").valueOr(0.0));
		shooter.setD(ConfigFileReader.getInstance().getDouble("shooter.d").valueOr(0.0));
		shooter.setFF(ConfigFileReader.getInstance().getDouble("shooter.ff").valueOr(0.0)); //overcome friction
	}

	public double getVelocity(){
		return shooter.getSensorVelocity();
	}
}
