package cz.ophite.ew2.ui.game;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.extended.panel.WebComponentPanel;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;

import cz.ophite.ew2.game.GameBoard;
import cz.ophite.ew2.game.GameState;
import cz.ophite.ew2.game.Player;
import cz.ophite.ew2.game.json.Difficulty;
import cz.ophite.ew2.game.json.DifficultyProvider;
import cz.ophite.ew2.ui.base.AbstractDialog;

@SuppressWarnings("serial")
public class NewGameDialog extends AbstractDialog
{
    private static final DifficultyProvider DP = DifficultyProvider.getInstance();

    private GameBoard gameBoard;
    private Difficulty selectedDifficulty;

    private WebComponentPanel comPane;
    private WebTextField fieldPlayer;
    private WebButton btnPlay;

    public NewGameDialog(Window gameWindow, GameBoard gameBoard)
    {
        super(gameWindow, "New Game", 400, 260);
        this.gameBoard = gameBoard;
        selectedDifficulty = gameBoard.getDifficulty();

        comPane = new WebComponentPanel();
        comPane.setElementMargin(5);
        comPane.setReorderingAllowed(false);
        comPane.setShowReorderGrippers(false);
        comPane.setEnabled(false);

        createPlayerName();
        createDifficulty();
        createButtons();

        add(comPane, BorderLayout.CENTER);

        tryEnablePlayButton();
    }

    private void play()
    {
        Player player = gameBoard.getPlayer();
        player.clear();
        player.setName(fieldPlayer.getText());

        gameBoard.setDifficulty(selectedDifficulty);
        gameBoard.setGameState(GameState.PLAY);

        dispose();
    }

    private void createPlayerName()
    {
        WebLabel lbName = new WebLabel("Player:");
        addLabelTooltip(lbName, "Player name");

        fieldPlayer = new WebTextField();
        fieldPlayer.setInputPrompt("set your in-game name...");
        fieldPlayer.putClientProperty(GroupPanel.FILL_CELL, true);
        fieldPlayer.setHideInputPromptOnFocus(false);
        fieldPlayer.addCaretListener(e -> {
            tryEnablePlayButton();
        });

        addElement(lbName, fieldPlayer);
    }

    private void createDifficulty()
    {
        WebLabel lbDifficulty = new WebLabel("Difficulty:");
        addLabelTooltip(lbDifficulty, "Game difficulty");

        List<WebToggleButton> toggles = new ArrayList<WebToggleButton>();

        for (Difficulty diff : DP.getDifficulty()) {
            WebToggleButton toggle = new WebToggleButton(diff.getName());

            toggle.addActionListener(e -> {
                selectedDifficulty = DP.getByCode(((WebToggleButton) e.getSource()).getText());
            });

            if (DP.isDefaultDifficulty(diff)) {
                toggle.setSelected(true);
            }
            addDifficultyTooltip(toggle, diff.getDescription());
            toggles.add(toggle);
        }
        WebButtonGroup textGroup = new WebButtonGroup(true, toggles.toArray(new WebToggleButton[] {}));
        textGroup.setButtonsDrawFocus(false);

        addElement(lbDifficulty, textGroup);
    }

    private void createButtons()
    {
        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout());

        JPanel spacePane = new JPanel();
        spacePane.setPreferredSize(new Dimension(0, 50));
        addElement(spacePane);

        btnPlay = new WebButton("Play", e -> {
            play();
        });
        btnPlay.setBoldFont();

        WebButton btnClose = new WebButton("Close", e -> {
            dispose();
        });
        btnPlay.setPreferredSize(new Dimension(100, 28));
        btnClose.setPreferredSize(new Dimension(50, 28));

        addButtonTooltip(btnPlay, "Enjoy the game ;)");
        addButtonTooltip(btnClose, "Hmm...");

        pane.add(btnPlay);
        pane.add(btnClose);

        comPane.addElement(pane);
    }

    private void tryEnablePlayButton()
    {
        btnPlay.setEnabled(fieldPlayer.getText().length() > 0);
    }

    private void addElement(Component... components)
    {
        comPane.addElement(new GroupPanel(10, components));
    }

    private void addLabelTooltip(Component com, String text)
    {
        TooltipManager.setTooltip(com, text, TooltipWay.right, 250);
    }

    private void addDifficultyTooltip(Component com, String text)
    {
        TooltipManager.setTooltip(com, text, TooltipWay.down, 250);
    }

    private void addButtonTooltip(Component com, String text)
    {
        TooltipManager.setTooltip(com, text, TooltipWay.up, 250);
    }
}
