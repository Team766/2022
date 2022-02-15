package com.team766.frc2022.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.RobotProvider;
import com.team766.hal.SpeedController;
import com.team766.hal.SolenoidController;
import com.team766.logging.Category;

public class Elevator extends Mechanism {
    // Declaration of Mechanisms
	private SpeedController m_elevator;
	private SolenoidController m_arms;

    public Elevator() {
        // Initializations
		m_elevator = RobotProvider.instance.getTalonCANMotor("climber.elevator");
		m_arms = RobotProvider.instance.getSolenoid("climber.arms");
    }

    
    public void setElevatorPower(double power) {
        loggerCategory = Category.DRIVE;
		checkContextOwnership();

        m_elevator.set(power);
	}
	
	public void setArmsPower(double power) {
        loggerCategory = Category.DRIVE;
		checkContextOwnership();
        m_arms.set(power == 0? m_arms.get() : power > 0);
    }
}
