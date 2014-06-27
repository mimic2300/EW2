package cz.ophite.ew2;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.alee.laf.WebLookAndFeel;

public final class Game
{
    public static final String GAME_TITLE = "Energy Wars 2.0";
    public static final String GAME_VERSION = "0.0.6";

    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
                WebLookAndFeel.setDecorateFrames(true);
                WebLookAndFeel.setDecorateDialogs(true);

                GameWindow wnd = new GameWindow();
                wnd.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
