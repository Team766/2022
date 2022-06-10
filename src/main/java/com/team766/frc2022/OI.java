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
			Robot.drive.setGyro(Robot.gyro.getGyroYaw());		
			//log(Robot.gyro.getGyroYaw());			
			// To test each PID individually or set angles:
			// Robot.drive.setFrontLeftAngle(0);
			// Robot.drive.setFrontRightAngle(0);
			// Robot.drive.setBackLeftAngle(0);
			// Robot.drive.setBackRightAngle(0);
			// Robot.drive.logs();
			if(m_leftJoystick.getButton(InputConstants.CROSS_DEFENSE)){
				context.startAsync(new DefenseCross());
			} else{ 
				if(Math.pow(Math.pow(m_leftJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT),2) + Math.pow(m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD),2), 0.5) > 0.1 /*To make sure the robot can "stop if no one is using the joystick"*/ ){
					Robot.drive.drive2D(
						((m_leftJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT))),
						((m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD)))
					);
						lastX = m_leftJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT);
						lastY = (m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD));
				}  else {
					Robot.drive.stopSteerMotors();
					Robot.drive.stopSteerMotors();
				}
			}
			if(m_leftJoystick.getButtonPressed(1))
				Robot.gyro.resetGyro();
			//Use a cross defense when needed the most
			// if(m_leftJoystick.getButton(InputConstants.CROSS_DEFENSE)){
			// 	context.startAsync(new DefenseCross());
			// } else {
			// 	//If we want, we can just turn all the wheels with the POV, otherwise, we use the regular joystick
			// 	if(m_leftJoystick.getPOV() != -1){
			// 		Robot.drive.drive2D(m_leftJoystick.getPOV(), 0);
			// 	}
			// 	else{
			// 		//Bad code, but good enough for testing
			// 		Robot.drive.drive2D(
			// 			correctedJoysticks(m_leftJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT)),
			// 			correctedJoysticks(m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD))
			// 		);	
			// 		//Good code :)
			// 		/* 
			// 		Robot.drive.swerveDrive( 
			// 				correctedJoysticks(m_leftJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT)),
			// 				correctedJoysticks(m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD)),
			// 				correctedJoysticks(m_rightJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT))
			// 		);
			// 		*/
			// 	}
				
			//}

			double cur_time = RobotProvider.instance.getClock().getTime();
				context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
		}
	}
}
