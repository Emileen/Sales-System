package com.theironyard.charlotte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by emileenmarianayagam on 1/12/17.
 */
public class Order {
    Integer id;
    int userId;

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



}
