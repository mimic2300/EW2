package cz.ophite.ew2.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import com.alee.laf.button.WebButton;

@SuppressWarnings("serial")
public class NavigationPane extends AbstractPane
{
    private WebButton btnExit;

    public NavigationPane(Frame owner)
    {
        super(owner);
    }

    @Override
    protected void initComponents()
    {
        setLayout(new FlowLayout());

        btnExit = new WebButton("Exit");
        btnExit.setPreferredSize(new Dimension(80, 24));
        btnExit.addActionListener(e -> {
            System.exit(0);
        });
        add(btnExit);
    }
}
