package finders;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Predicate;

public class YoshiTester implements Predicate<Color> {
    @Override
    public boolean test(@NotNull Color color) {
        return color.getRed() == color.getBlue() && color.getGreen() > 200 && color.getRed() < 100 && color.getBlue() < 100;
    }
}
