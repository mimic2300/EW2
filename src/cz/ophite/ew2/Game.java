package cz.ophite.ew2;

import java.awt.EventQueue;

public class Game
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> {
            try {
                GameWindow wnd = new GameWindow();
                wnd.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
