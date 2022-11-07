package finders;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Predicate;

public class MarioTester implements Predicate<Color> {

    @Override
    public boolean test(@NotNull Color color) {
        return color.getRed() > 200 && color.getGreen() < 80 && color.getBlue() < 80;
    }
}
