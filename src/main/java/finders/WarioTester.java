package finders;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Predicate;

public class WarioTester implements Predicate<Color> {
    @Override
    public boolean test(@NotNull Color color) {
        return color.getRed() > 150 && color.getGreen() > 150 && color.getBlue() < 50;
    }
}
