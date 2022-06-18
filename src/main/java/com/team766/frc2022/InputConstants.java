package com.team766.frc2022;

/**
 * Constants used for the Operator Interface, eg for joyticks, buttons, dials, etc.
 * 
 * TODO: consider moving this into a config file.
 */
public final class InputConstants {

	// "joysticks"
	public static final int LEFT_JOYSTICK = 0;
	public static final int RIGHT_JOYSTICK = 1;
	public static final int CONTROL_PANEL = 2;

	// navigation
	public static final int AXIS_LEFT_RIGHT = 0;
	public static final int AXIS_FORWARD_BACKWARD = 1;

	
	// other ops
	public static final int AXIS_SHOOTER_DIAL = 3;

	public static final int CONTROL_PANEL_SHOOTER_SWITCH = 1;
	public static final int CONTROL_PANEL_INTAKE_BUTTON = 2;
	
	public static final int CONTROL_PANEL_BELTS_BUTTON = 3;

	public static final int CONTROL_PANEL_SPITBALL_BUTTON = 14;

	// TODO: update these
	public static final int CONTROL_PANEL_AUTOCLIMB_BUTTON = 9;
	public static final int CONTROL_PANEL_ARMS_SWITCH = 13;
	public static final int CONTROL_PANEL_ELEVATOR_UP_BUTTON = 7;
	public static final int CONTROL_PANEL_ELEVATOR_DOWN_BUTTON = 8;
	public static final int CONTROL_PANEL_ELEVATOR_TOP_BUTTON = 5;
	public static final int CONTROL_PANEL_ELEVATOR_BOTTOM_BUTTON = 6;
	public static final int CONTROL_PANEL_AUTO_SHOOT = 10;
	public static final int JOYSTICK_TRIGGER = 1;
	public static final int E_STOP = 11;
	public static final int JOYSTICK_ELEVATOR_RESET_BUTTON = 2; //Joystick 2, left
	public static final int JOYSTICK_CLIMB_RUNG_BUTTON = 4; //Joystick 2, left
	public static final int JOYSTICK_CLIMB_FIRST_RUNG_BUTTON = 5; //joystick 2, left

	// single-controller inputs (Make sure controller is on X Mode, not D mode)

	public static final int CONTROLLER = 0;

	public static final int CONTROLLER_AXIS_LEFT_HORIZONTAL = 0;
	public static final int CONTROLLER_AXIS_LEFT_VERTICAL = 1;
	public static final int CONTROLLER_AXIS_RIGHT_HORIZONTAL = 4;
	public static final int CONTROLLER_AXIS_RIGHT_VERTICAL = 5;

	public static final int CONTROLLER_SHOOTER_POWER_TOGGLE = 6;
	public static final int CONTROLLER_ARMS_SWITCH = 4;
	public static final int CONTROLLER_AUTO_SHOOT = 1;
	public static final int CONTROLLER_E_STOP = 8;
	public static final int CONTROLLER_ELEVATOR_RESET_BUTTON = 7;

	public static final int CONTROLLER_AXIS_BELT_BACKWARDS = 2;
	public static final int CONTROLLER_AXIS_BELT_FORWARDS = 3;
}