package com.team766.frc2022;

import com.team766.framework.Procedure;
import com.team766.framework.Context;
import com.team766.frc2022.Robot;
import com.team766.frc2022.procedures.DefenseCross;
import com.team766.frc2022.procedures.*;
import com.team766.hal.JoystickReader;
import com.team766.hal.RobotProvider;
import com.team766.logging.Category;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the code that allow control of the robot.
 */
public class OI extends Procedure {
	private JoystickReader m_leftJoystick;
	private JoystickReader m_rightJoystick;
	private double lastX = 0;
	private double lastY = 0;
	double turningValue = 0;
	public OI() {
		loggerCategory = Category.OPERATOR_INTERFACE;

		m_leftJoystick = RobotProvider.instance.getJoystick(0);
		m_rightJoystick = RobotProvider.instance.getJoystick(1);
	}

	public void run(Context context) {
		double prev_time = RobotProvider.instance.getClock().getTime();
		context.takeOwnership(Robot.gyro);
		context.takeOwnership(Robot.drive);
		//Robot.gyro.resetGyro();
		Robot.drive.setFrontRightEncoders();
		Robot.drive.setFrontLeftEncoders();
		Robot.drive.setBackRightEncoders();
		Robot.drive.setBackLeftEncoders();
		
		while (true) {
			//log(getAngle(m_leftJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT) ,m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD)));
			if(m_rightJoystick.getButton(7)){
				Robot.drive.setGyro(0);

			}else{
				Robot.drive.setGyro(Robot.gyro.getGyroYaw());

			}		
			//log(Robot.gyro.getGyroYaw());			
			//TODO: fix defense: the robot basically locks up if there is defense
			// if(m_leftJoystick.getButton(InputConstants.CROSS_DEFENSE)){
			// 	context.startAsync(new DefenseCross());
			// }
			
			/*if(Math.pow(Math.pow(m_leftJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT),2) + Math.pow(m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD),2), 0.5) > 0.15 ){
				Robot.drive.drive2D(
					((m_leftJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT))),
					((m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD)))
				);
			}  else {
				if(Math.abs(m_leftJoystick.getAxis(InputConstants.AXIS_TWIST))>=0.1){
					Robot.drive.turning(m_leftJoystick.getAxis(InputConstants.AXIS_TWIST));
				} else {
				Robot.drive.stopDriveMotors();
				Robot.drive.stopSteerMotors();
				}
			}*/
			if(m_leftJoystick.getButtonPressed(1))
				Robot.gyro.resetGyro();
			
			if(m_leftJoystick.getButtonPressed(2)){
				Robot.drive.setFrontRightEncoders();
				Robot.drive.setFrontLeftEncoders();
				Robot.drive.setBackRightEncoders();
				Robot.drive.setBackLeftEncoders();
			}
			if(m_rightJoystick.getButton(1)){
				turningValue = m_rightJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT); 
			} else {
				turningValue = 0;
			}
			if(Math.abs(m_leftJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT))+
			Math.abs(m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD)) > 0.05){
			Robot.drive.swerveDrive( 
				(m_leftJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT)),
			 	(m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD)),
			 	(turningValue)
			);} else{
				Robot.drive.stopDriveMotors();
				Robot.drive.stopSteerMotors();				
			}

			double cur_time = RobotProvider.instance.getClock().getTime();
				context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
		}
	}
}
