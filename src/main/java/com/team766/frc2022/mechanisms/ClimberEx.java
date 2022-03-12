package com.team766.frc2022.mechanisms;

import com.ctre.phoenix.motorcontrol.MotorCommutation;
import com.ctre.phoenix.motorcontrol.can.MotControllerJNI;
import com.team766.framework.Procedure;
import com.team766.hal.RobotProvider;
import com.team766.hal.SolenoidController;
import com.team766.hal.SpeedController;
import com.team766.hal.DoubleSolenoid.State;
import com.team766.logging.Category;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
//import sun.tools.tree.CheckContext;
import com.team766.framework.Mechanism;
import com.team766.hal.CANSpeedController;
import com.team766.hal.DoubleSolenoid;
import com.team766.hal.EncoderReader;
import com.team766.hal.GyroReader;

public class ClimberEx extends Mechanism{
	private DoubleSolenoid m_armsPusher;

	public ClimberEx(){
		loggerCategory = Category.CLIMBER;
		//m_armsPusher = RobotProvider.instance.getSolenoid("climber.arms");
		m_armsPusher = new DoubleSolenoid(RobotProvider.instance.getSolenoid("climber.armsFront"), RobotProvider.instance.getSolenoid("climber.armsBack"));
	}

	public void setArmsPusher(boolean position){
		checkContextOwnership();
		m_armsPusher.set(position?State.Forward:State.Backward);
	}
}