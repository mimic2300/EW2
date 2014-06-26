package cz.ophite.ew2.ui;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class AbstractPane extends JPanel
{
    private Frame owner;

    public AbstractPane(Frame owner)
    {
        this.owner = owner;

        setLayout(new BorderLayout(0, 0));
        initComponents();
    }

    public Frame getOwner()
    {
        return owner;
    }

    protected abstract void initComponents();
}
