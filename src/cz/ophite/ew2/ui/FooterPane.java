package cz.ophite.ew2.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;

import com.alee.laf.label.WebLabel;

import cz.ophite.ew2.game.json.ConfigJson;
import cz.ophite.ew2.game.json.ConfigProvider;
import cz.ophite.ew2.ui.base.AbstractPane;

@SuppressWarnings("serial")
public class FooterPane extends AbstractPane
{
    private static final ConfigJson CONF = ConfigProvider.getInstance().getGameConfig();

    public FooterPane(Window gameWindow, Component owner)
    {
        super(gameWindow, owner);

        WebLabel lbVersion = new WebLabel("v" + CONF.getGameVersion());
        lbVersion.setFont(getFont().deriveFont(11f));
        add(lbVersion, BorderLayout.EAST);
    }
}
