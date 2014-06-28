package cz.ophite.ew2.ui.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.alee.laf.button.WebButton;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;

import cz.ophite.ew2.game.Player;
import cz.ophite.ew2.ui.base.AbstractDialog;

@SuppressWarnings("serial")
public class ShopDialog extends AbstractDialog
{
    private Player player;

    private JLabel lbTotalPrice;
    private JLabel lbTotalIncome;
    private WebButton btnBuy;

    public ShopDialog(Window gameWindow, Player player)
    {
        super(gameWindow, "Energy sources shop", 400, 260);
        this.player = player;

        setModal(false);
        setIconImage(null);
        setShowCloseButton(false);

        setLayout(new BorderLayout());

        ShopModel shopModel = new ShopModel();
        shopModel.setModelListener((resource, totalPrice, totalIncome) -> {
            checkTotalPrice(totalPrice);
            lbTotalIncome.setText(String.valueOf(totalIncome));
        });

        WebTable table = new WebTable(shopModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);

        WebScrollPane scrollPane = new WebScrollPane(table);
        autoResizeColumns(table);
        add(scrollPane);

        JPanel pane = new JPanel();
        pane.setPreferredSize(new Dimension(0, 45));
        pane.setLayout(new BorderLayout());
        {
            JPanel statePane = new JPanel();
            statePane.setPreferredSize(new Dimension(200, 0));
            statePane.setLayout(new FlowLayout(FlowLayout.LEFT));
            {
                JPanel pricePane = new JPanel();
                pricePane.setLayout(new BorderLayout());
                {
                    JLabel lbTotalPriceText = new JLabel("Total price: ");
                    pricePane.add(lbTotalPriceText, BorderLayout.WEST);

                    lbTotalPrice = new JLabel("0");
                    lbTotalPrice.setFont(lbTotalPrice.getFont().deriveFont(Font.BOLD));
                    pricePane.add(lbTotalPrice, BorderLayout.CENTER);
                }
                JPanel incomePane = new JPanel();
                incomePane.setLayout(new BorderLayout());
                {
                    JLabel lbTotalIncomeText = new JLabel("Total income: ");
                    incomePane.add(lbTotalIncomeText, BorderLayout.WEST);

                    lbTotalIncome = new JLabel("0");
                    lbTotalIncome.setForeground(new Color(50, 150, 150));
                    lbTotalIncome.setFont(lbTotalIncome.getFont().deriveFont(Font.BOLD));
                    incomePane.add(lbTotalIncome, BorderLayout.CENTER);
                }
                statePane.add(pricePane);
                statePane.add(Box.createHorizontalStrut(50));
                statePane.add(incomePane);
                statePane.add(Box.createHorizontalStrut(50));
            }
            pane.add(statePane, BorderLayout.WEST);

            btnBuy = new WebButton("Buy", e -> buy());
            btnBuy.setPreferredSize(new Dimension(80, 28));
            btnBuy.setBoldFont();
            pane.add(btnBuy, BorderLayout.EAST);
        }
        add(pane, BorderLayout.SOUTH);

        checkTotalPrice(0);
    }

    private void buy()
    {
        // TODO click on buy button...
    }

    private void autoResizeColumns(WebTable table)
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

    private void checkTotalPrice(int totalPrice)
    {
        boolean valid = (player.getMoney() != 0 && totalPrice != 0 && player.getMoney() >= totalPrice);

        lbTotalPrice.setText(String.format("%s / %s", totalPrice, player.getMoney()));
        lbTotalPrice.setForeground(valid ? new Color(50, 150, 50) : new Color(150, 50, 50));
        btnBuy.setEnabled(valid);
    }
}
