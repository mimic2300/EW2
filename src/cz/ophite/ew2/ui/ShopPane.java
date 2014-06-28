package cz.ophite.ew2.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import com.alee.laf.button.WebButton;

import cz.ophite.ew2.game.json.ResourceProvider;
import cz.ophite.ew2.ui.base.AbstractPane;

@SuppressWarnings("serial")
public class ShopPane extends AbstractPane
{
    private static final ResourceProvider RP = ResourceProvider.getInstance();

    public ShopPane(Component owner)
    {
        super(owner);
    }

    @Override
    protected void initComponents()
    {
        setLayout(new FlowLayout());

        addButton("Open shop", e -> {
            // TODO not implemented yet
        });
    }

    private WebButton addButton(String name, ActionListener action)
    {
        WebButton btn = new WebButton(name);
        btn.setPreferredSize(new Dimension(80, 24));
        btn.addActionListener(action);
        add(btn);
        return btn;
    }
}
