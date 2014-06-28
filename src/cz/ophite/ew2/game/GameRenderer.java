package cz.ophite.ew2.game;

import java.awt.Frame;
import java.awt.Graphics2D;

import cz.ophite.ew2.ui.ScenePane;

@SuppressWarnings("serial")
public class GameRenderer extends ScenePane
{
    private GameBoard gameBoard;

    public GameRenderer(Frame owner, GameBoard gameBoard)
    {
        super(owner);
        this.gameBoard = gameBoard;
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
