package cz.ophite.ew2.util;

import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public final class GuiUtil
{
    private GuiUtil()
    {}

    public static void setUIFont(FontUIResource f)
    {
        Enumeration<?> keys = UIManager.getDefaults().keys();

        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);

            if (value instanceof FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }
}
