package cz.ophite.ew2.ui.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import cz.ophite.ew2.game.GameBoard;
import cz.ophite.ew2.game.Player;
import cz.ophite.ew2.game.json.Resource;
import cz.ophite.ew2.game.json.ResourceProvider;
import cz.ophite.ew2.util.EventHandler;
import cz.ophite.ew2.util.Utils;

@SuppressWarnings("serial")
public class ShopModel extends AbstractTableModel
{
    private static final ResourceProvider RP = ResourceProvider.getInstance();

    public static final int COLUMN_PURCHASED = 0;
    public static final int COLUMN_BUY = 4;
    public static final int COLUMN_CODE = 5;

    public final ShopModelHandler shopHandler = new ShopModelHandler();

    private Player player;

    private String[] columnNames;
    private Object[][] data;
    private Object[] longestRowPattern;

    private Set<Resource> checkedResource;
    private double price = 0;
    private double income = 0;

    public ShopModel(Player player)
    {
        this.player = player;
        columnNames = new String[] { "Own", "Name", "Price", "Income/s", "" };
        longestRowPattern = new Object[] { toPurchased(1000, 1000), getLongestRowByName(), 100000000., 1000000., true };
        checkedResource = new HashSet<Resource>();
        data = prepareData();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return data.length;
    }

    @Override
    public String getColumnName(int col)
    {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        return data[row][col];
    }

    @Override
    public Class<?> getColumnClass(int c)
    {
        return getLongestRowPattern()[c].getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
        return (col == COLUMN_BUY);
    }

    @Override
    public void setValueAt(Object value, int row, int col)
    {
        data[row][col] = value;
        fireTableCellUpdated(row, col);

        String resCode = (String) data[row][COLUMN_CODE];
        Resource res = RP.getResourceByCode(resCode);

        if (Boolean.TRUE.equals(value)) {
            checkedResource.add(res);
        } else {
            checkedResource.remove(res);
        }
        calculatePriceAndIncome();
        shopHandler.fireResourceChecked(price, income);
    }

    public Set<Resource> getCheckedResources()
    {
        return checkedResource;
    }

    public double getPrice()
    {
        return price;
    }

    public double getIncome()
    {
        return income;
    }

    public void resetAfterAction()
    {
        for (int row = 0; row < data.length; row++) {
            Resource res = RP.getResourceByCode((String) data[row][COLUMN_CODE]);
            int purchasedCount = player.getResourceCountOf(res);

            if (checkedResource.contains(res)) {
                data[row][COLUMN_BUY] = false;
                data[row][COLUMN_PURCHASED] = toPurchased(purchasedCount, res.getMaxLimit());
                fireTableCellUpdated(row, COLUMN_BUY);
                fireTableCellUpdated(row, COLUMN_PURCHASED);
            }
        }
        checkedResource.clear();
        calculatePriceAndIncome();
        shopHandler.fireResourceChecked(price, income);
    }

    public Resource getResource(int row)
    {
        return RP.getResourceByCode((String) data[row][COLUMN_CODE]);
    }

    public void resetPurchased()
    {
        for (int row = 0; row < data.length; row++) {
            Resource res = RP.getResourceByCode((String) data[row][COLUMN_CODE]);
            data[row][COLUMN_PURCHASED] = toPurchased(0, res.getMaxLimit());
            fireTableCellUpdated(row, COLUMN_PURCHASED);
        }
    }

    public Object[] getLongestRowPattern()
    {
        return longestRowPattern;
    }

    private void calculatePriceAndIncome()
    {
        price = 0;
        income = 0;

        for (Resource res : checkedResource) {
            price += res.getPrice();
            income += res.getIncome();
        }
        price = Utils.toDoubleRound(price);
        income = Utils.toDoubleRound(income);
    }

    private Object[][] prepareData()
    {
        List<Object[]> rows = new ArrayList<Object[]>();

        for (Resource res : RP.getResources()) {
            List<Object> column = new ArrayList<Object>();
            column.add(toPurchased(0, res.getMaxLimit()));
            column.add(res.getName());
            column.add(res.getPrice());
            column.add(GameBoard.toMoneyToSecond(res.getIncome()));
            column.add(false);
            column.add(res.getCode());

            rows.add(column.toArray());
        }
        return rows.toArray(new Object[][] {});
    }

    private String getLongestRowByName()
    {
        if (RP.getResources().isEmpty()) {
            return "";
        }
        Resource longest = RP.getResources().get(0);

        for (Resource res : RP.getResources()) {
            if (res.getName().length() > longest.getName().length()) {
                longest = res;
            }
        }
        return longest.getName();
    }

    private String toPurchased(double current, double total)
    {
        return String.format("%s / %s", current, total);
    }

    class ShopModelHandler extends EventHandler<ShopModelListener>
    {
        private void fireResourceChecked(double price, double income)
        {
            for (ShopModelListener listener : listeners) {
                listener.resourceChecked(price, income);
            }
        }
    }
}
