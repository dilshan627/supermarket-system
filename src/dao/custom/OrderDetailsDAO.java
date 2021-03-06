package dao.custom;

import dao.CrudDAO;
import entity.OrderDetail;
import model.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsDAO extends CrudDAO<OrderDetail,String> {

    ArrayList<OrderDetail> orderId() throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetail> order(String id) throws SQLException, ClassNotFoundException;
}
