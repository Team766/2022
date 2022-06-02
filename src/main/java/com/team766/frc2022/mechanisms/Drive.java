package com.team766.frc2022.mechanisms;
import com.team766.framework.Mechanism;
import com.team766.hal.EncoderReader;
import com.team766.hal.RobotProvider;
import com.team766.hal.SpeedController;
import com.team766.hal.CANSpeedController.ControlMode;
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

	private CANSpeedController m_DriveFrontRight;
    private CANSpeedController m_DriveFrontLeft;
	private CANSpeedController m_DriveBackRight;
	private CANSpeedController m_DriveBackLeft;
    private CANSpeedController m_SteerFrontRight;
    private CANSpeedController m_SteerFrontLeft;
	private CANSpeedController m_SteerBackRight;
	private CANSpeedController m_SteerBackLeft;
	private CANCoder BLencoder;
	private CANCoder FLencoder;
	private CANCoder BRencoder;
	private CANCoder FRencoder;
    private ValueProvider<Double> drivePower;

	// Values for PID Driving Straight
	public double min_drive = -12;
	public double max_drive = 12;

	// Values for PID turning
	public double min_turn = -12;
	public double max_turn = 12;

    public Drive() {
		loggerCategory = Category.DRIVE;
        // Initializations
        m_DriveFrontRight = RobotProvider.instance.getCANMotor("drive.DriveFrontRight"); 
		m_DriveFrontLeft = RobotProvider.instance.getCANMotor("drive.DriveFrontLeft"); 
		m_DriveBackRight = RobotProvider.instance.getCANMotor("drive.DriveBackRight"); 
		m_DriveBackLeft = RobotProvider.instance.getCANMotor("drive.DriveBackLeft"); 
		m_SteerFrontRight = RobotProvider.instance.getCANMotor("drive.SteerFrontRight"); 
		m_SteerFrontLeft = RobotProvider.instance.getCANMotor("drive.SteerFrontLeft"); 
		m_SteerBackRight = RobotProvider.instance.getCANMotor("drive.SteerBackRight"); 
		m_SteerBackLeft = RobotProvider.instance.getCANMotor("drive.SteerBackLeft"); 

		BLencoder = new CANCoder(3);
		FLencoder = new CANCoder(2);
		CANCoderConfiguration config = new CANCoderConfiguration();
		config.absoluteSensorRange = AbsoluteSensorRange.Signed_PlusMinus180;
		//config.magnetOffsetDegrees = Math.toDegrees(configuration.getOffset());
		config.sensorDirection = true;
		FLencoder.configAllSettings(config, 250);


		BRencoder = new CANCoder(4);
		FRencoder = new CANCoder(1);
		
		m_DriveFrontRight.setCurrentLimit(20);
		m_DriveFrontLeft.setCurrentLimit(20);
		m_DriveBackRight.setCurrentLimit(20);
		m_DriveBackLeft.setCurrentLimit(20);
		m_SteerFrontRight.setCurrentLimit(20);
		m_SteerFrontLeft.setCurrentLimit(20);
		m_SteerBackRight.setCurrentLimit(20);
		m_SteerBackLeft.setCurrentLimit(20);



		//Setting up the connection between steer and cancoders
		m_SteerFrontLeft.setRemoteFeedbackSensor(FLencoder, 0);
		m_SteerFrontRight.setRemoteFeedbackSensor(FRencoder, 0);
		m_SteerBackLeft.setRemoteFeedbackSensor(BLencoder, 0);
		m_SteerBackRight.setRemoteFeedbackSensor(BRencoder, 0);

		m_SteerFrontLeft.setSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0);
		m_SteerFrontRight.setSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0);
		m_SteerBackLeft.setSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0);
		m_SteerBackRight.setSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0);	
	}

    public void setDrivePower(double leftPower, double rightPower) {
		checkContextOwnership();
		log("Front left encoder: "+ getFLencoder() + " || Back left encoder: " + getBLencoder() + " || Front right encoder: " + getFRencoder() + " || Back right encoder: " +getBRencoder());
    }

	public void setFLAngle(double angle){
		configPIDFL();
		log("Angle: " + getFLencoder() + " || Motor angle: " + m_SteerFrontLeft.getSensorPosition());
		m_SteerFrontLeft.set(ControlMode.Position, angle);
	}
	public void configPIDFL(){
		m_SteerFrontLeft.setP(10  );
	}
	public double getBLencoder(){
		return BLencoder.getAbsolutePosition();
	}
	public double getFLencoder(){
		return FLencoder.getAbsolutePosition();
	}
	public double getBRencoder(){
		return BRencoder.getAbsolutePosition();
	}
	public double getFRencoder(){
		return FRencoder.getAbsolutePosition();
	}
}
