package cz.ophite.ew2.ui.game;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;

import cz.ophite.ew2.ui.base.AbstractDialog;

@SuppressWarnings("serial")
public class ShopDialog extends AbstractDialog
{
    public ShopDialog(Component owner)
    {
        super(owner, "Energy sources shop", 400, 260);
    }

    @Override
    protected void initComponents()
    {
        setModal(false);
        setIconImage(null);
        setShowCloseButton(false);

        WebTable table = new WebTable(new ShopModel());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);

        WebScrollPane scrollPane = new WebScrollPane(table);
        autoResizeColumns(table);
        add(scrollPane);
    }

    private void autoResizeColumns(WebTable table)
    {
        ShopModel model = (ShopModel) table.getModel();
        Object[] longestRowPattern = model.longestRowPattern;
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
}
