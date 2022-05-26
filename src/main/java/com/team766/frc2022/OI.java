package com.team766.frc2022;

import com.team766.framework.Procedure;
import com.team766.framework.Context;
import com.team766.frc2022.Robot;
<<<<<<< Updated upstream
import com.team766.frc2022.procedures.*;
=======
//import com.team766.frc2022.procedures.*;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
		while (true) {
			// Add driver controls here - make sure to take/release ownership
			// of mechanisms when appropriate.
			

=======
		int index = 0; // index counter for the intake
		double autopower = 0; // power given during auto shooting
		boolean startShoot = false; //is it staring to shoot
		double prev_time = RobotProvider.instance.getClock().getTime();

		context.takeOwnership(Robot.drive);
		
		//LaunchedContext climbingContext = null;
		while (true) {
			// TODO: tweak all of this based on actual revB controls
			Robot.drive.setDrivePower(
				-m_leftJoystick.getAxis(InputConstants.AXIS_FORWARD_BACKWARD), 
				m_rightJoystick.getAxis(InputConstants.AXIS_LEFT_RIGHT));
			double cur_time = RobotProvider.instance.getClock().getTime();
			// if (cur_time-prev_time >= 0.5){
			// 	/* log("Elevator: " + Robot.elevator.getElevatorPosition() + " Arms: " + Robot.elevator.getArmsPower());
			// 	 log("Pitch: " + Robot.gyro.getGyroPitch() + " Yaw: " + Robot.gyro.getGyroYaw() + " Roll: " + Robot.gyro.getGyroRoll());
			// 	 log("Top: " + Robot.elevator.getLimitSwitchTop() + " Bottom: " + Robot.elevator.getLimitSwitchBottom());
			// 	 log("Top switch: " + Boolean.toString(Robot.elevator.getLimitSwitchTop()) + " Bottom switch:" + Boolean.toString(Robot.elevator.getLimitSwitchBottom()));*/
			// 	log("Velocity: "+Robot.shooter.getVelocity());
			// 	//log("Distance: "+Robot.limelight.distanceFromTarget());
			// 	prev_time = cur_time;
			// }

			// if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ELEVATOR_TOP_BUTTON)) {				
			// 	context.startAsync(new ExtendElevator());
			// 	log("Elevator to Top");
			// }
			// if (m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_ELEVATOR_BOTTOM_BUTTON)) {
			// 	context.startAsync(new RetractElevator());
			// 	log("Elevator to Bottom");	
			// }

			//1 pushes the arms forward, -1 pushes the arms backwards
			

			// if(m_ControlPanel.getButtonPressed(InputConstants.CONTROL_PANEL_AUTOCLIMB_BUTTON)){
			// 	context.startAsync(new Climb());
			// }

			
			// if(m_leftJoystick.getButtonPressed(InputConstants.JOYSTICK_CLIMB_RUNG_BUTTON)){
			// 	context.startAsync(new ClimbRung());
			// }

			// if(m_leftJoystick.getButtonPressed(InputConstants.JOYSTICK_CLIMB_FIRST_RUNG_BUTTON)){
			// 	context.startAsync(new ClimbFirstRung());
			// }
			// 
			//log(""+Robot.elevator.getElevatorPosition());
>>>>>>> Stashed changes
			context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
		}
	}
}
