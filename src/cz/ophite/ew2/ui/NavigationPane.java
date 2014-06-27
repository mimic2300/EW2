package cz.ophite.ew2.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import com.alee.laf.button.WebButton;

import cz.ophite.ew2.game.Player;
import cz.ophite.ew2.ui.base.AbstractPane;
import cz.ophite.ew2.ui.game.NewGameDialog;

@SuppressWarnings("serial")
public class NavigationPane extends AbstractPane
{
    private Player player;

    public NavigationPane(Component owner, Player player)
    {
        super(owner);
        this.player = player;
    }

    @Override
    protected void initComponents()
    {
        setLayout(new FlowLayout());

        addButton("New Game", e -> {
            NewGameDialog dlg = new NewGameDialog(this, player);
            dlg.setVisible(true);
        });

        addButton("Exit", e -> {
            System.exit(0);
        });
    }

    private void addButton(String name, ActionListener action)
    {
        WebButton btn = new WebButton(name);
        btn.setPreferredSize(new Dimension(80, 24));
        btn.addActionListener(action);
        add(btn);
    }
}
