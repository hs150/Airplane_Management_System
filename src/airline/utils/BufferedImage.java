package airline.utils;

import java.awt.image.BufferedImage;

public class BufferedImage {
    public static BufferedImage create(int width, int height, int type) {
        return new java.awt.image.BufferedImage(width, height, type);
    }
}
