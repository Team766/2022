package com.team766.frc2022.mechanisms;

import com.team766.config.ConfigFileReader;
import com.team766.framework.Mechanism;
import com.team766.hal.CANSpeedController;
import com.team766.hal.RobotProvider;
import com.team766.hal.SpeedController;
import com.team766.library.ValueProvider;
import com.team766.logging.Category;

public class Drive extends Mechanism {
    // Declaration of Mechanisms
    private CANSpeedController m_leftVictor1;
    private CANSpeedController m_leftVictor2;
    private CANSpeedController m_rightVictor1;
    private CANSpeedController m_rightVictor2;
    private CANSpeedController m_leftTalon;
    private CANSpeedController m_rightTalon;
    private ValueProvider<Double> drivePower;

    public Drive() {
        // Initializations
        m_leftVictor1 = RobotProvider.instance.getVictorCANMotor("drive.leftVictor1"); 
        m_rightVictor1 = RobotProvider.instance.getVictorCANMotor("drive.rightVictor1");
        m_leftVictor2 = RobotProvider.instance.getVictorCANMotor("drive.leftVictor2");
        m_rightVictor2 = RobotProvider.instance.getVictorCANMotor("drive.rightVictor2");
        m_leftTalon = RobotProvider.instance.getTalonCANMotor("drive.leftTalon");
        m_rightTalon = RobotProvider.instance.getTalonCANMotor("drive.rightTalon");
        //m_rightVictor1.follow(m_rightTalon);
        //m_rightVictor2.follow(m_rightTalon);
        //m_leftVictor1.follow(m_leftTalon);
        //m_leftVictor2.follow(m_leftTalon);
        drivePower = ConfigFileReader.getInstance().getDouble("drive.drivePower"); //1
    }

    public void setDrivePower(double leftPower, double rightPower) {
        loggerCategory = Category.DRIVE;
		checkContextOwnership();

        leftPower *= drivePower.get();
        rightPower *= drivePower.get();
        m_leftTalon.set(leftPower);
        m_rightTalon.set(rightPower);
    }
    
    public void setArcadeDrivePower(double forward, double turn) {
		setDrivePower(turn + forward, -turn + forward);
    }
    
}
