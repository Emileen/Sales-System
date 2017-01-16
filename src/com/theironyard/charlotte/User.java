package com.theironyard.charlotte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emileenmarianayagam on 1/12/17.
 */
public class User {
    Integer id;
    String name;
    String email;
    private List<Order> orders;


    public User() {
    }

    public User(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void creatTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY, name VARCHAR, email VARCHAR)");
    }

    public static void insertUser(Connection conn, String name, String email) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement ("insert into users values (NULL, ?, ?)");
        stmt.setString(1,name);
        stmt.setString(2,email);
        stmt.execute();
    }

    public static int selectId(Connection conn, String name, String email) throws SQLException {
        Integer id = 0;

        PreparedStatement stmt = conn.prepareStatement("Select id from users where name = ? and email = ? ");
        stmt.setString(1,name);
        stmt.setString(2,email);

        stmt.execute();
        ResultSet results = stmt.executeQuery();

        if(results.next()) {
          id  = results.getInt("user_Id");
        }

        return id;
    }

/*    public static ArrayList<Item> listOfItems(Connection conn) throws SQLException{
        ArrayList<Item> items = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("select * from items");
        while (results.next()){

        }
        return items;
    }*/

}
