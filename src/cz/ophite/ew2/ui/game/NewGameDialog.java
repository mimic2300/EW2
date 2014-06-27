package cz.ophite.ew2.ui.game;

import java.awt.Component;

import cz.ophite.ew2.game.Player;
import cz.ophite.ew2.ui.base.AbstractDialog;

@SuppressWarnings("serial")
public class NewGameDialog extends AbstractDialog
{
    private Player player;

    public NewGameDialog(Component owner, Player player)
    {
        super(owner, "New Game", 400, 500);
        this.player = player;
        initComponents();
    }

    @Override
    protected void initComponents()
    {}
}
