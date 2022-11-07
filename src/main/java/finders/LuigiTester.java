package finders;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Predicate;

public class LuigiTester implements Predicate<Color> {
    @Override
    public boolean test(@NotNull Color color) {
        return color.getRed() - color.getBlue() > 5 && color.getGreen() > 150 && color.getRed() < 110 && color.getBlue() < 110;
    }
}
