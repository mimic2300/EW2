package cz.ophite.ew2.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import cz.ophite.ew2.ui.base.AbstractPane;

@SuppressWarnings("serial")
public abstract class ScenePane extends AbstractPane
{
    public static final int UPDATE_DELAY = 16; // ms

    private Timer timer;
    private BufferedImage backBuffer;

    public ScenePane(Window gameWindow, Component owner)
    {
        super(gameWindow, owner);

        setIgnoreRepaint(true);
        setFocusable(false);
        setRequestFocusEnabled(false);
        setDoubleBuffered(false);

        getOwner().addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                backBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

                timer = new Timer();
                timer.schedule(new GameLoop(), 0, UPDATE_DELAY);

                super.componentShown(e);
            }
        });
    }

    public void exportToPng(String fileName)
    {
        if (backBuffer != null) {
            try {
                File image = new File(fileName);
                ImageIO.write(backBuffer, "png", image);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        if (backBuffer != null) {
            Graphics2D g2 = (Graphics2D) backBuffer.getGraphics();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(Color.GRAY);
            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            g2.setColor(Color.BLACK);

            render(g2);

            g.drawImage(backBuffer, 0, 0, null);
            g2.dispose();
        }
    }

    public abstract void render(Graphics2D g2);

    private class GameLoop extends TimerTask
    {
        @Override
        public void run()
        {
            repaint();
        }
    }
}
