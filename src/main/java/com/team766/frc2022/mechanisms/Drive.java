package com.team766.frc2022.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.CANSpeedController;
import com.team766.hal.RobotProvider;
import com.team766.hal.SolenoidController;
import com.team766.hal.SpeedController;
import com.team766.hal.mock.Solenoid;

public class Drive extends Mechanism {
    // Some other test change
    private CANSpeedController m_leftMotor;
    private CANSpeedController m_rightMotor;

    public Drive(){
        m_leftMotor = RobotProvider.instance.getTalonCANMotor("drive.leftMotor");
        m_rightMotor = RobotProvider.instance.getTalonCANMotor("drive.rightMotor");
    }

    public void setArcadeDrivePower(double forward, double turn) {
        checkContextOwnership();
		double leftMotorPower = turn + forward;
		double rightMotorPower = -turn + forward;
        
        m_leftMotor.set(leftMotorPower);
        m_rightMotor.set(rightMotorPower);
	}

}
