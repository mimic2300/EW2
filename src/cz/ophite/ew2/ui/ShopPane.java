package cz.ophite.ew2.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;

import cz.ophite.ew2.game.GameBoard;
import cz.ophite.ew2.game.GameState;
import cz.ophite.ew2.game.json.ConfigJson;
import cz.ophite.ew2.game.json.ConfigProvider;
import cz.ophite.ew2.ui.base.AbstractPane;
import cz.ophite.ew2.ui.game.ShopDialog;

@SuppressWarnings("serial")
public class ShopPane extends AbstractPane implements Observer
{
    private static final ConfigJson CONF = ConfigProvider.getInstance().getGameConfig();

    private GameBoard gameBoard;

    private AbstractButton btnOpenShop;
    private AbstractButton btnGiveUp;

    private ShopDialog shopDialog;

    public ShopPane(Component owner, GameBoard gameBoard)
    {
        super(owner);
        this.gameBoard = gameBoard;
        gameBoard.addObserver(this);

        shopDialog = new ShopDialog(this);
        calculateShopPosition();
        shopDialog.setVisible(true);// HACK FOR TESTING !!!
    }

    @Override
    protected void initComponents()
    {
        setLayout(new FlowLayout());

        btnOpenShop = addButton("Open shop", true, e -> {
            shopDialog.setVisible(btnOpenShop.isSelected());
        });

        btnGiveUp = addButton("Give Up", false, e -> {
            if (giveUpApproved()) {
                gameBoard.setGameState(GameState.MENU);
                shopDialog.setVisible(false);
                btnOpenShop.setSelected(shopDialog.isVisible());
            }
        });
    }

    private AbstractButton addButton(String name, boolean toggle, ActionListener action)
    {
        AbstractButton btn = toggle ? new WebToggleButton() : new WebButton();
        btn.setText(name);
        btn.setPreferredSize(new Dimension(80, 24));
        btn.addActionListener(action);
        add(btn);
        return btn;
    }

    private boolean giveUpApproved()
    {
        int result = JOptionPane.showConfirmDialog(getOwner(),
                "You really want to give up?",
                "Give Up",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        return (result == JOptionPane.YES_OPTION);
    }

    private void calculateShopPosition()
    {
        getOwner().addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentMoved(ComponentEvent e)
            {
                shopDialog.setLocation(getOwner().getX() + CONF.getWindowWidth() - 40, getOwner().getY());
            }
        });
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
