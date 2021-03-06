package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.OrderDetailsDAO;
import entity.Customer;
import entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO `Order Details` VALUES(?,?,?,?,?)",
                entity.getOrderID(), entity.getItemCode(), entity.getOrderqty(), entity.getDiscount(), entity.getPrice());

    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<OrderDetail> orderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT OrderID FROM `Order Details` group by OrderID");
        ArrayList<OrderDetail> id = new ArrayList<>();
        while (rst.next()) {
            id.add(new OrderDetail(rst.getString(1)));
        }
        return id;
    }

    @Override
    public ArrayList<OrderDetail> order(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM  `Order Details` WHERE OrderID=?", id);
        ArrayList<OrderDetail> allOrder = new ArrayList<>();
        while (rst.next()) {
            allOrder.add(new OrderDetail(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4), rst.getDouble(5)));
        }

        return allOrder;
    }
}
