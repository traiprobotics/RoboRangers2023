package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class IO {

    public static final int DRIVE_JOYSTICK_PORT = 0;
    public static final int CONTROL_JOYSTICK_PORT = 1;

    public static final int LX_STICK_AXIS = 0;
    public static final int LY_STICK_AXIS = 1;
    public static final int RX_STICK_AXIS = 2;
    public static final int RY_STICK_AXIS = 3;

    public static final int L_TRIGGER_AXIS = 2;
    public static final int R_TRIGGER_AXIS = 3;

    public static final int PAD = 16;

    public static final int A_BUTTON = 2;
    public static final int B_BUTTON = 3;
    public static final int X_BUTTON = 1;
    public static final int Y_BUTTON = 4;
    public static final int LB_BUTTON = 5;
    public static final int RB_BUTTON = 6;
    public static final int BACK_BUTTON = 9;
    public static final int START_BUTTON = 8;
    public static final int L_STICK_BUTTON = 7;
    public static final int R_STICK_BUTTON = 10;
    public static final int L_TRIGGER_BUTTON = 11;
    public static final int R_TRIGGER_BUTTON = 12;

    public static Joystick driveJoystick;
    public static Joystick controlJoystick;

    public IO() {
        driveJoystick = new Joystick(DRIVE_JOYSTICK_PORT);
        controlJoystick = new Joystick(CONTROL_JOYSTICK_PORT);
    }

    public static int buttonPressed(Joystick pJoystick) {
        
        if(pJoystick.getRawButton(A_BUTTON)) {
            return A_BUTTON;
        } else if(pJoystick.getRawButton(B_BUTTON)) {
            return B_BUTTON;
        } else if(pJoystick.getRawButton(X_BUTTON)) {
            return X_BUTTON;
        } else if(pJoystick.getRawButton(Y_BUTTON)) {
            return Y_BUTTON;
        } else if (pJoystick.getRawButton(LB_BUTTON)) {
            return LB_BUTTON;
        } else if (pJoystick.getRawButton(RB_BUTTON)) {
            return RB_BUTTON;
        } else if (pJoystick.getRawButton(BACK_BUTTON)) {
            return BACK_BUTTON;
        } else if (pJoystick.getRawButton(START_BUTTON)) {
            return START_BUTTON;
        } else if (pJoystick.getRawButton(L_STICK_BUTTON)) {
            return L_STICK_BUTTON;
        } else if (pJoystick.getRawButton(R_STICK_BUTTON)) {
        return R_STICK_BUTTON;
        } else if (pJoystick.getRawAxis(L_TRIGGER_AXIS) > 0.1f) {
        return L_TRIGGER_BUTTON;
        } else if(pJoystick.getRawAxis(R_TRIGGER_AXIS) > 0.1f) {
        return R_TRIGGER_BUTTON;
        } else if(pJoystick.getPOV() > -1){
            return PAD;
        } else {
            return 0;
        } 
    } 

    public static void driveButtonsPressed() {//DRIVE CONTROLLER
        switch (buttonPressed(IO.driveJoystick)) {
            case A_BUTTON:
                Drivetrain.incrementBack();
                break;
            case B_BUTTON:
                Drivetrain.incrementRight();
                break;
            case X_BUTTON:
                Drivetrain.incrementLeft();
                break;
            case Y_BUTTON:
                Drivetrain.incrementForward();
                break;
            case RB_BUTTON:
            //Manipulator.setPosition(Manipulator.ARM_GROUND, Manipulator.INTAKE_AUTO_GROUND);
            //System.out.println("fuck this");
                break;
            case LB_BUTTON:
                Drivetrain.sprintMode(true);
                break;
            case BACK_BUTTON:
                // changeLayoutBackwards();
                break;
            case START_BUTTON:
                break;
         //   case L_TRIGGER
            default:
                Drivetrain.sprintMode(false);
                // Manipulator.armStop();
            break;
        }
    }


    public static void controlButtonsPressed() {//DRIVE CONTROLLER
        switch (buttonPressed(IO.controlJoystick)) {
            case A_BUTTON:
                Manipulator.setPosition(Manipulator.ARM_HOME, Manipulator.INTAKE_HOME);
                break;
            case B_BUTTON:
                Manipulator.setPosition(Manipulator.ARM_GROUND, Manipulator.INTAKE_GROUND);
                break;
            case X_BUTTON:
                Manipulator.setPosition(Manipulator.ARM_MIDDLE, Manipulator.INTAKE_MIDDLE);
                break;
            case Y_BUTTON:
                Manipulator.setPosition(Manipulator.ARM_HIGH, Manipulator.INTAKE_HIGH);
                break;
            case LB_BUTTON:
                Manipulator.gripperOpen();
                break;
            case RB_BUTTON:
                Manipulator.gripperClose();
                break;
            case START_BUTTON:
                break;
            case BACK_BUTTON:
                break;
            default:
                //Manipulator.intakeStop();
                Manipulator.gripperStop();
            break;
        }
    }
}