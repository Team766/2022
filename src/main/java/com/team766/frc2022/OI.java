package com.team766.frc2022;

import com.team766.framework.Procedure;
import com.ShooterVelociltyUtil;
import com.team766.config.ConfigFileReader;
import com.team766.framework.Context;
import com.team766.frc2022.Robot;
import com.team766.frc2022.mechanisms.Elevator;
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
		int index = 0;
		double prev_time = RobotProvider.instance.getClock().getTime();

		context.takeOwnership(Robot.drive);
		context.takeOwnership(Robot.shooter);

		Robot.shooter.setPIDValues();

		//LaunchedContext climbingContext = null;
		while (true) {
			// TODO: tweak all of this based on actual revB controls
			Robot.drive.setArcadeDrivePower(
				-m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD), 
				m_rightJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT));

			double cur_time = RobotProvider.instance.getClock().getTime();
			if (cur_time-prev_time >= 0.5){
				log("Elevator: " + Robot.elevator.getElevatorPosition() + " Arms: " + Robot.elevator.getArmsPower());
				log("Pitch: " + Robot.gyro.getGyroPitch() + " Yaw: " + Robot.gyro.getGyroYaw() + " Roll: " + Robot.gyro.getGyroRoll());
				log("Top: " + Robot.elevator.getLimitSwitchTop() + " Bottom: " + Robot.elevator.getLimitSwitchBottom());
				log("Top switch: " + Boolean.toString(Robot.elevator.getLimitSwitchTop()) + " Bottom switch:" + Boolean.toString(Robot.elevator.getLimitSwitchBottom()));
				log("Velocity: "+Robot.shooter.getVelocity());
				log("Distance: "+Robot.limelight.distanceFromTarget());
				prev_time = cur_time;
			}
		
			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ELEVATOR_UP_BUTTON)) {
				context.takeOwnership(Robot.elevator);
				Robot.elevator.setElevatorPower(0.5);
				context.releaseOwnership(Robot.elevator);
				log("UP");
			} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_ELEVATOR_UP_BUTTON)) {
				context.takeOwnership(Robot.elevator);
				Robot.elevator.setElevatorPower(0);
				context.releaseOwnership(Robot.elevator);
				log("UP Stop");
			} 

			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ELEVATOR_DOWN_BUTTON)) {
				context.takeOwnership(Robot.elevator);
				Robot.elevator.setElevatorPower(-0.5);
				context.releaseOwnership(Robot.elevator);
				log("Down");
			} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_ELEVATOR_DOWN_BUTTON)) {
				context.takeOwnership(Robot.elevator);
				Robot.elevator.setElevatorPower(0);
				context.releaseOwnership(Robot.elevator);
				log("down stop");
			} 

			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ELEVATOR_TOP_BUTTON)) {
				context.takeOwnership(Robot.elevator);
				new ExtendElevator().run(context);				
				context.releaseOwnership(Robot.elevator);
			}
			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ELEVATOR_BOTTOM_BUTTON)) {
				context.takeOwnership(Robot.elevator);
				new RetractElevator().run(context);				
				context.releaseOwnership(Robot.elevator);
			}

			//1 pushes the arms forward, -1 pushes the arms backwards
			
			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ARMS_SWITCH)) {
				context.takeOwnership(Robot.elevator);				
				Robot.elevator.setArmsPower(1);
				context.releaseOwnership(Robot.elevator);
			} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_ARMS_SWITCH)) {
				context.takeOwnership(Robot.elevator);				
				Robot.elevator.setArmsPower(-1);
				context.releaseOwnership(Robot.elevator);
			}

			if(m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_AUTOCLIMB_BUTTON)){
				context.startAsync(new Climb());
			}

			if (m_leftJoystick.getButtonPressed(InputConstants.JOYSTICK_ELEVATOR_RESET_BUTTON)){
				//context.takeOwnership(Robot.shooter);
				log("Activated");
				context.startAsync(new ResetElevator());
			}

			if(m_leftJoystick.getButtonPressed(InputConstants.JOYSTICK_CLIMB_RUNG_BUTTON)){
				context.startAsync(new ClimbRung());
			}

			if(m_leftJoystick.getButtonPressed(InputConstants.JOYSTICK_CLIMB_FIRST_RUNG_BUTTON)){
				context.startAsync(new ClimbFirstRung());
			}
			// 
			//log(""+Robot.elevator.getElevatorPosition());

			double dialPower = m_ControlPanel.getAxis(InputConstants.AXIS_SHOOTER_DIAL);
			if (m_ControlPanel.getButton(InputConstants.CONTROL_PANEL_SHOOTER_SWITCH)){
				double configPower = ConfigFileReader.getInstance().getDouble("shooter.velocity").get();
				double power = ((dialPower - 0.6456)*3.734)*configPower;
				Robot.shooter.setVelocity(power);
				log("shooter power:" + power);
			} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_SHOOTER_SWITCH)) {
				Robot.shooter.stopShoot();
			}

			//log(""+m_ControlPanel.getAxis(InputConstants.AXIS_SHOOTER_DIAL));
			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_INTAKE_BUTTON)) {
				if (index % 2 == 0){
					new StartIntake().run(context);
				}else{
					new StopIntake().run(context);
				}
				index++;
			} 

			/*if (m_joystick0.getButtonPressed(5)) {
				context.takeOwnership(Robot.gyro);
				Robot.gyro.resetGyro();
				context.releaseOwnership(Robot.gyro);
			}*/

/*
			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ELEVATOR_UP_BUTTON)) {
				context.startAsync(new ExtendElevator());
			}
			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ELEVATOR_DOWN_BUTTON)) {
				context.startAsync(new RetractElevator());
			}
*/
			if (m_rightJoystick.getButtonPressed(InputConstants.JOYSTICK_TRIGGER)) {
				new StartBelts().run(context);
			} else if (m_rightJoystick.getButtonReleased(InputConstants.JOYSTICK_TRIGGER)){
				new StopBelts().run(context);
			}
			
			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_SPITBALL_BUTTON)){
				new SpitBall().run(context);
			} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_SPITBALL_BUTTON)) {
				new StopBelts().run(context);
				new StopIntake().run(context);
				new StopArms().run(context);
			}

			if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_AUTO_SHOOT)) {
				double distance = Robot.limelight.limelightFilter(context);
				log("Calculated distance");
				if (distance != 0){
					//context.takeOwnership(Robot.shooter);
					log("Auto Shooting");
					double power = ShooterVelociltyUtil.computeVelocityForDistance(distance);
					Robot.shooter.setVelocity(power);
					if(power == 0.0) {
						log("out of range");
					} else {
						log("set velocity to " + power);
					}
				}
			} 
			// else if (m_leftJoystick.getButtonReleased(InputConstants.CONTROL_PANEL_AUTO_SHOOT)){
			// 	Robot.shooter.stopShoot();
			// }

			// if (m_leftJoystick.getButtonPressed(2)){
			// 	//context.takeOwnership(Robot.shooter);
			// 	Robot.shooter.basicShoot();
			// }// } else if (m_leftJoystick.getButtonReleased(2)){
			// 	Robot.shooter.stopShoot();
			// }

			// log("Velocity: "+Robot.shooter.getVelocity());
			// log("Distance: "+Robot.limelight.distanceFromTarget());
			
			/* if (m_leftJoystick.getButtonPressed(3)) {
				context.startAsync(new activateShooter());
			} else if (m_leftJoystick.getButtonReleased(3)) {
				context.startAsync(new StopShooter());
			} */

			context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
		}
	}
}
