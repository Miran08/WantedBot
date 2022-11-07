package core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class ImageProcessor {

    private ImageProcessor() {
    }

    public static int @Nullable [] locateColor(@NotNull BufferedImage source, @NotNull Predicate<Color> colorTester) {

        for ( int x = 0; x < source.getWidth(); x++ ) {
            for ( int y = 0; y < source.getHeight(); y++ ) {
                Color color = new Color(source.getRGB(x, y));

                if (colorTester.test(color)) {
                    return new int[]{x, y};
                }
            }
        }

        return null;
    }

    public static float getDifference(@NotNull BufferedImage img1, @NotNull BufferedImage img2, boolean ignoreAlpha) {
        int width = img1.getWidth();
        int height = img1.getHeight();

        if (width != img2.getWidth() || height != img2.getHeight()) {
            throw new IllegalArgumentException("images should have the same size");
        }
        int difference = 0;
        for ( int x = 0; x < width; x++ ) {
            for ( int y = 0; y < height; y++ ) {
                Color color1 = new Color(img1.getRGB(x, y), true);

                if (ignoreAlpha && color1.getAlpha() == 0) continue;
                int r1 = color1.getRed();
                int g1 = color1.getGreen();
                int b1 = color1.getBlue();

                Color color2 = new Color(img2.getRGB(x, y), true);
                if (ignoreAlpha && color2.getAlpha() == 0) continue;
                int r2 = color2.getRed();
                int g2 = color2.getGreen();
                int b2 = color2.getBlue();
                difference += Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
            }
        }

        float average = difference / (width * height * 3f);

        return average / 255;
    }

    public static int[] getClosestMatch(@NotNull BufferedImage source, BufferedImage image) {
        HashMap<Integer, ArrayList<int[]>> pixels = new HashMap<>();

        for ( int x = 0; x < source.getWidth(); x++ ) {
            for ( int y = 0; y < source.getHeight(); y++ ) {
                int color = source.getRGB(x, y);
                ArrayList<int[]> list = pixels.get(color);
                if (list == null) list = new ArrayList<>();
                list.add(new int[]{x, y});
                pixels.put(color, list);
            }
        }
        return getBestMatch(source, pixels, image);
    }

    private static int @Nullable [] getBestMatch(BufferedImage source, HashMap<Integer, ArrayList<int[]>> pixels, @NotNull BufferedImage image) {
        final ArrayList<int[]> emptyList = new ArrayList<>();

        int maxMatchCount = 0;
        ArrayList<int[]> points = null;

        for ( int x = 0; x < image.getWidth(); x++ ) {
            for ( int y = 0; y < image.getHeight(); y++ ) {
                int color = image.getRGB(x, y);
                for ( int[] pos : pixels.getOrDefault(color, emptyList) ) {
                    ArrayList<int[]> matches = getMatchAmount(source, pos, image, x, y);
                    int matchCount = matches.size();

                    if (matchCount > maxMatchCount) {
                        maxMatchCount = matchCount;
                        points = matches;
                    }
                }

            }
        }
        if (points == null) return null;

        return points.get(0);
    }

    private static @NotNull ArrayList<int[]> getMatchAmount(BufferedImage source, int[] check, BufferedImage image, int x, int y) {
        ArrayList<int[]> matches = new ArrayList<>();
        matches.add(check);
        int baseX = check[0];
        int baseY = check[1];

        for ( int i = 0; i < matches.size(); i++ ) {
            try {
                int[] match = matches.get(i);

                int xI = match[0] - baseX + x;
                int yI = match[1] - baseY + y;

                int srcColor1 = source.getRGB(match[0], match[1] + 1);
                if (srcColor1 == -16777216) continue;
                int imgColor1 = image.getRGB(xI, yI + 1);
                if (imgColor1 == srcColor1) {
                    int[] newMatch = new int[]{match[0], match[1] + 1};
                    if (notContains2D(matches, newMatch)) matches.add(newMatch);
                }

                int srcColor2 = source.getRGB(match[0] + 1, match[1]);
                if (srcColor2 == -16777216) continue;
                int imgColor2 = image.getRGB(xI + 1, yI);
                if (imgColor2 == srcColor2) {
                    int[] newMatch = new int[]{match[0] + 1, match[1]};
                    if (notContains2D(matches, newMatch)) matches.add(newMatch);
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
        return matches;
    }

    private static boolean notContains2D(@NotNull List<int[]> list, int[] a) {
        for ( int[] i : list ) {
            if (i[0] == a[0] && i[1] == a[1]) {
                return false;
            }
        }
        return true;
    }


}
