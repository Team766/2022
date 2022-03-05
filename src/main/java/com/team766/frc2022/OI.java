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
			loggerCategory = Category.DRIVE;
			log("Elevator: " + Robot.elevator.getElevatorPosition() + " Arms: " + Robot.elevator.getArmsPower());
			log("Pitch: " + Robot.gyro.getGyroPitch() + " Yaw: " + Robot.gyro.getGyroYaw() + " Roll: " + Robot.gyro.getGyroRoll());
			log("Top: " + Robot.elevator.getElevatorTop() + " Bottom: " + Robot.elevator.getElevatorBottom());
			loggerCategory = Category.OPERATOR_INTERFACE;


			//Robot.drive.setArcadeDrivePower(m_joystick0.getAxis(1), m_joystick0.getAxis(0));
			Robot.elevator.setElevatorPower(-1 * m_joystick0.getAxis(1));
			if (m_joystick0.getButtonPressed(1)) {
				if (m_joystick0.getButtonPressed(7)) {
					Robot.elevator.setArmsPower(0);
				} else {
					Robot.elevator.setArmsPower(1);
				}
			} else if (m_joystick0.getButtonPressed(7)) {
				Robot.elevator.setArmsPower(-1);
			}
			/*if (m_joystick0.getButtonPressed(3) || m_joystick0.getButtonPressed(4) || m_joystick0.getButtonPressed(5) || m_joystick0.getButtonPressed(6)) {
				context.takeOwnership(Robot.elevator);
				Robot.elevator.setElevatorPower(/*m_joystick1.getAxis(1) + (m_joystick0.getButtonPressed(3)? 1 : 0) + (m_joystick0.getButtonPressed(4)? -1 : 0));
				Robot.elevator.setArmsPower(/*m_joystick1.getAxis(0) + (m_joystick0.getButtonPressed(5)? 1 : 0) + (m_joystick0.getButtonPressed(6)? -1 : 0));
				context.releaseOwnership(Robot.elevator);
				joystickControl = true;
			} else if (joystickControl) {
				joystickControl = false;
				context.takeOwnership(Robot.elevator);
				Robot.elevator.setElevatorPower(0);
				Robot.elevator.setArmsPower(0);
				context.releaseOwnership(Robot.elevator);
			}*/
			//if (climbingContext != null && !climbingContext.isDone())
			/*
			if (m_joystick0.getButtonPressed(1)) {
				log("Reset Elevator Position!");
				Robot.elevator.resetElevatorPosition();
			} 
			
			if (m_joystick0.getButtonPressed(2)) {
				log("DoubleClimbing!");
				/*climbingContext = 
				context.startAsync(new DoubleRung());
			}

			if (m_joystick0.getButtonPressed(7)) {
				log("Reset Gyro!");
				context.takeOwnership(Robot.gyro);
				Robot.gyro.resetGyro();
			}*/

			context.waitFor(() -> RobotProvider.instance.hasNewDriverStationData());
		}
	}
}
