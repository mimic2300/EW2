package cz.ophite.ew2.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Window;
import java.awt.geom.AffineTransform;

import cz.ophite.ew2.ui.ScenePane;

@SuppressWarnings("serial")
public class GameRenderer extends ScenePane
{
    private static final Font FONT_NORMAL = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
    private static final Font FONT_BOLD = new Font(Font.SANS_SERIF, Font.BOLD, 13);

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
            if (theta < 2) {
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
            gameBoard.update();
        }

        private void render(Graphics2D g2)
        {
            final int yStep = 18;
            int y = 20;
            int x = 140;

            g2.setFont(FONT_BOLD);
            g2.setColor(Color.BLACK);
            g2.drawString("Player: ", 10, y);
            g2.setColor(Color.BLUE);
            g2.drawString(player.getName(), x, y);
            y += yStep;

            g2.setFont(FONT_BOLD);
            g2.setColor(Color.BLACK);
            g2.drawString("Income: ", 10, y);
            g2.setColor(Color.BLUE);
            g2.drawString(String.valueOf(player.getIncome()), x, y);
            y += yStep;

            g2.setFont(FONT_BOLD);
            g2.setColor(Color.BLACK);
            g2.drawString("Money: ", 10, y);
            g2.setColor(Color.BLUE);
            g2.drawString(String.format("%.0f", player.getMoney()), x, y);
            y += yStep;

            g2.setFont(FONT_BOLD);
            g2.setColor(Color.BLACK);
            g2.drawString("Money per tick: ", 10, y);
            g2.setColor(Color.BLUE);
            g2.drawString(String.format("%.3f", gameBoard.getMoneyPerTick()), x, y);
            y += yStep;

            g2.setFont(FONT_BOLD);
            g2.setColor(Color.BLACK);
            g2.drawString("Money per second: ", 10, y);
            g2.setColor(Color.BLUE);
            g2.drawString(String.format("%.3f", gameBoard.getMoneyPerSecond()), x, y);
            y += yStep;

            g2.setFont(FONT_BOLD);
            g2.setColor(Color.BLACK);
            g2.drawString("Elapsed time: ", 10, y);
            g2.setColor(Color.BLUE);
            g2.drawString(gameBoard.getElapsedTimeFormat(), x, y);
            y += yStep;
        }
    }
}
