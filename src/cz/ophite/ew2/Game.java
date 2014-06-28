package cz.ophite.ew2;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.UIManager;

import com.alee.laf.WebLookAndFeel;

import cz.ophite.ew2.game.json.ConfigProvider;
import cz.ophite.ew2.game.json.DifficultyProvider;
import cz.ophite.ew2.game.json.ResourceProvider;
import cz.ophite.ew2.util.GuiUtil;

public final class Game
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
                WebLookAndFeel.setDecorateFrames(true);
                WebLookAndFeel.setDecorateDialogs(true);

                ConfigProvider.getInstance();
                ResourceProvider.getInstance();
                DifficultyProvider.getInstance();

                GuiUtil.setUIFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));

                GameWindow wnd = new GameWindow();
                wnd.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
