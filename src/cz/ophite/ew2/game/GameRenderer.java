package cz.ophite.ew2.game;

import java.awt.Frame;
import java.awt.Graphics2D;

import cz.ophite.ew2.ui.ScenePane;

@SuppressWarnings("serial")
public class GameRenderer extends ScenePane
{
    private Player player;

    public GameRenderer(Frame owner, Player player)
    {
        super(owner);
        this.player = player;
    }

    public Player getPlayer()
    {
        return player;
    }

    @Override
    public void render(Graphics2D g2)
    {
        // TODO draw something
    }

    @Override
    public void update()
    {
        // TODO logic
    }
}
