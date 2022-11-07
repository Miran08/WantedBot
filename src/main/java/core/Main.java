package core;

import enums.Character;
import org.jetbrains.annotations.NotNull;
import utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static utils.Constants.*;

public class Main {
    private static final BufferedImage GAME_OVER_SCREEN;

    static {
        try {
            GAME_OVER_SCREEN = RESOURCE_LOADER.loadImage("gameOver.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static int nullCombo = 0;
    private static BufferedImage screenCapture;

    public static void main(String[] args) {
        runMainLoop();
    }

    private static void runMainLoop() {
        while (true) {
            screenCapture = RobotManager.createScreenCapture(new Rectangle(LOWER_SCREEN_POS_X, LOWER_SCREEN_POS_Y, LOWER_SCREEN_WIDTH, LOWER_SCREEN_HEIGHT));
            checkGameOver();

            if (isCaptureBlack()) {
                Utils.wait(100);
                continue;
            }

            int rgb = getWantedColor();
            boolean found = false;
            if (rgb == -16777216) continue;

            for ( Character character : Character.values() ) {
                if (character.pixelColor == rgb) {
                    found = true;
                    findCharacter(character);
                    break;
                }
            }

            if (!found) {
                System.out.println("UNKNOWN RGB: " + rgb);
            }
        }
    }

    private static int getWantedColor() {
        BufferedImage upperScreen = RobotManager.createScreenCapture(new Rectangle(1920 / 2, 500, 1, 1));
        return upperScreen.getRGB(0, 0);
    }

    private static void checkGameOver() {
        if (ImageProcessor.getDifference(screenCapture, GAME_OVER_SCREEN, true) == 0) {
            System.out.println("GAME OVER!");
            System.exit(0);
        }
    }

    private static void findCharacter(@NotNull Character character) {
        int[] characterPos = ImageProcessor.locateColor(screenCapture, character.colorTester);

        if (nullCombo > 5 && characterPos == null) {
            characterPos = ImageProcessor.getClosestMatch(screenCapture, character.sample);
            nullCombo = 0;
        }

        if (characterPos != null) {
            nullCombo = 0;
            int x = characterPos[0] + LOWER_SCREEN_POS_X;
            int y = characterPos[1] + LOWER_SCREEN_POS_Y;

            RobotManager.moveMouse(x, y);
            RobotManager.clickMouse();
        } else {
            nullCombo++;
            Utils.wait(100);
        }
    }

    private static boolean isCaptureBlack() {
        for ( int x = 0; x < screenCapture.getWidth(); x++ ) {
            for ( int y = 0; y < screenCapture.getHeight(); y++ ) {
                if (screenCapture.getRGB(x, y) != -16777216 && screenCapture.getRGB(x, y) != -6334) {
                    return false;
                }
            }
        }
        return true;
    }

}
