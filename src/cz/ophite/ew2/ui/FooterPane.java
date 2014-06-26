package cz.ophite.ew2.ui;

import java.awt.BorderLayout;
import java.awt.Frame;

import com.alee.laf.label.WebLabel;

import cz.ophite.ew2.Game;

@SuppressWarnings("serial")
public class FooterPane extends AbstractPane
{
    public FooterPane(Frame owner)
    {
        super(owner);
    }

    @Override
    protected void initComponents()
    {
        WebLabel lbVersion = new WebLabel("v" + Game.GAME_VERSION);
        lbVersion.setFont(getFont().deriveFont(11f));
        add(lbVersion, BorderLayout.EAST);
    }
}
