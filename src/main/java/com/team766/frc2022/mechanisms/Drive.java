package com.team766.frc2022.mechanisms;
import edu.wpi.first.wpilibj.I2C.Port;

import com.team766.framework.Mechanism;
import com.team766.hal.CANSpeedController;
import com.team766.hal.RobotProvider;
import com.team766.hal.GyroReader;
import com.kauailabs.navx.frc.*;
public class Drive extends Mechanism {
    // Some other test change
    private CANSpeedController m_leftVictor1;
    private CANSpeedController m_leftVictor2;
    private CANSpeedController m_rightVictor1;
    private CANSpeedController m_rightVictor2;
    private  CANSpeedController m_leftTalon;
    private  CANSpeedController m_rightTalon;
    private AHRS m_gyro;

    public Drive() {
        // Initialize victors
        m_leftVictor1 = RobotProvider.instance.getVictorCANMotor("drive.leftVictor1"); 
        m_rightVictor1 = RobotProvider.instance.getVictorCANMotor("drive.rightVictor1");

        // Initialize second victors if they exist
        // if (ConfigFileReader.getInstance().getInt("drive.leftVictor2").get() >= 0) {
        //     m_secondVictor = true;
        //     m_leftVictor2 = RobotProvider.instance.getVictorCANMotor("drive.leftVictor2");
        //     m_rightVictor2 = RobotProvider.instance.getVictorCANMotor("drive.rightVictor2");    
        // } else {
        //    m_secondVictor = false;
        // }

        // Initializes talons
        m_leftTalon = RobotProvider.instance.getTalonCANMotor("drive.leftTalon");
        m_rightTalon = RobotProvider.instance.getTalonCANMotor("drive.rightTalon");
        
        // Initialize gyro
       // m_gyro = RobotProvider.instance.getGyro("drive.gyro");
       m_gyro = new AHRS(Port.kOnboard);
 
       //m_gyroDirection = ConfigFileReader.getInstance().getDouble("drive.gyroDirection").get();
    }
    public double getGyroAngle() {
        return((m_gyro.getAngle() % 360) );
    }
    public void run(){
        System.out.println("Gyro: " + getGyroAngle() + " Connected? " + m_gyro.isConnected());
    }
}
