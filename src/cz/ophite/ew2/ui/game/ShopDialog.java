package cz.ophite.ew2.ui.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.alee.laf.button.WebButton;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;

import cz.ophite.ew2.ImageConst;
import cz.ophite.ew2.game.GameBoard;
import cz.ophite.ew2.game.GameBoardListener;
import cz.ophite.ew2.game.GameState;
import cz.ophite.ew2.game.Player;
import cz.ophite.ew2.game.json.Resource;
import cz.ophite.ew2.ui.base.AbstractDialog;
import cz.ophite.ew2.util.GuiUtil;

@SuppressWarnings("serial")
public class ShopDialog extends AbstractDialog implements GameBoardListener
{
    private Player player;

    private ShopModelResCountRenderer shopRenderer;
    private ShopModel shopModel;

    private JLabel lbPrice;
    private JLabel lbIncome;
    private WebButton btnBuy;
    private WebButton btnSell;

    public ShopDialog(Window gameWindow, GameBoard gameBoard)
    {
        super(gameWindow, "Energy sources shop", 400, 260);
        gameBoard.gameBoardHandler.addListener(this);
        player = gameBoard.getPlayer();

        setModal(false);
        setIconImage(null);
        setShowCloseButton(false);
        setLayout(new BorderLayout());

        player.playerHandler.addListener(money -> {
            checkPrice(shopModel.getPrice());
        });

        shopModel = new ShopModel(player);
        shopModel.shopHandler.addListener((price, income) -> {
            checkPrice(price);
            checkIncome(income);
        });

        shopRenderer = new ShopModelResCountRenderer(player);

        WebTable table = new WebTable(shopModel);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setSelectionBackground(Color.WHITE);
        table.getColumnModel().getColumn(0).setCellRenderer(shopRenderer);

        WebScrollPane scrollPane = new WebScrollPane(table);
        resizeTableColumns(table);
        add(scrollPane);

        JPanel controlPane = new JPanel();
        controlPane.setPreferredSize(new Dimension(0, 45));
        controlPane.setLayout(new BorderLayout());
        {
            JPanel labelsPane = new JPanel();
            labelsPane.setPreferredSize(new Dimension(150, 0));
            labelsPane.setLayout(new FlowLayout(FlowLayout.LEFT));
            {
                JPanel pricePane = new JPanel();
                pricePane.setLayout(new BorderLayout());
                {
                    JLabel lbPriceStatic = new JLabel("Price: ");
                    pricePane.add(lbPriceStatic, BorderLayout.WEST);

                    lbPrice = new JLabel("");
                    lbPrice.setFont(lbPrice.getFont().deriveFont(Font.BOLD));
                    pricePane.add(lbPrice, BorderLayout.CENTER);
                }
                JPanel incomePane = new JPanel();
                incomePane.setLayout(new BorderLayout());
                {
                    JLabel lbIncomeStatic = new JLabel("Income: ");
                    incomePane.add(lbIncomeStatic, BorderLayout.WEST);

                    lbIncome = new JLabel("");
                    lbIncome.setFont(lbIncome.getFont().deriveFont(Font.BOLD));
                    incomePane.add(lbIncome, BorderLayout.CENTER);
                }
                labelsPane.add(pricePane);
                labelsPane.add(Box.createHorizontalGlue());
                labelsPane.add(incomePane);
                labelsPane.add(Box.createHorizontalStrut(40));
            }
            JPanel buttonsPane = new JPanel();
            buttonsPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            {
                btnSell = new WebButton("Sell", e -> sellResource());
                btnSell.setPreferredSize(new Dimension(60, 28));
                btnSell.setBoldFont();
                btnSell.setIcon(GuiUtil.getIcon(ImageConst.SELL));
                buttonsPane.add(btnSell);

                btnBuy = new WebButton("Buy", e -> buyResource());
                btnBuy.setPreferredSize(new Dimension(60, 28));
                btnBuy.setBoldFont();
                btnBuy.setIcon(GuiUtil.getIcon(ImageConst.BUY));
                buttonsPane.add(btnBuy);
            }
            controlPane.add(labelsPane, BorderLayout.WEST);
            controlPane.add(buttonsPane, BorderLayout.EAST);
        }
        add(controlPane, BorderLayout.SOUTH);

        checkPrice(0);
        checkIncome(0);
    }

    private void buyResource()
    {
        Set<Resource> resources = shopModel.getCheckedResources();

        for (Resource res : resources) {
            player.buyResource(res);
        }
        shopModel.resetAfterAction();
    }

    private void sellResource()
    {
        Set<Resource> resources = shopModel.getCheckedResources();

        for (Resource res : resources) {
            player.sellResource(res);
        }
        shopModel.resetAfterAction();
    }

    private void resizeTableColumns(WebTable table)
    {
        ShopModel model = (ShopModel) table.getModel();
        Object[] longestRowPattern = model.getLongestRowPattern();
        TableCellRenderer cellRenderer = table.getTableHeader().getDefaultRenderer();
        TableColumn column;
        Component comp;
        int headerWidth;
        int cellWidth;

        for (int i = 0; i < model.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            comp = cellRenderer.getTableCellRendererComponent(null, column.getHeaderValue(), false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;

            TableCellRenderer cell = table.getDefaultRenderer(model.getColumnClass(i));
            comp = cell.getTableCellRendererComponent(table, longestRowPattern[i], false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;
            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    }

    private void checkPrice(double tablePrice)
    {
        boolean valid = (player.getMoney() != 0 && tablePrice != 0 && player.getMoney() >= tablePrice);

        if (tablePrice == 0) {
            lbPrice.setForeground(Color.BLACK);
        } else {
            lbPrice.setForeground(valid ? new Color(50, 150, 50) : new Color(150, 50, 50));
        }
        lbPrice.setText(String.format("%.0f / %.0f", tablePrice, player.getMoney()));
        btnBuy.setEnabled(valid && !shopModel.getCheckedResources().isEmpty());
        btnSell.setEnabled(!shopModel.getCheckedResources().isEmpty());
    }

    private void checkIncome(double tableIncome)
    {
        if (tableIncome > 0) {
            lbIncome.setText("+" + tableIncome);
            lbIncome.setForeground(new Color(50, 150, 50));
        } else {
            lbIncome.setText(String.valueOf(tableIncome));
            lbIncome.setForeground(Color.BLACK);
        }
    }

    @Override
    public void gameStateChanged(GameState newState)
    {
        if (newState == GameState.PLAY) {
            shopModel.resetAfterAction();
            shopModel.resetPurchased();

            checkPrice(shopModel.getPrice());
            checkIncome(shopModel.getIncome());
        }
    }
}
