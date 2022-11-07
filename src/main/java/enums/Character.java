package enums;

import finders.LuigiTester;
import finders.MarioTester;
import finders.WarioTester;
import finders.YoshiTester;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.Predicate;

import static utils.Constants.RESOURCE_LOADER;

public enum Character {
    MARIO("characters/mario.png", new MarioTester(), -744067),
    WARIO("characters/wario.png", new WarioTester(), -3421500),
    LUIGI("characters/luigi.png", new LuigiTester(), -3108504),
    YOSHI("characters/yoshi.png", new YoshiTester(), -14120408);

    public final BufferedImage sample;
    public final Predicate<Color> colorTester;
    public final int pixelColor;

    Character(String samplePath, Predicate<Color> colorTester, int pixelColor) {
        try {
            sample = RESOURCE_LOADER.loadImage(samplePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.colorTester = colorTester;
        this.pixelColor = pixelColor;
    }


}
