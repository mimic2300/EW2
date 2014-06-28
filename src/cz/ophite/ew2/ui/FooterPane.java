package cz.ophite.ew2.ui;

import java.awt.BorderLayout;
import java.awt.Component;

import com.alee.laf.label.WebLabel;

import cz.ophite.ew2.game.json.ConfigJson;
import cz.ophite.ew2.game.json.ConfigProvider;
import cz.ophite.ew2.ui.base.AbstractPane;

@SuppressWarnings("serial")
public class FooterPane extends AbstractPane
{
    private static final ConfigJson CONF = ConfigProvider.getInstance().getGameConfig();

    public FooterPane(Component owner)
    {
        super(owner);
    }

    @Override
    protected void initComponents()
    {
        WebLabel lbVersion = new WebLabel("v" + CONF.getGameVersion());
        lbVersion.setFont(getFont().deriveFont(11f));
        add(lbVersion, BorderLayout.EAST);
    }
}
