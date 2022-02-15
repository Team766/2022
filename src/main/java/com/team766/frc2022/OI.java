package com.team766.frc2022;

import com.team766.framework.Procedure;
import com.team766.framework.Context;
import com.team766.frc2022.Robot;
import com.team766.frc2022.procedures.*;
import com.team766.hal.JoystickReader;
import com.team766.hal.RobotProvider;
import com.team766.logging.Category;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the code that allow control of the robot.
 */
public class OI extends Procedure {
	private JoystickReader m_joystick0;
	private JoystickReader m_joystick1;
	private JoystickReader m_joystick2;
	
	public OI() {
		loggerCategory = Category.OPERATOR_INTERFACE;

		m_joystick0 = RobotProvider.instance.getJoystick(0);
		m_joystick1 = RobotProvider.instance.getJoystick(1);
		m_joystick2 = RobotProvider.instance.getJoystick(2);
	}
	
	public void run(Context context) {
		context.takeOwnership(Robot.drive);
		boolean joystickControl = true;
		//LaunchedContext climbingContext = null;
		while (true) {
			// Add driver controls here - make sure to take/release ownership
			// of mechanisms when appropriate.
			Robot.drive.setArcadeDrivePower(m_joystick0.getAxis(1), m_joystick0.getAxis(0));
			if (Math.abs(m_joystick1.getAxis(1)) + Math.abs(m_joystick1.getAxis(0))> 0.3) {
				context.takeOwnership(Robot.elevator);
				Robot.elevator.setElevatorPower(m_joystick1.getAxis(1));
				Robot.elevator.setArmsPower(m_joystick1.getAxis(0));
				context.releaseOwnership(Robot.elevator);
				joystickControl = true;
			} else if (joystickControl) {
				joystickControl = false;
				context.takeOwnership(Robot.elevator);
				Robot.elevator.setElevatorPower(0);
				Robot.elevator.setArmsPower(0);
				context.releaseOwnership(Robot.elevator);
			}

			//if (climbingContext != null && !climbingContext.isDone())

			if (m_joystick0.getButtonPressed(1)) {
				log("Climbing!");
				/*climbingContext = */
				context.startAsync(new ClimbRung());
			}

			context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
		}
	}
}
