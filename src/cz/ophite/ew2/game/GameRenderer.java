package cz.ophite.ew2.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import cz.ophite.ew2.ui.ScenePane;

@SuppressWarnings("serial")
public class GameRenderer extends ScenePane
{
    private GameBoard gameBoard;

    private float theta = 0;
    private boolean thetaAddition = true;

    public GameRenderer(Component owner, GameBoard gameBoard)
    {
        super(owner);
        this.gameBoard = gameBoard;
    }

    @Override
    public void render(Graphics2D g2)
    {
        switch (gameBoard.getGameState()) {
            case MENU:
                drawMenuState(g2);
                break;

            case PLAY:
                drawPlayState(g2);
                break;
        }
    }

    @Override
    public void update()
    {
        theta += thetaAddition ? 0.2f : -0.2f;
        if (theta > 8) {
            thetaAddition = false;
        }
        if (theta < -8) {
            thetaAddition = true;
        }
    }

    private void drawPlayState(Graphics2D g2)
    {}

    private void drawMenuState(Graphics2D g2)
    {
        // DEBUG
        g2.setColor(Color.BLACK);
        g2.drawString(String.valueOf(theta), 10, 20);
        g2.drawString(String.valueOf(thetaAddition), 10, 35);

        Point center = getCenter();
        AffineTransform transform = g2.getTransform();

        for (int i = 0, n = 0; i < 56; i++, n += 5) {
            g2.rotate(Math.toRadians(theta), center.x, center.y);
            g2.drawRect(center.x - 140 + n, center.y - 140 + n, 280 - n * 2, 280 - n * 2);
        }
        g2.setTransform(transform);
    }

    private Point getCenter()
    {
        return new Point(getWidth() / 2, getHeight() / 2);
    }
}
