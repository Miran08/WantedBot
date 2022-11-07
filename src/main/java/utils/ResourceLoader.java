package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ResourceLoader {

    public BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(getInternalPath(path));
    }

    private URL getInternalPath(String path) {
        return this.getClass().getClassLoader().getResource(path);
    }

}
