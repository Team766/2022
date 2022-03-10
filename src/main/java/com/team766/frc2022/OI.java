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
		boolean b1 = true;
		while (true) {
			Robot.drive.setArcadeDrivePower(m_joystick0.getAxis(1), m_joystick0.getAxis(0));
			
			if (m_joystick0.getButtonPressed(1)) {
				context.startAsync(new StartIntake());
			} else if (m_joystick0.getButtonReleased(1)){
				context.startAsync(new StopIntake());
			}

			if (b1){
				if (m_joystick0.getButtonPressed(2)) {
					context.startAsync(new StartBelts());
				} else if (m_joystick0.getButtonReleased(2)){
					context.startAsync(new StopBelts());
				}
			}

			if (m_joystick0.getButtonPressed(3)) {
				context.startAsync(new StartShooter());
			} else if (m_joystick0.getButtonReleased(3)){
				context.startAsync(new StopShooter());
			}

			if (m_joystick0.getButtonPressed(4)){
				b1 = false;
			}
			context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
		}
	}
}
