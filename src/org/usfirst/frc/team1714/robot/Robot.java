package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.CANTalon; 
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive; 
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Victor;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	Joystick stick;
	AnalogPotentiometer pot;
	CANTalon talon1,talon2;
	DigitalInput LS1;
	Encoder encoder1,encoder2;
	RobotDrive drive;
	Ultrasonic VEXultrasonic;
	AnalogGyro gyro;
	Victor victorFrontLeft,victorRearLeft,victorFrontRight, victorRearRight;
	Timer timer;
	DigitalInput irSensor;
	PowerDistributionPanel pdp;
	boolean LSbeforeTrigger,LSTriggering,LSTriggered;
	double speed;
	PrintWriter currentWriter, timeWriter;

	public Robot() {
		stick= new Joystick(0);
		pot = new AnalogPotentiometer(0, 3600, 0);
		talon1=new CANTalon(1);
		talon2=new CANTalon(2);
		LS1=new DigitalInput(10); 
		irSensor = new DigitalInput(7);
		timer = new Timer();
		encoder1 = new Encoder(0,1);
		encoder2 = new Encoder(2,3); 
		VEXultrasonic = new Ultrasonic(9,8); 
		gyro = new AnalogGyro(1);
		victorFrontLeft = new Victor(2);
		victorRearRight = new Victor(0);
		victorRearLeft = new Victor(3);
		victorFrontRight = new Victor(1);
		drive=new RobotDrive(victorFrontLeft, victorRearLeft, victorFrontRight, victorRearRight);
		
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		VEXultrasonic.setAutomaticMode(true);
		//ultrasonic.setDistanceUnits(Ultrasonic.Unit.kInches);
		gyro.initGyro();
		gyro.calibrate();
		gyro.setSensitivity(0.001675);
		timer = new Timer();
		LSbeforeTrigger = true;
		encoder1.setDistancePerPulse(1);
		pdp = new PowerDistributionPanel();
		try{
			currentWriter = new PrintWriter("/home/lvuser/current.txt", "UTF-8");
			timeWriter = new PrintWriter("/home/lvuser/time.txt", "UTF-8");
		}
		catch(IOException e){
			System.out.println(e.toString());
			System.out.println("No good, slice");
		}
		timer.start();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}
	
	double encoderRate;
	double expectedEncoderRate = 350;
	double shootSpeedBuffer = 0.05;
	double shootSpeedIncrement = 0.01;
	double shootSpeed = 0;
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		if(pot.get() < 1500){
			currentWriter.println(pdp.getCurrent(14));
			timeWriter.println(Timer.getFPGATimestamp());
		}
		else
		{
			currentWriter.close();
			timeWriter.close();
		}
		
		SmartDashboard.putNumber("current: ", pdp.getCurrent(14));
		encoderRate = encoder1.getRate();
		if(encoderRate < (expectedEncoderRate - shootSpeedBuffer)){
			shootSpeed = (talon1.get() + shootSpeedIncrement);
			System.out.println("yoin up" + shootSpeed);
			//if the speed is slower than the speed we want, increase the speed till the speed is within buffer zone
		}
		else if(encoderRate > (expectedEncoderRate + shootSpeedBuffer)){
			shootSpeed = (talon1.get() - shootSpeedIncrement);
			System.out.println("yoin down" + shootSpeed);
			//if the speed is faster than the speed we want, decrease the speed till the speed is within buffer zone
		}
		if(LS1.get())
		{
			talon1.set(shootSpeed);
		}
		else
		{
			talon1.set(1);
		}
		
		//System.out.println(pot.get());
		//drive.arcadeDrive(stick);
		
		SmartDashboard.getBoolean("LS1", LS1.get());
		SmartDashboard.putNumber("Encoder1", encoder1.get());
		SmartDashboard.putNumber("Encoder2", encoder2.get());
		SmartDashboard.putNumber("Potentiometer", pot.get());
		
		SmartDashboard.putNumber("VEXUltrasonic distance", VEXultrasonic.getRangeInches());
		SmartDashboard.putNumber("Talon1", talon1.getOutputVoltage());
		SmartDashboard.putNumber("Talon1Current", talon1.getOutputCurrent());
		SmartDashboard.putNumber("Talon2Current", talon2.getOutputCurrent());
		SmartDashboard.putNumber("Talon2", talon2.getOutputVoltage());
		SmartDashboard.putNumber("Gyro Angles", gyro.getAngle());
		SmartDashboard.putNumber("Timer time", timer.get());
		SmartDashboard.putNumber("Talon1 speed", talon1.get());
		SmartDashboard.putBoolean("IR Sensed?", irSensor.get());
		
		//SmartDashboard.putNumber("Victor Motor speed in PWM value", victor.getSpeed());
		//SmartDashboard.putNumber("Motor2 speed in PWM value", talon2.getSpeed());
		
		
		if(!LS1.get() && LSbeforeTrigger){
			LSbeforeTrigger = false;
			LSTriggering = true;
		}
		else if(LS1.get() && LSTriggering){
			LSTriggered = true;
			LSTriggering = false;
			timer.start();
		}
		else if(!LS1.get() && LSTriggered){
			LSTriggered = false;
		}
		else if(LS1.get() && !LSTriggered && !LSbeforeTrigger){
			timer.stop();
			timer.reset();
			LSbeforeTrigger = true;
		}
		
		/*a
		if(timer.get() < 10.0 && timer.get() > 1.0){
			//victorFrontLeft.set(speed);
			//victorFrontRight.set(speed);
			//victorRearLeft.set(speed);
			//victorRearRight.set(speed);
			talon1.set(0.5);
		}
		else if(timer.get() > 10.0){
			//victorFrontLeft.set(-speed);
			//victorFrontRight.set(-speed);
			//victorRearLeft.set(-speed);
			//victorRearRight.set(-speed);
			talon1.set(-0.6);
		}*/
	
		
		
		
		
		//talon1.set(.5);
		//talon2.set(.5);
		//if(LS1.get()==false){
			//drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
			//drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
			//drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
			//drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);
		//	talon1.set(-1);
		//	talon2.set(-.25);
		//	gyro.reset();
		//	victor.set(0.5);
		//}
		//else
		//{
		//	talon1.set(0);
		//	talon2.set(0);
		//	victor.set(1);
		//}
		
	}
	

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	
	
	
		
		
		// TODO Auto-generated method stub
		
	}

}