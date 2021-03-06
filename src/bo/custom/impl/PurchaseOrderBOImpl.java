package bo.custom.impl;

import bo.custom.PurchaseOrderBO;
import dao.DAOFactory;
import dao.custom.QueryDAO;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import db.DBConnection;
import entity.Customer;
import entity.Item;
import entity.OrderDetail;
import entity.Orders;
import javafx.scene.control.Alert;
import model.CustomerDTO;
import model.ItemDTO;
import model.OrderDTO;
import model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public boolean purchaseOrder(OrderDTO order, ArrayList<OrderDetailDTO> details) throws SQLException, ClassNotFoundException {
        Connection connection = null;

        connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);

        Orders orders=new Orders(order.getOrderID(),order.getCusID(),order.getOrderDate(),order.getTotal());

        boolean isOrderSaved = orderDAO.save(orders);

        if (isOrderSaved) {


            boolean isDetailsSaved = false;
            for (OrderDetailDTO detail : details) {

                OrderDetail orderDetail=new OrderDetail(detail.getOrderID(),detail.getItemCode(),detail.getOrderqty(),detail.getDiscount(),detail.getPrice());

                isDetailsSaved = orderDetailsDAO.save(orderDetail);

                //**  quantity update *//*
                updateQty(detail.getItemCode(), detail.getOrderqty());
            }
            if (isDetailsSaved) {
                connection.commit();
                new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully...!").showAndWait();
            } else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Error...!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Error...!").show();
        }
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer ent = customerDAO.search(id);
        return new CustomerDTO(ent.getCusID(), ent.getCusTitle(), ent.getCusName(),ent.getCusAddress(),ent.getCity(),ent.getProvince(),ent.getPostCode());
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item ent = itemDAO.search(code);
        return new ItemDTO(ent.getCode(),ent.getDescription(),ent.getPackageSize(),ent.getUnitPrice(),ent.getQtyOnHand());
    }

    @Override
    public boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        for (Customer ent : all) {
            allCustomers.add(new CustomerDTO(ent.getCusID(), ent.getCusTitle(), ent.getCusName(), ent.getCusAddress(), ent.getCity(), ent.getProvince(), ent.getPostCode()));
        }
        return allCustomers;
    }


    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item ent : all) {
            allItems.add(new ItemDTO(ent.getCode(), ent.getDescription(),ent.getPackageSize(),ent.getUnitPrice(),ent.getQtyOnHand()));
        }
        return allItems;
    }

    @Override
    public boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return itemDAO.updateQty(itemCode, qty);

    }
}
