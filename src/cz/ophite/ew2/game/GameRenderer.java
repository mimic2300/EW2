package cz.ophite.ew2.game;

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

    private MenuRenderer menuRenderer;
    private PlayRenderer playRenderer;

    public GameRenderer(Window gameWindow, GameBoard gameBoard)
    {
        super(gameWindow, gameWindow);
        this.gameBoard = gameBoard;

        player = gameBoard.getPlayer();
        menuRenderer = new MenuRenderer();
        playRenderer = new PlayRenderer();
    }

    @Override
    public void render(Graphics2D g2)
    {
        switch (gameBoard.getGameState()) {
            case MENU:
                menuRenderer.update();
                menuRenderer.render(g2);
                break;

            case PLAY:
                playRenderer.update();
                playRenderer.render(g2);
                break;
        }
    }

    private Point getCenter()
    {
        return new Point(getWidth() / 2, getHeight() / 2);
    }

    private class MenuRenderer
    {
        private float theta = 0;
        private boolean thetaAddition = true;

        private void update()
        {
            theta += thetaAddition ? 0.4f : -0.2f;

            if (theta > 8) {
                thetaAddition = false;
            }
            if (theta < 0) {
                thetaAddition = true;
            }
        }

        private void render(Graphics2D g2)
        {
            Point center = getCenter();
            AffineTransform transform = g2.getTransform();

            for (int i = 0, n = 0, r = 100; i < 56; i++, n += 5, r -= 2) {
                g2.rotate(Math.toRadians(theta), center.x, center.y);
                g2.drawRoundRect(center.x - 140 + n, center.y - 140 + n, 280 - n * 2, 280 - n * 2, r, r);
            }
            g2.setTransform(transform);

            for (int i = 0, n = 0, r = 100; i < 56; i++, n += 5, r -= 2) {
                g2.rotate(-Math.toRadians(theta), center.x, center.y);
                g2.drawRoundRect(center.x - 140 + n, center.y - 140 + n, 280 - n * 2, 280 - n * 2, r, r);
            }
            g2.setTransform(transform);
        }
    }

    private class PlayRenderer
    {
        private void update()
        {
            gameBoard.updatePlayerMoney();
        }

        private void render(Graphics2D g2)
        {
            g2.drawString(getWidth() + "x" + getHeight(), 200, 20);
            g2.drawString(String.format("Money: %.0f (%.3f /t), (%.3f /s)",
                    player.getMoney(),
                    gameBoard.getMoneyPerTick(),
                    gameBoard.getMoneyPerSecond()),
                    10,
                    getHeight() - 5);
            g2.drawString(String.format("Income: %.3f", player.getIncome()), 300, getHeight() - 5);
        }
    }
}
