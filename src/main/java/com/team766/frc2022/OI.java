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
		while (true) {
			// Add driver controls here - make sure to take/release ownership
			// of mechanisms when appropriate.
			context.takeOwnership(Robot.drive);
			context.takeOwnership(Robot.climber);
			context.takeOwnership(Robot.climberEx);
			log("J0 A0: " + m_joystick0.getAxis(0) +
			    "  J0 A1: " + m_joystick0.getAxis(1) +
			    "  J1 A0: " + m_joystick1.getAxis(0) +
			    "  J1 A1: " + m_joystick1.getAxis(1) +
			    "  J0 B1: " + m_joystick0.getButton(1) +
			    "  J0 B2: " + m_joystick0.getButton(2) +
				"  J0 B3: " + m_joystick0.getButton(3) +
				"  J1 B1: " + m_joystick1.getButton(1) +
				"  J1 B2: " + m_joystick1.getButton(2) +
				"  J1 B3: " + m_joystick1.getButton(3));
				
			Robot.drive.setArcadeDrivePower(m_joystick0.getAxis(1), m_joystick0.getAxis(0));
			Robot.climber.setElevatorMotor(m_joystick1.getAxis(1));
			Robot.climberEx.setArmsPusher(m_joystick1.getButton(2));

			context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
		}
	}
}
