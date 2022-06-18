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
import com.team766.library.ValueProvider;

// 1 min 18s to fill air
import com.team766.web.dashboard.StatusLight;

// TODO: extract joysticks, buttons into constants class or config file

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the code that allow control of the robot.
 */

public class OI extends Procedure {
	private JoystickReader m_leftJoystick;
	private JoystickReader m_rightJoystick;
	private JoystickReader m_ControlPanel;
	private JoystickReader m_controller;
	private boolean b = true;
	private StatusLight light;
	private ValueProvider<Integer> controlMode;

	public OI() {
		loggerCategory = Category.OPERATOR_INTERFACE;

		controlMode = ConfigFileReader.getInstance().getInt("controlScheme"); //0 or 1

		m_leftJoystick = RobotProvider.instance.getJoystick(InputConstants.LEFT_JOYSTICK);
		m_rightJoystick = RobotProvider.instance.getJoystick(InputConstants.RIGHT_JOYSTICK);
		m_ControlPanel = RobotProvider.instance.getJoystick(InputConstants.CONTROL_PANEL);
		m_controller = RobotProvider.instance.getJoystick(InputConstants.CONTROLLER);
		light = new StatusLight("Ready to fire");
		light.setColor("Maroon");
	}

	public void run(Context context) {
		int index = 0; // index counter for the intake
		double autopower = 0; // power given during auto shooting
		boolean startShoot = false; //is it staring to shoot
		double prev_time = RobotProvider.instance.getClock().getTime();

		context.takeOwnership(Robot.drive);
		context.takeOwnership(Robot.shooter);

		Robot.shooter.setPIDValues();

		//LaunchedContext climbingContext = null;
		switch (controlMode.get()) {
			case 0:
				while (true) {
					// TODO: tweak all of this based on actual revB controls
					Robot.drive.setArcadeDrivePower(
						-m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD)*12, 
						m_rightJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT)*12,true);

					double cur_time = RobotProvider.instance.getClock().getTime();
				
					log("Distance: "+Robot.limelight.distanceFromTarget());
					log("Velocity: "+Robot.shooter.getVelocity());

					if (m_ControlPanel.getButton(InputConstants.CONTROL_PANEL_ELEVATOR_UP_BUTTON)) {
						context.takeOwnership(Robot.elevator);
						Robot.elevator.setElevatorPower(1);
						context.releaseOwnership(Robot.elevator);
						log("UP");
					} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_ELEVATOR_UP_BUTTON)) {
						context.takeOwnership(Robot.elevator);
						Robot.elevator.setElevatorPower(0);
						context.releaseOwnership(Robot.elevator);
						log("UP Stop");
					} 

					if (m_ControlPanel.getButton(InputConstants.CONTROL_PANEL_ELEVATOR_DOWN_BUTTON)) {
						context.takeOwnership(Robot.elevator);
						Robot.elevator.setElevatorPower(-1);
						context.releaseOwnership(Robot.elevator);
						log("Down");
					} else if (m_ControlPanel.getButtonReleased(InputConstants.CONTROL_PANEL_ELEVATOR_DOWN_BUTTON)) {
						context.takeOwnership(Robot.elevator);
						Robot.elevator.setElevatorPower(0);
						context.releaseOwnership(Robot.elevator);
						log("down stop");
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

					if (m_leftJoystick.getButtonPressed(InputConstants.JOYSTICK_ELEVATOR_RESET_BUTTON)){
						//context.takeOwnership(Robot.shooter);
						log("Activated");
						context.startAsync(new ResetElevator());
					}

					double dialPower = m_ControlPanel.getAxis(InputConstants.AXIS_SHOOTER_DIAL);
					if (m_ControlPanel.getButton(InputConstants.CONTROL_PANEL_SHOOTER_SWITCH)){
						log("Manual shooting starting.");
						double configPower = ConfigFileReader.getInstance().getDouble("shooter.velocity").get();
						double power = ((dialPower - 0.6456)*3.734)*configPower;
						Robot.shooter.setVelocity(power);
						log("Power set to:" + power);
						light.setColor("Maroon"); //color when we aren't ready to shoot
						startShoot = false;
					} 

					if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_INTAKE_BUTTON)) {
						if (index % 2 == 0){
							new StartIntake().run(context);
						}else{
							new StopIntake().run(context);
						}
						index++;

					} 
					
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

					if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_AUTO_SHOOT) && m_ControlPanel.getButton(InputConstants.CONTROL_PANEL_SHOOTER_SWITCH) == false) {
						light.setColor("Maroon");
						double distance = Robot.limelight.limelightFilter(context);
						log("Autoshooting starting.");
						log("Calculated distance");
						if (distance >= 2.5 && distance <= 5){
							log("Auto Shooting");
							autopower = ShooterVelociltyUtil.computeVelocityForDistance(distance);
							Robot.shooter.setVelocity(autopower);
							if(autopower == 0.0) {
								log("out of range");
							} else {
								log("Velocity set to:" + autopower);
								startShoot = true;
							}
						}
					} 

					if (startShoot){
						if (Math.abs(Robot.shooter.getVelocity()-autopower) <= 30.0){
							startShoot = false;
							light.setColor("LimeGreen"); //color when we're ready to shoot
						}
					}

					context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
				}
			default:
				while (true) {
					// TODO: tweak all of this based on actual revB controls
					Robot.drive.setArcadeDrivePower(
						-m_controller.getAxis(InputConstants.CONTROLLER_AXIS_LEFT_VERTICAL)*12, 
						m_controller.getAxis(InputConstants.CONTROLLER_AXIS_RIGHT_HORIZONTAL)*12,true);

					double cur_time = RobotProvider.instance.getClock().getTime();
				
					log("Distance: "+Robot.limelight.distanceFromTarget());
					log("Velocity: "+Robot.shooter.getVelocity());

					if (m_controller.getPOV() == 0 || m_controller.getPOV() == 45 || m_controller.getPOV() == 315) {
						context.takeOwnership(Robot.elevator);
						Robot.elevator.setElevatorPower(1);
						context.releaseOwnership(Robot.elevator);
						log("UP");
					} else if (m_controller.getPOV() == 135 || m_controller.getPOV() == 180 || m_controller.getPOV() == 225) {
						context.takeOwnership(Robot.elevator);
						Robot.elevator.setElevatorPower(-1);
						context.releaseOwnership(Robot.elevator);
						log("Down");
					} else {
						context.takeOwnership(Robot.elevator);
						Robot.elevator.setElevatorPower(0);
						context.releaseOwnership(Robot.elevator);
						log("elevator stop");
					}
					
					if (m_controller.getButtonPressed(InputConstants.CONTROLLER_ARMS_SWITCH)) {
						context.takeOwnership(Robot.elevator);
						if (Robot.elevator.getArmsPower()) {
							Robot.elevator.setArmsPower(1.0);
						} else {
							Robot.elevator.setArmsPower(-1.0);
						}			
						Robot.elevator.setArmsPower(1);
						context.releaseOwnership(Robot.elevator);
					}

					if (m_controller.getButtonPressed(InputConstants.CONTROLLER_ELEVATOR_RESET_BUTTON)){
						//context.takeOwnership(Robot.shooter);
						log("Activated");
						context.startAsync(new ResetElevator());
					}

					if (m_controller.getButtonPressed(InputConstants.CONTROLLER_SHOOTER_POWER_TOGGLE)){
						log("Manual shooter toggle");
						double configPower = ConfigFileReader.getInstance().getDouble("shooter.velocity").get();
						if (Robot.shooter.getVelocity() < 0.1 * configPower) {
							Robot.shooter.setVelocity(configPower);
						} else {
							Robot.shooter.setVelocity(0);
						}
						log("Power set to:" + Robot.shooter.getVelocity());
						light.setColor("Maroon"); //color when we aren't ready to shoot
						startShoot = false;
					} 
					
					if (m_rightJoystick.getButtonPressed(InputConstants.JOYSTICK_TRIGGER)) {
						new StartBelts().run(context);
					} else if (m_rightJoystick.getButtonReleased(InputConstants.JOYSTICK_TRIGGER)){
						new StopBelts().run(context);
					}

					if (m_controller.getButtonPressed(InputConstants.CONTROLLER_AUTO_SHOOT)) {
						light.setColor("Maroon");
						double distance = Robot.limelight.limelightFilter(context);
						log("Autoshooting starting.");
						log("Calculated distance");
						if (distance >= 2.5 && distance <= 5){
							log("Auto Shooting");
							autopower = ShooterVelociltyUtil.computeVelocityForDistance(distance);
							Robot.shooter.setVelocity(autopower);
							if(autopower == 0.0) {
								log("out of range");
							} else {
								log("Velocity set to:" + autopower);
								startShoot = true;
							}
						}
					} 

					if (startShoot){
						if (Math.abs(Robot.shooter.getVelocity()-autopower) <= 30.0){
							startShoot = false;
							light.setColor("LimeGreen"); //color when we're ready to shoot
						}
					}

					context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
				}
		}
	}
}
