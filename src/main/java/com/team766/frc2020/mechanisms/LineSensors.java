package com.team766.frc2020.mechanisms;

import com.team766.framework.Mechanism;
import com.team766.hal.DigitalInputReader;
import com.team766.hal.RobotProvider;

public class LineSensors extends Mechanism {
	private DigitalInputReader m_lineSensorLeft;
	private DigitalInputReader m_lineSensorCenter;
	private DigitalInputReader m_lineSensorRight;

	public LineSensors() {
		m_lineSensorLeft = RobotProvider.instance.getDigitalInput("lineSensorLeft");
		m_lineSensorCenter = RobotProvider.instance.getDigitalInput("lineSensorCenter");
		m_lineSensorRight = RobotProvider.instance.getDigitalInput("lineSensorRight");
	}

	public boolean getLineSensorLeft() {
		return m_lineSensorLeft.get();
	}

	public boolean getLineSensorCenter() {
		return m_lineSensorCenter.get();
	}

	public boolean getLineSensorRight() {
		return m_lineSensorRight.get();
	}
}