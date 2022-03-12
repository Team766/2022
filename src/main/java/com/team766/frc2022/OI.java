package com.team766.frc2022;

import com.team766.framework.Procedure;
import com.team766.config.ConfigFileReader;
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
		context.takeOwnership(Robot.shooter);
		Robot.shooter.setPIDValues();
		while (true) {
			Robot.drive.setArcadeDrivePower(-m_joystick0.getAxis(1), m_joystick1.getAxis(0));

			double shooterPower = m_joystick2.getAxis(3);
			if (m_joystick2.getButtonPressed(1)){
				double power = ConfigFileReader.getInstance().getDouble("shooter.velocity").get();
				double powerPercent = (power - 0.6456)*3.734;
				Robot.shooter.setVelocity(shooterPower*powerPercent);
			} else if(m_joystick2.getButtonReleased(1)) {
				Robot.shooter.stopShoot();
			}

			log(""+m_joystick2.getAxis(3));
			
			if (m_joystick2.getButtonPressed(2)) {
				context.startAsync(new StartIntake());
			} else if (m_joystick2.getButtonReleased(2)){
				context.startAsync(new StopIntake());
			}

			/*if(m_joystick2.getButtonPressed(1)){
				context.startAsync(new StartArms());
			}else if(m_joystick2.getButtonReleased(1)){
				context.startAsync(new StopArms());
			}*/

			
			if (m_joystick1.getButtonPressed(1)) {
				context.startAsync(new StartBelts());
			} else if (m_joystick1.getButtonReleased(1)){
				context.startAsync(new StopBelts());
			}

			if(m_joystick2.getButtonPressed(14)){
				context.startAsync(new SpitBall());
			}else if(m_joystick2.getButtonReleased(14)){
				context.startAsync(new StopBelts());
				context.startAsync(new StopIntake());
				context.startAsync(new StopArms());
			}
			
			log(""+Robot.shooter.getVelocity());

			if (m_joystick0.getButtonPressed(3)) {
				context.startAsync(new activateShooter());
			} else if (m_joystick0.getButtonReleased(3)){
				context.startAsync(new StopShooter());
			}

			context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
		}
	}
}
