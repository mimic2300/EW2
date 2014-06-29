package cz.ophite.ew2.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Window;
import java.awt.geom.AffineTransform;

import cz.ophite.ew2.ui.ScenePane;

@SuppressWarnings("serial")
public class GameRenderer extends ScenePane
{
    private GameBoard gameBoard;
    private Player player;

    private float theta = 0;
    private boolean thetaAddition = true;

    public GameRenderer(Window gameWindow, GameBoard gameBoard)
    {
        super(gameWindow, gameWindow);
        this.gameBoard = gameBoard;
        player = gameBoard.getPlayer();
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
        g2.drawString(String.format("Money: %.0f (%.3f /t), (%.3f /s)",
                player.getMoney(),
                gameBoard.getMoneyPerTick(),
                gameBoard.getMoneyPerSecond()), 10, getHeight() - 5);
        g2.drawString(String.format("Income: %.3f", player.getIncome()), 330, getHeight() - 5);
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
        gameBoard.updatePlayerMoney();
    }

    private void drawPlayState(Graphics2D g2)
    {}

    private void drawMenuState(Graphics2D g2)
    {
        Player player = gameBoard.getPlayer();

        // DEBUG BEGIN
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
        // DEBUG END
    }

    private Point getCenter()
    {
        return new Point(getWidth() / 2, getHeight() / 2);
    }
}
