package com.team766.frc2022.mechanisms;

import com.team766.config.ConfigFileReader;
import com.team766.framework.Mechanism;
import com.team766.hal.CANSpeedController;
import com.team766.hal.RobotProvider;
import com.team766.hal.CANSpeedController.ControlMode;
import com.team766.logging.Category;

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
		double power = ConfigFileReader.getInstance().getDouble("shooter.velocity").get();
		setVelocity(power);
	}

	public void stopShoot(){
		shooter.set(0.0);
	}

	public void setPIDValues(){
		shooter.setP(ConfigFileReader.getInstance().getDouble("shooter.p").get());
		shooter.setI(ConfigFileReader.getInstance().getDouble("shooter.i").get());
		shooter.setD(ConfigFileReader.getInstance().getDouble("shooter.d").get());
		shooter.setFF(ConfigFileReader.getInstance().getDouble("shooter.ff").get()); //overcome friction
	}

	public double getVelocity(){
		return shooter.getSensorVelocity();
	}
}
