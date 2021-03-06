package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Item");
        ArrayList<Item> allItems = new ArrayList<>();
        while (rst.next()) {
            allItems.add(new Item(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4), rst.getInt(5)));
        }
        return allItems;
    }

    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Item VALUES (?,?,?,?,?)", entity.getCode(), entity.getDescription(), entity.getPackageSize(), entity.getUnitPrice(), entity.getQtyOnHand());

    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Item SET Description =? , PackSize=?, UnitPrice=?, QtyOnHand=?  WHERE ItemCode=?;", entity.getDescription(), entity.getPackageSize(), entity.getUnitPrice(), entity.getQtyOnHand(), entity.getCode());

    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Item WHERE ItemCode=?", code);
        if (rst.next()) {
            return new Item(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4), rst.getInt(5));
        }
        return null;
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT ItemCode FROM Item WHERE ItemCode=?", code).next();

    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Item WHERE ItemCode=?", code);

    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT ItemCode FROM Item ORDER BY ItemCode DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("ItemCode");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }

    @Override
    public boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Item SET QtyOnHand = QtyOnHand -? WHERE ItemCode=?", qty, itemCode);
    }

    @Override
    public String ItemCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT count(ItemCode) FROM Item");
        resultSet.next();
        String count = resultSet.getString(1);
        return count;
    }

    @Override
    public ArrayList<Item> Item() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT Description,QtyOnHand FROM Item");
        ArrayList<Item> allItems = new ArrayList<>();
        while (rst.next()) {
            allItems.add(new Item(rst.getString(1), rst.getInt(2)));
        }
        return allItems;
    }
}
