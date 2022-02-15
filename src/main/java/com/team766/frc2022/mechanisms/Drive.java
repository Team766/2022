package com.team766.frc2022.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.RobotProvider;
import com.team766.hal.SpeedController;
import com.team766.logging.Category;

public class Drive extends Mechanism {
    // Declaration of Mechanisms
    private SpeedController m_leftMotor;
    private SpeedController m_rightMotor;

    public Drive() {
        // Initializations
        m_leftMotor = RobotProvider.instance.getTalonCANMotor("drive.leftMotor");
        m_rightMotor = RobotProvider.instance.getTalonCANMotor("drive.rightMotor");
    }

    public void setDrivePower(double leftPower, double rightPower) {
        loggerCategory = Category.DRIVE;
		checkContextOwnership();

        m_leftMotor.set(leftPower);
        m_rightMotor.set(rightPower);
    }
    
    public void setArcadeDrivePower(double forward, double turn) {
		setDrivePower(turn + forward, -turn + forward);
    }
    
}
