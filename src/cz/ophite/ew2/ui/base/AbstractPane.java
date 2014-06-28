package cz.ophite.ew2.ui.base;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class AbstractPane extends JPanel
{
    private Window gameWindow;
    private Component owner;

    public AbstractPane(Window gameWindow, Component owner)
    {
        super();
        this.gameWindow = gameWindow;
        this.owner = owner;

        setLayout(new BorderLayout(0, 0));
    }

    public Component getOwner()
    {
        return owner;
    }

    public Window getGameWindow()
    {
        return gameWindow;
    }
}
