package com.team766.frc2022;

import com.team766.framework.Procedure;
import com.team766.framework.Context;
import com.team766.frc2022.Robot;
//import com.team766.frc2022.procedures.*;
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
	
	public void run(Context context) {
		int index = 0; // index counter for the intake
		double autopower = 0; // power given during auto shooting
		double prev_time = RobotProvider.instance.getClock().getTime();

		context.takeOwnership(Robot.drive);
		
		//LaunchedContext climbingContext = null;
		while (true) {
			// TODO: tweak all of this based on actual revB controls
			Robot.drive.setDrivePower(
				m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD), 
				m_leftJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT));
			double cur_time = RobotProvider.instance.getClock().getTime();
				context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
		}
	}
}
