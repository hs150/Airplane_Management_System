package airline.utils;

import java.awt.*;
import java.io.File;
import java.net.URL;
import javax.swing.*;

public class AssetLoader {
    public static ImageIcon loadImage(String path, int width, int height) {
        try {
            URL url = AssetLoader.class.getResource(path);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
        } catch (Exception ignored) {
        }

        File file = new File(path);
        if (file.exists()) {
            ImageIcon icon = new ImageIcon(path);
            Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }

        JLabel fallback = new JLabel("Preview Image");
        fallback.setHorizontalAlignment(SwingConstants.CENTER);
        fallback.setOpaque(true);
        fallback.setBackground(new Color(240, 244, 250));
        fallback.setForeground(new Color(21, 101, 192));
        return new ImageIcon(new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB));
    }
}
