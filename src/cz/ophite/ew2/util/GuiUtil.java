package cz.ophite.ew2.util;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import cz.ophite.ew2.Game;
import cz.ophite.ew2.ImageConst;

public final class GuiUtil
{
    private GuiUtil()
    {}

    public static void setUIFont(Font font)
    {
        Enumeration<?> keys = UIManager.getDefaults().keys();
        FontUIResource fontResource = new FontUIResource(font);

        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);

            if (value instanceof FontUIResource) {
                UIManager.put(key, fontResource);
            }
        }
    }

    public static ImageIcon getIcon(ImageConst image)
    {
        return new ImageIcon(getImage(image));
    }

    public static Image getImage(ImageConst image)
    {
        return getBufferedImage(image.getPath());
    }

    private static BufferedImage getBufferedImage(String imagePath)
    {
        InputStream stream = Game.class.getClassLoader().getResourceAsStream(imagePath);
        BufferedImage image = null;

        try {
            if (stream == null) {
                image = ImageIO.read(new File(imagePath).toURI().toURL());
            } else {
                image = ImageIO.read(stream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
