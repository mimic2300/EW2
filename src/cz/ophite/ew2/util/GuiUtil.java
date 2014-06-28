package cz.ophite.ew2.util;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

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
}
