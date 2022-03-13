package com.team766.frc2022;

import com.team766.framework.Procedure;
import com.team766.config.ConfigFileReader;
import com.team766.framework.Context;
import com.team766.frc2022.Robot;
import com.team766.frc2022.procedures.*;
import com.team766.hal.JoystickReader;
import com.team766.hal.RobotProvider;
import com.team766.logging.Category;
// 1 min 18s to fill air

// TODO: extract joysticks, buttons into constants class or config file

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the code that allow control of the robot.
 */
public class OI extends Procedure {
	private JoystickReader m_leftJoystick;
	private JoystickReader m_rightJoystick;
	private JoystickReader m_ControlPanel;
	
	public OI() {
		loggerCategory = Category.OPERATOR_INTERFACE;

		m_leftJoystick = RobotProvider.instance.getJoystick(InputConstants.LEFT_JOYSTICK);
		m_rightJoystick = RobotProvider.instance.getJoystick(InputConstants.RIGHT_JOYSTICK);
		m_controlPanel = RobotProvider.instance.getJoystick(InputConstants.CONTROL_PANEL);
	}
	
	public void run(Context context) {
		context.takeOwnership(Robot.drive);
		context.takeOwnership(Robot.shooter);
		context.takeOwnership(Robot.elevator); //move somewhere else later

		Robot.shooter.setPIDValues();

		while (true) {
			// TODO: tweak all of this based on actual revB controls
			Robot.drive.setArcadeDrivePower(
				-m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD), 
				m_rightJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT));

			log("Elevator: " + Robot.elevator.getElevatorPosition() + " Arms: " + Robot.elevator.getArmsPower());
			log("Pitch: " + Robot.gyro.getGyroPitch() + " Yaw: " + Robot.gyro.getGyroYaw() + " Roll: " + Robot.gyro.getGyroRoll());
			log("Top: " + Robot.elevator.getLimitSwitchTop() + " Bottom: " + Robot.elevator.getLimitSwitchBottom());

			//Robot.drive.setArcadeDrivePower(m_leftJoystick.getAxis(1), m_leftJoystick.getAxis(0));
			Robot.elevator.setElevatorPower(-1 * m_leftJoystick.getAxis(1));

			// TODO: change dis
			if (m_controlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ARMS_SWITCH)) {
				if (m_leftJoystick.getButtonPressed()) {
					Robot.elevator.setArmsPower(0);
					log("Setting Power to 0 (1 and 7 both pressed)");
				} else {
					Robot.elevator.setArmsPower(1);
					log("Setting Power to 1 (1 Pressed)");
				}
			} else if (m_leftJoystick.getButtonPressed(7)) {
				Robot.elevator.setArmsPower(-1);
				log("Setting Power to -1 (7 Pressed)");
			}

			if (m_leftJoystick.getButtonPressed(2)) {
				Robot.elevator.resetElevatorPosition();
			}

			double shooterPower = m_ControlPanel.getAxis(InputConstants.AXIS_SHOOTER_DIAL);

			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_SHOOTER_SWITCH)){
				double power = ConfigFileReader.getInstance().getDouble("shooter.velocity").get();
				Robot.shooter.setVelocity(shooterPower*power);
			} else if(m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_SHOOTER_SWITCH)) {
				Robot.shooter.stopShoot();
			}

			log(""+m_ControlPanel.getAxis(InputConstants.AXIS_SHOOTER_DIAL));
			
			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_INTAKE_BUTTON)) {
				context.startAsync(new StartIntake());
			} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_INTAKE_BUTTON)){
				context.startAsync(new StopIntake());
			}

			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_INTAKE_BUTTON)) {
				context.startAsync(new StartBelts());
			} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_INTAKE_BUTTON)){
				context.startAsync(new StopBelts());
			}

			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_SPITBALL_BUTTON)){
				context.startAsync(new SpitBall());
			} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_SPITBALL_BUTTON)) {
				context.startAsync(new StopBelts());
				context.startAsync(new StopIntake());
				context.startAsync(new StopArms());
			}
			
			log(""+Robot.shooter.getVelocity());

			context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
		}
	}
}
