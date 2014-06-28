package cz.ophite.ew2.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import com.alee.laf.button.WebButton;

import cz.ophite.ew2.game.GameBoard;
import cz.ophite.ew2.ui.base.AbstractFrame;
import cz.ophite.ew2.ui.base.AbstractPane;
import cz.ophite.ew2.ui.game.NewGameDialog;

@SuppressWarnings("serial")
public class NavigationPane extends AbstractPane implements Observer
{
    private WebButton btnNewGame;

    public NavigationPane(Window gameWindow, Component owner, GameBoard gameBoard)
    {
        super(gameWindow, owner);
        gameBoard.addObserver(this);

        setLayout(new FlowLayout());

        btnNewGame = addButton("New Game", e -> {
            NewGameDialog dlg = new NewGameDialog(gameWindow, gameBoard);
            dlg.setVisible(true);
        });

        addButton("Quit", e -> {
            WindowEvent we = new WindowEvent(((AbstractFrame) getOwner()), WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(we);
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

    @Override
    public void update(Observable o, Object arg)
    {
        GameBoard board = (GameBoard) arg;

        switch (board.getGameState()) {
            case MENU:
                btnNewGame.setVisible(true);
                break;

            case PLAY:
                btnNewGame.setVisible(false);
                break;
        }
    }
}
