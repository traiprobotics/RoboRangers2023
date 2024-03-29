// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kCubeBackUp = "Cube and Back Up";
  private static final String kCube = "Cube Only";
  private static float countAtPosition = 0;
  private static float counter = 0;
  private String m_autoSelected;
  int stage;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /*These are the classes for operations of the robot we want.
   * IO - Inputs from the controllers
   * Drivetrain - Methods for driving the robot
   */

   public static IO io = new IO();
   public static Drivetrain driveTrain = new Drivetrain();
   public static Manipulator manipulator = new Manipulator();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Cube and Back Up", kCubeBackUp);
    m_chooser.addOption("Cube Only", kCube);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    Manipulator.setPosition(Manipulator.ARM_MIDDLE, Manipulator.INTAKE_HIGH);
    countAtPosition = 0;
    counter = 0;
    stage = 1;
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCube:
        switch (stage) {
          //32 inches away from grid
          case 1:
          System.out.println("Switch cases");
          if (Manipulator.atPosition()){
            stage ++; counter = 0;}
          break;
          case 2:
          Manipulator.gripperClose();
          Drivetrain.driveArcade(0f, -0.3f);
          if (counter > 10) {
            Manipulator.gripperStop();
          }
          if (counter > 80) {
            stage ++; counter = 0;}
          break;
          case 3:
          Drivetrain.stop();
          Manipulator.gripperOpen();
          if (counter > 10) {
            stage ++; counter = 0;
          }
          break; 
          case 4:
          manipulator.gripperStop();
          Drivetrain.driveArcade(0f, 0.3f);
          if (counter > 20) {
            manipulator.gripperClose();
          }
          if (counter > 50) {
            manipulator.gripperStop();
          }
          if (counter > 50) {
            manipulator.setPosition(Manipulator.ARM_HOME,Manipulator.INTAKE_HOME);
          }
          if (counter > 80) {
            stage ++; counter = 0;
          }
          break;
          case 5:
          Drivetrain.stop();
          counter = 0;
          break;
          }
        

        break;
      case kCubeBackUp:
      switch (stage) {
        //32 inches away from grid
        case 1:
        System.out.println("Switch cases");
        if (Manipulator.atPosition()){
          stage ++; counter = 0;}
        break;
        case 2:
        Drivetrain.driveArcade(0f, -0.3f);
        if (counter > 80) {
          stage ++; counter = 0;}
        break;
        case 3:
        Drivetrain.stop();
        Manipulator.gripperOpen();
        if (counter > 10) {
          stage ++; counter = 0;
        }
        break; 
        case 4:
        manipulator.gripperStop();
        Drivetrain.driveArcade(0f, 0.65f);
        if (counter > 20) {
          manipulator.gripperClose();
        }
        if (counter > 50) {
          manipulator.gripperStop();
        }
        if (counter > 30) {
          manipulator.setPosition(Manipulator.ARM_HOME,Manipulator.INTAKE_HOME);
        }
        if (counter > 80) {
          stage ++; counter = 0;
        }
        break;
        case 5:
        Drivetrain.stop();
        Drivetrain.driveArcade(0.65f, 0f);
        if (counter > 47) {
          Drivetrain.stop();
          Manipulator.setPosition(Manipulator.ARM_GROUND, manipulator.INTAKE_AUTO_GROUND);
          stage ++; counter = 0;
        }
        break;
        case 6:
        Manipulator.gripperOpen();
        if (counter > 30) {
          Manipulator.gripperClose();
          stage ++; counter = 0;
        }

        break;
        case 7:
        counter = 0;
        break;
        }


      default:
        // Put default auto code here
        break;
    }
    counter ++;
    Manipulator.driveArmToPosition();
    Manipulator.driveIntakeToPosition();
   // System.out.println(counter);
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. 
   * 
   * When Mini Myles starts up, he will CRAWL!!!
  */
  @Override
  public void teleopPeriodic() {
    
    //DRIVE
    //Drivetrain.tankDriveWithJoystick(); //Drive the robot yo.
    Drivetrain.driveArcadeWithJoystick();
    IO.driveButtonsPressed(); //Read in IO (driver joystick) and perform actions here.

    //MANIPULATOR
    IO.controlButtonsPressed(); //Read in IO (control joystick) and perform actions here.
    Manipulator.controlManipulator(); //Use the control functions and setpoints to drive motors.
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
