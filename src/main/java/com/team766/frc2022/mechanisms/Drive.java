package com.team766.frc2022.mechanisms;
import com.team766.framework.Mechanism;
import com.team766.hal.EncoderReader;
import com.team766.hal.RobotProvider;
import com.team766.hal.SpeedController;
import com.team766.hal.CANSpeedController.ControlMode;
import com.team766.hal.mock.Joystick;
import com.team766.hal.simulator.Encoder;
import com.team766.hal.CANSpeedController;
import com.team766.library.ValueProvider;
import com.team766.logging.Category;
import com.team766.config.ConfigFileReader;
import com.team766.framework.Mechanism;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;


public class Drive extends Mechanism {
	public final ValueProvider<Double> LENGTH_OF_CHASSIS = ConfigFileReader.getDouble("LENGTH_OF_CHASSIS");
	public final ValueProvider<Double> WIDTH_OF_CHASSIS = ConfigFileReader.getDouble("WIDTH_OF_CHASSIS");

	private CANSpeedController m_DriveFrontRight;
    private CANSpeedController m_DriveFrontLeft;
	private CANSpeedController m_DriveBackRight;
	private CANSpeedController m_DriveBackLeft;

    private CANSpeedController m_SteerFrontRight;
    private CANSpeedController m_SteerFrontLeft;
	private CANSpeedController m_SteerBackRight;
	private CANSpeedController m_SteerBackLeft;

	private CANCoder e_FrontRight;
	private CANCoder e_FrontLeft;
	private CANCoder e_BackRight;
	private CANCoder e_BackLeft;
    
	private ValueProvider<Double> drivePower;

	// Values for PID Driving Straight
	public double min_drive = -12;
	public double max_drive = 12;

	// Values for PID turning
	public double min_turn = -12;
	public double max_turn = 12;

