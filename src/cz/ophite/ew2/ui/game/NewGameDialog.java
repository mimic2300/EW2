package cz.ophite.ew2.ui.game;

import java.awt.BorderLayout;
import java.awt.Component;
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

import cz.ophite.ew2.game.Difficulty;
import cz.ophite.ew2.game.Player;
import cz.ophite.ew2.ui.base.AbstractDialog;

@SuppressWarnings("serial")
public class NewGameDialog extends AbstractDialog
{
    private WebComponentPanel comPane;
    private Player player;

    private WebTextField fieldPlayer;
    private Difficulty selectedDifficulty;

    public NewGameDialog(Component owner, Player player)
    {
        super(owner, "New Game", 400, 300);
        this.player = player;
        selectedDifficulty = player.getDifficulty();
        initComponents();
    }

    @Override
    protected void initComponents()
    {
        comPane = new WebComponentPanel();
        comPane.setElementMargin(10);
        comPane.setReorderingAllowed(true);

        createPlayerName();
        createDifficulty();
        createSaveButton();

        add(comPane, BorderLayout.CENTER);
    }

    private void addElement(Component... components)
    {
        comPane.addElement(new GroupPanel(10, components));
    }

    private void createPlayerName()
    {
        WebLabel lbName = new WebLabel("Player:");
        fieldPlayer = new WebTextField();
        fieldPlayer.setInputPrompt("player name...");
        fieldPlayer.putClientProperty(GroupPanel.FILL_CELL, true);

        addElement(lbName, fieldPlayer);
    }

    private void createDifficulty()
    {
        WebLabel lbDifficulty = new WebLabel("Difficulty:");
        List<WebToggleButton> toggles = new ArrayList<WebToggleButton>();

        for (Difficulty diff : Difficulty.values()) {
            WebToggleButton toggle = new WebToggleButton(diff.getName());
            toggle.addActionListener(e -> {
                selectedDifficulty = Difficulty.getByName(((WebToggleButton) e.getSource()).getText());
            });

            if (diff.isInitial()) {
                toggle.setSelected(true);
            }
            toggles.add(toggle);
        }
        WebButtonGroup textGroup = new WebButtonGroup(true, toggles.toArray(new WebToggleButton[] {}));
        textGroup.setButtonsDrawFocus(false);

        addElement(lbDifficulty, textGroup);
    }

    private void createSaveButton()
    {
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        WebButton btnSave = new WebButton("Play", e -> {
            player.clear();
            player.setName(fieldPlayer.getText());
            player.setDifficulty(selectedDifficulty);
            dispose();
        });
        pane.add(btnSave, BorderLayout.EAST);

        comPane.addElement(pane);
    }
}
