package com.team766.frc2022;

import com.team766.framework.Procedure;
import com.ShooterVelociltyUtil;
import com.team766.config.ConfigFileReader;
import com.team766.framework.Context;
import com.team766.frc2022.Robot;
import com.team766.frc2022.mechanisms.Limelight;
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
	private boolean b = true;

	public OI() {
		loggerCategory = Category.OPERATOR_INTERFACE;

		m_leftJoystick = RobotProvider.instance.getJoystick(InputConstants.LEFT_JOYSTICK);
		m_rightJoystick = RobotProvider.instance.getJoystick(InputConstants.RIGHT_JOYSTICK);
		m_ControlPanel = RobotProvider.instance.getJoystick(InputConstants.CONTROL_PANEL);
	}

	public void run(Context context) {
		context.takeOwnership(Robot.drive);
		context.takeOwnership(Robot.shooter);

		Robot.shooter.setPIDValues();

		//LaunchedContext climbingContext = null;
		while (true) {
			// TODO: tweak all of this based on actual revB controls
			Robot.drive.setArcadeDrivePower(
				-m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD), 
				m_rightJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT));

			log("Elevator: " + Robot.elevator.getElevatorPosition() + " Arms: " + Robot.elevator.getArmsPower());
			log("Pitch: " + Robot.gyro.getGyroPitch() + " Yaw: " + Robot.gyro.getGyroYaw() + " Roll: " + Robot.gyro.getGyroRoll());
			log("Top: " + Robot.elevator.getLimitSwitchTop() + " Bottom: " + Robot.elevator.getLimitSwitchBottom());

			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ELEVATOR_UP_BUTTON)) {
				context.takeOwnership(Robot.elevator);
				Robot.elevator.setElevatorPower(-1);
				context.releaseOwnership(Robot.elevator);
			} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_ELEVATOR_DOWN_BUTTON)) {
				context.takeOwnership(Robot.elevator);
				Robot.elevator.setElevatorPower(1);
				context.releaseOwnership(Robot.elevator);
			} else {
				context.takeOwnership(Robot.elevator);				
				Robot.elevator.setElevatorPower(0);
				context.releaseOwnership(Robot.elevator);
			}

			// TODO: change dis
			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ARMS_SWITCH)) {
				context.takeOwnership(Robot.elevator);				
				Robot.elevator.setArmsPower(1);
				context.releaseOwnership(Robot.elevator);
			} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_ARMS_SWITCH)) {
				context.takeOwnership(Robot.elevator);				
				Robot.elevator.setArmsPower(-1);
				context.releaseOwnership(Robot.elevator);
			}

			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ELEVATOR_BOTTOM_BUTTON)) {
				context.takeOwnership(Robot.elevator);				
				Robot.elevator.resetElevatorPosition();
				context.releaseOwnership(Robot.elevator);
			}

			double dialPower = m_ControlPanel.getAxis(InputConstants.AXIS_SHOOTER_DIAL);
			if (m_ControlPanel.getButton(InputConstants.CONTROL_PANEL_SHOOTER_SWITCH)){
				double configPower = ConfigFileReader.getInstance().getDouble("shooter.velocity").get();
				double power = ((dialPower - 0.6456)*3.734)*configPower;
				Robot.shooter.setVelocity(power);
				log("shooter power:" + power);
			} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_SHOOTER_SWITCH)) {
				Robot.shooter.stopShoot();
			}

			log(""+m_ControlPanel.getAxis(InputConstants.AXIS_SHOOTER_DIAL));
			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_INTAKE_BUTTON)) {
				context.startAsync(new StartIntake());
			} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_INTAKE_BUTTON)){
				context.startAsync(new StopIntake());
			}

			/*if (m_joystick0.getButtonPressed(5)) {
				context.takeOwnership(Robot.gyro);
				Robot.gyro.resetGyro();
				context.releaseOwnership(Robot.gyro);
			}*/

			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ELEVATOR_UP_BUTTON)) {
				context.startAsync(new ExtendElevator());
			}
			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ELEVATOR_DOWN_BUTTON)) {
				context.startAsync(new RetractElevator());
			}
			
			if (m_rightJoystick.getButtonPressed(InputConstants.JOYSTICK_TRIGGER)) {
				context.startAsync(new StartBelts());
			} else if (m_rightJoystick.getButtonReleased(InputConstants.JOYSTICK_TRIGGER)){
				context.startAsync(new StopBelts());
			}
			
			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_SPITBALL_BUTTON)){
				context.startAsync(new SpitBall());
			} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_SPITBALL_BUTTON)) {

			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_AUTO_SHOOT)) {
				double distance = Robot.limelight.limelightFilter(context);
				if (distance != 0){
					double power = ShooterVelociltyUtil.computeVelocityForDistance(distance);
					Robot.shooter.setVelocity(power);
					if(power == 0.0) {
						log("out of range");
					} else {
						log("set velocity to " + power);
					}
				}
			}

			log("Velocity: "+Robot.shooter.getVelocity());
			log("Distance: "+Robot.limelight.distanceFromTarget());
			if (m_joystick0.getButtonPressed(3)) {
				context.startAsync(new activateShooter());
			} else if (m_joystick0.getButtonReleased(3)){
				context.startAsync(new StopShooter());
			}

			context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
		}
	}
}
