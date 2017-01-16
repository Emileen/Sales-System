package com.theironyard.charlotte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by emileenmarianayagam on 1/12/17.
 */
public class Order {
    private Integer id;
    private int userId;
    private List<Item> items;


    public Order() {
    }

    public Order(Integer id, int userId) {
        this.id = id;
        this.userId = userId;
    }

    public Order(int userId) {
        this.userId = userId;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static void creatTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS orders (id IDENTITY, user_id int)");
    }

    public static void createItemId(Connection conn, User user ) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders values (NULL, ?)");
        stmt.setInt(1, user.getId() );
        stmt.execute();
    }






}
