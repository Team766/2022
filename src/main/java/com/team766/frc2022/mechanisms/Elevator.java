package com.team766.frc2022.mechanisms;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.team766.config.ConfigFileReader;
import com.team766.framework.Mechanism;
import com.team766.hal.RobotProvider;
import com.team766.hal.CANSpeedController;
import com.team766.hal.DigitalInputReader;
import com.team766.hal.DoubleSolenoid;
import com.team766.library.ValueProvider;
import com.team766.logging.Category;

public class Elevator extends Mechanism {
    // Declaration of Mechanisms
	private CANSpeedController m_elevator;
    private DoubleSolenoid m_leftArm;
    private DoubleSolenoid m_rightArm;
    private DigitalInputReader m_bottom;
    private DigitalInputReader m_top;

    private ValueProvider<Integer> elevatorTop;
    private ValueProvider<Integer> elevatorBottom;
    private ValueProvider<Integer> elevatorLeniency;
    private ValueProvider<Double> elevatorPower;
    private ValueProvider<Integer> slowMode;
    

    public Elevator() {
        // Initializations
		m_elevator = RobotProvider.instance.getCANMotor("climber.elevator");
        m_elevator.setNeutralMode(NeutralMode.Brake);
        m_leftArm = RobotProvider.instance.getSolenoid("climber.armsLeft");
        m_rightArm = RobotProvider.instance.getSolenoid("climber.armsRight");
        m_bottom = RobotProvider.instance.getDigitalInput("climber.bottomDigitalInput");
        m_top = RobotProvider.instance.getDigitalInput("climber.topDigitalInput");

        elevatorTop = ConfigFileReader.getInstance().getInt("climber.elevatorTop"); //670
        elevatorBottom = ConfigFileReader.getInstance().getInt("climber.elevatorBottom"); //0
        elevatorLeniency = ConfigFileReader.getInstance().getInt("climber.elevatorHeightLeniency"); //5
        elevatorPower = ConfigFileReader.getInstance().getDouble("climber.elevatorScaledPower"); //1
        slowMode = ConfigFileReader.getInstance().getInt("climber.slowMode"); //0

        loggerCategory = Category.ELEVATOR;
    }

    
    public void setElevatorPower(double power) {
        power *= elevatorPower.get();
        checkContextOwnership();
        if (power < 0 && getElevatorBottom() - getElevatorPosition() >= getElevatorLeniency() && !m_bottom.get()) {
            m_elevator.set(-power);
        } else if (power > 0 && getElevatorPosition() - getElevatorTop() >= getElevatorLeniency() && !m_top.get()) {
            m_elevator.set(-power);
        } else {
            m_elevator.set(0);
        } 
    }

    public void setElevatorPowerUnrestricted(double power){
        power *= elevatorPower.get();
        checkContextOwnership();
        if (power < 0 && !m_bottom.get()) {
            m_elevator.set(-power);
        } else if (power > 0 && !m_top.get()) {
            m_elevator.set(-power);
        } else {
            m_elevator.set(0);
        } 
    }

    public void setElevatorPowerTrueUnrestricted(double power){
        power *= elevatorPower.get();
        checkContextOwnership();
        if(power > -0.3 && power < 0.3){
            m_elevator.set(-power);
        }
    }
    
    public double getElevatorPosition() {
        
        return m_elevator.getSensorPosition();
	}
    
    public void resetElevatorPosition() {
        m_elevator.setPosition(0);
	}

    public void setElevatorPosition(int pos) {
        m_elevator.setPosition(pos);
	}
	
	public void setArmsPower(double power) {
		checkContextOwnership();
        DoubleSolenoid.State armsState;
        if (power > 0) {
            log("Backward!");
            armsState = DoubleSolenoid.State.Backward;
        } else if (power < 0) {
            log("Forward!");
            armsState = DoubleSolenoid.State.Forward;
        } else {
            log("Neutral!");
            armsState = DoubleSolenoid.State.Neutral;
        }
        m_leftArm.set(armsState);
        m_rightArm.set(armsState);
    }

    public boolean getArmsPower() {
        return m_leftArm.get();
    }

    public double getElevatorPower() {
        return elevatorPower.get();
    }

    public boolean getLimitSwitchBottom() {
        return m_bottom.get();
    }

    public boolean getLimitSwitchTop() {
        return m_top.get();
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

    @Override
    public void run() {
        //log("Elevator Encoder: " + getElevatorPosition());
    }
}
