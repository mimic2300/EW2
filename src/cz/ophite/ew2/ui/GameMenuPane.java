package cz.ophite.ew2.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JOptionPane;

import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;

import cz.ophite.ew2.ImageConst;
import cz.ophite.ew2.game.GameBoard;
import cz.ophite.ew2.game.GameBoardListener;
import cz.ophite.ew2.game.GameState;
import cz.ophite.ew2.game.json.ConfigJson;
import cz.ophite.ew2.game.json.ConfigProvider;
import cz.ophite.ew2.ui.base.AbstractPane;
import cz.ophite.ew2.ui.game.ShopDialog;
import cz.ophite.ew2.util.GuiUtil;

@SuppressWarnings("serial")
public class GameMenuPane extends AbstractPane implements GameBoardListener
{
    private static final ConfigJson CONF = ConfigProvider.getInstance().getGameConfig();

    private GameBoard gameBoard;

    private WebToggleButton btnShop;
    private WebButton btnGiveUp;

    private ShopDialog shopDialog;

    public GameMenuPane(Window gameWindow, Component owner, GameBoard gameBoard)
    {
        super(gameWindow, owner);
        this.gameBoard = gameBoard;
        gameBoard.gameBoardHandler.addListener(this);

        setLayout(new FlowLayout());

        btnShop = new WebToggleButton();
        btnShop.setText("Shop");
        btnShop.setPreferredSize(new Dimension(100, 24));
        btnShop.addActionListener(e -> onOpenShop());
        btnShop.setIcon(GuiUtil.getIcon(ImageConst.SHOP));
        btnShop.setShadeToggleIcon(true);
        add(btnShop);

        btnGiveUp = new WebButton();
        btnGiveUp.setText("Give Up");
        btnGiveUp.setPreferredSize(new Dimension(100, 24));
        btnGiveUp.addActionListener(e -> onGiveUp());
        btnGiveUp.setIcon(GuiUtil.getIcon(ImageConst.GIVE_UP));
        add(btnGiveUp);

        shopDialog = new ShopDialog(gameWindow, gameBoard);
        calculateShopPosition();
    }

    private void onOpenShop()
    {
        shopDialog.setVisibleEx(btnShop.isSelected());
    }

    private void onGiveUp()
    {
        if (giveUpApproved()) {
            gameBoard.setGameState(GameState.MENU);
            shopDialog.setVisibleEx(false);
            btnShop.setSelected(shopDialog.isVisible());
        }
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
    public void gameStateChanged(GameState newState)
    {
        switch (newState) {
            case MENU:
                btnShop.setVisible(false);
                btnGiveUp.setVisible(false);
                break;

            case PLAY:
                btnShop.setVisible(true);
                btnGiveUp.setVisible(true);
                break;
        }
    }
}
