package cz.ophite.ew2.ui.base;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class AbstractPane extends JPanel
{
    private Component owner;

    public AbstractPane(Component owner)
    {
        super();
        this.owner = owner;

        setLayout(new BorderLayout(0, 0));
        initComponents();
    }

    public Component getOwner()
    {
        return owner;
    }

    protected abstract void initComponents();
}