    public Drive() {
		loggerCategory = Category.DRIVE;
        // Initializations of motors
		//Initialize the drive motors
        m_DriveFrontRight = RobotProvider.instance.getCANMotor("drive.DriveFrontRight"); 
		m_DriveFrontLeft = RobotProvider.instance.getCANMotor("drive.DriveFrontLeft"); 
		m_DriveBackRight = RobotProvider.instance.getCANMotor("drive.DriveBackRight"); 
		m_DriveBackLeft = RobotProvider.instance.getCANMotor("drive.DriveBackLeft"); 
		//Initialize the steering motors
		m_SteerFrontRight = RobotProvider.instance.getCANMotor("drive.SteerFrontRight"); 
		m_SteerFrontLeft = RobotProvider.instance.getCANMotor("drive.SteerFrontLeft"); 
		m_SteerBackRight = RobotProvider.instance.getCANMotor("drive.SteerBackRight"); 
		m_SteerBackLeft = RobotProvider.instance.getCANMotor("drive.SteerBackLeft");

		//Setting up the "config" 
		CANCoderConfiguration config = new CANCoderConfiguration();
		config.absoluteSensorRange = AbsoluteSensorRange.Signed_PlusMinus180;
		//The encoders output "encoder" values, so we need to convert that to degrees (because that is what the cool kids are using)
		config.sensorCoefficient = 360.0 / 4096.0;
		//The offset is going to be changed in ctre, but we can change it here too.
		//config.magnetOffsetDegrees = Math.toDegrees(configuration.getOffset());
		config.sensorDirection = true;

		//initialize the encoders
		e_FrontRight = new CANCoder(1);
		e_FrontRight.configAllSettings(config, 250);
		e_FrontLeft = new CANCoder(2);
		e_FrontLeft.configAllSettings(config, 250);
		e_BackRight = new CANCoder(4);
		e_BackRight.configAllSettings(config, 250);
		e_BackLeft = new CANCoder(3);
		e_BackLeft.configAllSettings(config, 250);
 
		
		//Current limit for motors to avoid breaker problems (mostly to avoid getting electrical people to yell at us)
		m_DriveFrontRight.setCurrentLimit(20);
		m_DriveFrontLeft.setCurrentLimit(20);
		m_DriveBackRight.setCurrentLimit(20);
		m_DriveBackLeft.setCurrentLimit(20);

		m_SteerFrontRight.setCurrentLimit(20);
		m_SteerFrontLeft.setCurrentLimit(20);
		m_SteerBackRight.setCurrentLimit(20);
		m_SteerBackLeft.setCurrentLimit(20);

		//Setting up the connection between steering motors and cancoders
		m_SteerFrontRight.setRemoteFeedbackSensor(e_FrontRight, 0);
		m_SteerFrontLeft.setRemoteFeedbackSensor(e_FrontLeft, 0);
		m_SteerBackRight.setRemoteFeedbackSensor(e_BackRight, 0);
		m_SteerBackLeft.setRemoteFeedbackSensor(e_BackLeft, 0);

		m_SteerFrontRight.setSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0);
		m_SteerFrontLeft.setSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0);
		m_SteerBackRight.setSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0);	
		m_SteerBackLeft.setSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0);
	}
	//If you want me to repeat code, then no.
	public double pythagrian(double x, double y) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	public double getAngle(double LR, double FB){
		return Math.toDegrees(Math.atan2(LR ,-FB));
	}

	//This is the method that is called to drive the robot in the 2D plane
    public void drive2D(double JoystickX, double JoystickY) {
		checkContextOwnership();
		logs();
		configPID();
		double power = pythagrian(JoystickX, JoystickY);
		double angle = getAngle(JoystickX, JoystickY);
		//Temporary Drive code, kinda sucks
		m_DriveFrontRight.set(power);
		m_DriveFrontLeft.set(power);
		m_DriveBackRight.set(power);
		m_DriveBackLeft.set(power);


		//Steer code
		m_SteerFrontLeft.set(ControlMode.Position, angle);
		m_SteerFrontRight.set(ControlMode.Position, angle);
		m_SteerBackLeft.set(ControlMode.Position, angle);
		m_SteerBackRight.set(ControlMode.Position, angle);
    }

	public void swerveDrive(double JoystickX, double JoystickY, double JoystickTheta){
		checkContextOwnership();
		configPID();
		double radius = Math.sqrt(Math.pow(LENGTH_OF_CHASSIS.get(), 2) + Math.pow(WIDTH_OF_CHASSIS.get(), 2));
		double a = JoystickX - JoystickTheta * (LENGTH_OF_CHASSIS.get() / radius);
		double b = JoystickX + JoystickTheta * (LENGTH_OF_CHASSIS.get() / radius);
		double c = JoystickY - JoystickTheta * (WIDTH_OF_CHASSIS.get() / radius);
		double d = JoystickY + JoystickTheta * (WIDTH_OF_CHASSIS.get() / radius);
		m_DriveFrontRight.set(pythagrian(b, d));
		m_DriveFrontLeft.set(pythagrian(b, c));
		m_DriveBackRight.set(pythagrian(a, d));
		m_DriveBackLeft.set(pythagrian(a, c));

		double angle = getAngle(JoystickX, JoystickY);
		m_SteerFrontLeft.set(ControlMode.Position, angle);
		m_SteerFrontRight.set(ControlMode.Position, angle);
		m_SteerBackLeft.set(ControlMode.Position, angle);
		m_SteerBackRight.set(ControlMode.Position, angle);
	}
	//Logging the encoder values (also I love Github Copilot <3)
	public void logs(){
		log("Front Right Encoder: " + e_FrontRight.getPosition() + " Front Left Encoder: " + e_FrontLeft.getPosition() + " Back Right Encoder: " + e_BackRight.getPosition() + " Back Left Encoder: " + e_BackLeft.getPosition());
	}

	//To control each steering individually with a PID
	public void setFrontRightAngle(double angle){
		configPID();
		log("Angle: " + getFrontRight() + " || Motor angle: " + m_SteerFrontRight.getSensorPosition());
		m_SteerFrontRight.set(ControlMode.Position, angle);
	}
	public void setFrontLeftAngle(double angle){
		configPID();
		log("Angle: " + getFrontLeft() + " || Motor angle: " + m_SteerFrontLeft.getSensorPosition());
		m_SteerFrontLeft.set(ControlMode.Position, angle);
	}
	public void setBackRightAngle(double angle){
		configPID();
		log("Angle: " + getBackRight() + " || Motor angle: " + m_SteerBackRight.getSensorPosition());
		m_SteerBackRight.set(ControlMode.Position, angle);
	}
	public void setBackLeftAngle(double angle){
		configPID();
		log("Angle: " + getBackLeft() + " || Motor angle: " + m_SteerBackLeft.getSensorPosition());
		m_SteerBackLeft.set(ControlMode.Position, angle);
	}
	
	public void configPID(){
		//PID for turning the various steering motors. Here is a good link to a tuning website: https://www.robotsforroboticists.com/pid-control/
		m_SteerFrontRight.setP(0);
		m_SteerFrontRight.setI(0);
		m_SteerFrontRight.setD(0);
		m_SteerFrontRight.setFF(0);

		m_SteerFrontLeft.setP(10);
		m_SteerFrontLeft.setI(0);
		m_SteerFrontLeft.setD(0);
		m_SteerFrontLeft.setFF(0);
		
		m_SteerBackRight.setP(0);
		m_SteerBackRight.setI(0);
		m_SteerBackRight.setD(0);
		m_SteerBackRight.setFF(0);

		m_SteerBackLeft.setP(0);
		m_SteerBackLeft.setI(0);
		m_SteerBackLeft.setD(0);
		m_SteerBackLeft.setFF(0);

		//pid values from sds for Flacons 500: P = 0.2 I = 0.0 D = 0.1 FF = 0.0

		//IDK what those do tbh, but I like to keep them here.
		//m_SteerFrontRight.setSensorInverted(false);
		//m_SteerFrontLeft.setSensorInverted(false);
		//m_SteerBackRight.setSensorInverted(false);
		//m_SteerBackLeft.setSensorInverted(false);
	}

	//Method to get the encoder values, the encoders are in degrees from -180 to 180. To change that, we need to change the syntax and use getPosition()
	public double getFrontRight(){
		return e_FrontRight.getAbsolutePosition();
	}
	public double getFrontLeft(){
		return e_FrontLeft.getAbsolutePosition();
	}
	public double getBackRight(){
		return e_BackRight.getAbsolutePosition();
	}
	public double getBackLeft(){
		return e_FrontRight.getAbsolutePosition();
	}
}

//AS