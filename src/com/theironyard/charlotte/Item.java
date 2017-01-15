package com.theironyard.charlotte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by emileenmarianayagam on 1/12/17.
 */
public class Item {
    Integer id;
    String name;
    int quantity;
    double price;
    int orderId;

    public Item() {
    }

    public Item(Integer id, String name, int quantity, double price, int orderId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.orderId = orderId;
    }

    public Item(String name, int quantity, double price, int orderId) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public static void creatTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS items (id IDENTITY, name VARCHAR, quantity int, price double, order_id int)");
    }

    public static void createItem(Connection conn, String name, int quantity,double price) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO items VALUES (NULL, ?, ?, ?)");
        stmt.setString(1, name);
        stmt.setInt(2,quantity);
        stmt.setDouble(3,price);
        stmt.execute();
    }
}
