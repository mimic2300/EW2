package cz.ophite.ew2.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import com.alee.laf.button.WebButton;

import cz.ophite.ew2.game.GameBoard;
import cz.ophite.ew2.game.GameState;
import cz.ophite.ew2.ui.base.AbstractPane;

@SuppressWarnings("serial")
public class ShopPane extends AbstractPane implements Observer
{
    private GameBoard gameBoard;

    private WebButton btnOpenShop;
    private WebButton btnGiveUp;

    public ShopPane(Component owner, GameBoard gameBoard)
    {
        super(owner);
        this.gameBoard = gameBoard;
        gameBoard.addObserver(this);
    }

    @Override
    protected void initComponents()
    {
        setLayout(new FlowLayout());

        btnOpenShop = addButton("Open shop", e -> {
            openShop();
        });

        btnGiveUp = addButton("Give Up", e -> {
            if (giveUpQuestion()) {
                gameBoard.setGameState(GameState.MENU);
            }
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

    private void openShop()
    {
        // TODO not implemented yet
    }

    private boolean giveUpQuestion()
    {
        int result = JOptionPane.showConfirmDialog(getOwner(),
                "You really want to give up?",
                "Give Up",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        return (result == JOptionPane.YES_OPTION);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        GameBoard board = (GameBoard) arg;

        switch (board.getGameState()) {
            case MENU:
                btnOpenShop.setVisible(false);
                btnGiveUp.setVisible(false);
                break;

            case PLAY:
                btnOpenShop.setVisible(true);
                btnGiveUp.setVisible(true);
                break;
        }
    }
}
