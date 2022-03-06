package com.team766.frc2022.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.CANSpeedController;
import com.team766.hal.RobotProvider;

public class Drive extends Mechanism {
    private CANSpeedController m_leftMotor1;
    private CANSpeedController m_rightMotor1;
    private CANSpeedController m_leftMotor2;
    private CANSpeedController m_rightMotor2;

    public Drive() {
        m_leftMotor1 = RobotProvider.instance.getCANMotor("drive.leftMotor1");
        m_rightMotor1 = RobotProvider.instance.getCANMotor("drive.rightMotor1");
        m_leftMotor2 = RobotProvider.instance.getCANMotor("drive.leftMotor2");
        m_rightMotor2 = RobotProvider.instance.getCANMotor("drive.rightMotor2");
    }

    public void setDrivePower(double leftPower, double rightPower) {
        checkContextOwnership();

        m_leftMotor1.set(leftPower);
        m_rightMotor1.set(rightPower);
        m_leftMotor2.set(leftPower);
        m_rightMotor2.set(rightPower);
    }

    public void setArcadeDrivePower(double forward, double turn) {
        checkContextOwnership();

        double leftMotorPower = turn + forward;  
        double rightMotorPower = -turn + forward;
        setDrivePower(leftMotorPower, rightMotorPower);
    }
}
