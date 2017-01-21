package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Timer;
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
	AnalogInput ezSonic;
	DigitalOutput ezSonicPower;
	int ezSonicValue;
	
	public Robot() {
		stick= new Joystick(0);
		pot = new AnalogPotentiometer(0, 3600, 0);
		talon1=new CANTalon(1);
		talon2=new CANTalon(2);
		LS1=new DigitalInput(4); 
		//drive=new RobotDrive(talon1,talon2);
		encoder1 = new Encoder(0,1);
		encoder2 = new Encoder(2,3); 
		VEXultrasonic = new Ultrasonic(9,8); 
		ezSonic = new AnalogInput(1);
		ezSonicPower = new DigitalOutput(6);
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

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//System.out.println(pot.get());
		//drive.arcadeDrive(stick);
		SmartDashboard.putBoolean("LS1", LS1.get());
		SmartDashboard.putNumber("Encoder1", encoder1.get());
		SmartDashboard.putNumber("Encoder2", encoder2.get());
		SmartDashboard.putNumber("Potentiometer", pot.get());
		
		SmartDashboard.putNumber("VEXUltrasonic distance", VEXultrasonic.getRangeInches());
		SmartDashboard.putNumber("Talon1", talon1.getOutputVoltage());
		SmartDashboard.putNumber("Talon1Current", talon1.getOutputCurrent());
		SmartDashboard.putNumber("Talon2Current", talon2.getOutputCurrent());
		SmartDashboard.putNumber("Talon2", talon2.getOutputVoltage());
		
		
		//talon1.set(.5);
		//talon2.set(.5);
		if(LS1.get()==false){
			//drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
			//drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
			//drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
			//drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);
			talon1.set(-1);
			talon2.set(-.25);
		}
		else
		{
			talon1.set(0);
			talon2.set(0);
		}
		
		ezSonicPower.set(true);
		ezSonicValue = (int)(ezSonic.getValue() / 0.976);
		SmartDashboard.putNumber("REAL Ultrasonic distance", ezSonicValue);
	}
	

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	
	
	
		
		
		// TODO Auto-generated method stub
		
	}

}