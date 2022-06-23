package com.team766.frc2022;

import com.team766.framework.Loggable;
import java.lang.Math;

public class Point extends Loggable {
	private double x;
	private double y;

	public String getName() {
		return "Points";
	}

	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double distance(Point a) {
		return Math.sqrt(Math.pow(a.getX() - getX(), 2.0) + Math.pow(a.getY() - getY(), 2.0));
	}

	public double slope(Point a) {
		double s;
		final int MAX = 1000; 
		if (a.getX() == getX()) {
			//If the points are on top of each other, returns positive or negative MAX.
			if (a.getY() < getY()) {
				s = -MAX;
			} else {
				s = MAX;
			}
		} else {
			s = (getY() - a.getY()) / (getX() - a.getX());
		}
		if (Math.abs(s) > MAX) {
			s = Math.signum(s) * MAX;
		}
		return s;
	}

	//Gets a unit vector between the point and another point. Used for swerve drive method input.
	public Point getUnitVector(Point inputPoint) {
		double d = distance(inputPoint);
		return new Point((inputPoint.getX() - getX()) / d, (inputPoint.getY() - getY()) / d);
	}

	public String toString() {
		return "X: " + getX() + " Y: " + getY();
	}
}