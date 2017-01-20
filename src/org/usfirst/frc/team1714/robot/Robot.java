package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.CANTalon; 
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive; 
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
	DigitalInput Noot;
	Encoder FUUT,SUUT;
	RobotDrive DRUUT;
	
	public Robot() {
		stick= new Joystick(0);
		pot = new AnalogPotentiometer(0, 3600, 0);
		talon1=new CANTalon(1);
		talon2=new CANTalon(2);
		Noot=new DigitalInput(4); 
		//DRUUT=new RobotDrive(talon1,talon2);
		FUUT=new Encoder(0,1);
		SUUT=new Encoder(2,3); 
		
		
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
		//DRUUT.arcadeDrive(stick);
		SmartDashboard.putBoolean("NOOT", Noot.get());
		SmartDashboard.putNumber("Encoder1", FUUT.get());
		SmartDashboard.putNumber("Encoder2", SUUT.get());
		SmartDashboard.putNumber("Potentiometer", pot.get());
		talon1.set(.5);
		talon2.set(.5);
		if(Noot.get()==false){
			//DRUUT.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
			//DRUUT.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
			//DRUUT.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
			//DRUUT.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);
			talon1.set(-.5);
			talon2.set(-.5);
		}
	
	}
	

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	
	
	
		
		
		// TODO Auto-generated method stub
		
	}

}