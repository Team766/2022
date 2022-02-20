package com.team766.frc2022.mechanisms;

import com.team766.config.ConfigFileReader;
import com.team766.framework.Mechanism;
import com.team766.hal.RobotProvider;
import com.team766.hal.CANSpeedController;
import com.team766.hal.SolenoidController;
import com.team766.library.ValueProvider;
import com.team766.logging.Category;

public class Elevator extends Mechanism {
    // Declaration of Mechanisms
	private CANSpeedController m_elevator;
    private SolenoidController m_arms;
    private ValueProvider<Integer> elevatorTop;
    private ValueProvider<Integer> elevatorBottom;
    private ValueProvider<Integer> elevatorLeniency;
    private ValueProvider<Double> elevatorPower;
    private ValueProvider<Integer> slowMode;

    public Elevator() {
        // Initializations
		m_elevator = RobotProvider.instance.getTalonCANMotor("climber.elevator");
        m_arms = RobotProvider.instance.getSolenoid("climber.arms");
        elevatorTop = ConfigFileReader.getInstance().getInt("climber.elevatorTop"); //670
        elevatorBottom = ConfigFileReader.getInstance().getInt("climber.elevatorBottom"); //0
        elevatorLeniency = ConfigFileReader.getInstance().getInt("climber.elevatorLeniency"); //5
        elevatorPower = ConfigFileReader.getInstance().getDouble("climber.elevatorPower"); //1
        slowMode = ConfigFileReader.getInstance().getInt("climber.slowMode"); //0
    }

    
    public void setElevatorPower(double power) {
        loggerCategory = Category.DRIVE;
        power *= elevatorPower.get();
        checkContextOwnership();
        if (power < 0 && getElevatorPosition() - getElevatorBottom() >= getElevatorLeniency()) {
            m_elevator.set(power);
        } else if (power > 0 && getElevatorTop() - getElevatorPosition() >= getElevatorLeniency()) {
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

    public boolean getArmsPower() {
        return m_arms.get();
    }

    public int getElevatorBottom() {
        return elevatorBottom.get();
    }

    public int getElevatorTop() {
        return elevatorTop.get();
    }

    public int getElevatorLeniency() {
        return elevatorLeniency.get();
    }

    public int getSlowMode() {
        return slowMode.get();
    }
}
