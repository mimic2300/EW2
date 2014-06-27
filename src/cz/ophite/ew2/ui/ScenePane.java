package cz.ophite.ew2.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
    private Timer timer;
    private BufferedImage backBuffer;

    public ScenePane(Component owner)
    {
        super(owner);
    }

    @Override
    protected void initComponents()
    {
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
                timer.schedule(new GameLoop(), 0, 10);

                super.componentShown(e);
            }
        });
    }

    public void saveScene(String fileName)
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
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(Color.GRAY);
            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

            render(g2);

            g.drawImage(backBuffer, 0, 0, null);
            g2.dispose();
        }
    }

    public abstract void update();

    public abstract void render(Graphics2D g2);

    private class GameLoop extends TimerTask
    {
        @Override
        public void run()
        {
            update();
            repaint();
        }
    }
}
