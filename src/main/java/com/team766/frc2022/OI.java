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
	
	public OI() {
		loggerCategory = Category.OPERATOR_INTERFACE;

		m_leftJoystick = RobotProvider.instance.getJoystick(0);
		m_rightJoystick = RobotProvider.instance.getJoystick(1);
	}
	public double getAngle(double LR, double FB){
		return Math.toDegrees(Math.atan2(LR ,-FB));
	}
	public double correctedJoysticks(double Joystick){
		return(3.0*Math.pow(Joystick,2)-2.0*Math.pow(Joystick,3));
	}
	public double pythagrianJoysticks(double First, double Second){
		return Math.sqrt(Math.pow(First,2)+Math.pow(Second,2));
	}

	public void run(Context context) {
		double prev_time = RobotProvider.instance.getClock().getTime();

		context.takeOwnership(Robot.drive);
		
		while (true) {
			//log(getAngle(m_leftJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT) ,m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD)));
			/*
			 To test each PID individually or set angles:
			  	Robot.drive.setFrontLeftAngle(90);
			  	Robot.drive.setFrontRightAngle(90);
			  	Robot.drive.setBackLeftAngle(90);
			  	Robot.drive.setBackRightAngle(90);
			*/
		
			//Use a cross defense when needed the most
			if(m_leftJoystick.getButton(InputConstants.CROSS_DEFENSE)){
				context.startAsync(new DefenseCross());
			}
			else{
				//If we want, we can just turn all the wheels with the POV, otherwise, we use the regular joystick
				if(m_leftJoystick.getPOV() != 1){
					Robot.drive.drive2D(m_leftJoystick.getPOV(), 0);
				}
				else{
				Robot.drive.drive2D(
					getAngle(m_leftJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT), m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD)), 
					pythagrianJoysticks(correctedJoysticks(m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD)), correctedJoysticks(m_leftJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT)))
				);
				}
			}

			double cur_time = RobotProvider.instance.getClock().getTime();
				context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
		}
	}
}
