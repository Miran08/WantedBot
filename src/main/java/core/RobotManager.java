package core;

import utils.Utils;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;


public class RobotManager {

    private RobotManager() {
    }

    private static final java.awt.Robot ROBOT;
    private static final int LEFT_BUTTON_MASK = InputEvent.BUTTON1_DOWN_MASK;

    static {
        try {
            ROBOT = new java.awt.Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public static void moveMouse(int x, int y) {
        ROBOT.mouseMove(x, y);
    }

    public static void clickMouse() {
        ROBOT.mousePress(LEFT_BUTTON_MASK);
        Utils.wait(100);
        ROBOT.mouseRelease(LEFT_BUTTON_MASK);
    }

    public static BufferedImage createScreenCapture(Rectangle rectangle) {
        return ROBOT.createScreenCapture(rectangle);
    }

}
