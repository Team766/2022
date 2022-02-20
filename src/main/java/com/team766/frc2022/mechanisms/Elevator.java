package com.team766.frc2022.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.RobotProvider;
import com.team766.hal.CANSpeedController;
import com.team766.hal.SolenoidController;
import com.team766.logging.Category;

public class Elevator extends Mechanism {
    // Declaration of Mechanisms
	private CANSpeedController m_elevator;
    private SolenoidController m_arms;
    private int elevatorTop;
    private int elevatorBottom;
    private int elevatorLeniency;
    private int slowMode;

    public Elevator() {
        // Initializations
		m_elevator = RobotProvider.instance.getTalonCANMotor("climber.elevator");
        m_arms = RobotProvider.instance.getSolenoid("climber.arms");
        elevatorTop = 670; //WILL BE DIFFERENT ON THE ACTUAL ROBOT!!
        elevatorBottom = 0; //WILL BE DIFFERENT ON THE ACTUAL ROBOT!!
        elevatorLeniency = 5;
        slowMode = 0;
    }

    
    public void setElevatorPower(double power) {
        loggerCategory = Category.DRIVE;
        checkContextOwnership();
        if (power < 0 && getElevatorPosition() - getElevatorBottom() >= elevatorLeniency) {
            m_elevator.set(power);
        } else if (power > 0 && getElevatorTop() - getElevatorPosition() >= elevatorLeniency) {
            m_elevator.set(power);
        } else if (power != 0) {
            m_elevator.set(0);
        }
    }
    
    public double getElevatorPosition() {
        return m_elevator.getSensorPosition();
	}
    public void resetElevatorPosition() {
        m_elevator.setPosition(0);
	}
	
	public void setArmsPower(double power) {
        loggerCategory = Category.DRIVE;
		checkContextOwnership();
        m_arms.set(power == 0? m_arms.get() : power > 0);
    }

    public int getElevatorBottom() {
        return elevatorBottom;
    }

    public int getElevatorTop() {
        return elevatorTop;
    }

    public int getSlowMode() {
        return slowMode;
    }

    public void setSlowMode(int setter) {
        slowMode = setter;
    }
}
