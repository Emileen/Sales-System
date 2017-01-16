package com.theironyard.charlotte;

import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    static User user;

    private static Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:h2:./main");
    }


    private static List<Order> getOrdersForUser(Integer userId) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement("select * from orders where user_id = ?");
        ArrayList<Order> order = new ArrayList<>();

        return new ArrayList<>();

    }

    public static ArrayList<Item> selectRestaurant(Connection conn) throws SQLException {
        //creates an arraylist of restaurant
        ArrayList<Item> items = new ArrayList<>();
        Statement stmt = conn.createStatement();
        //A table of data representing a database result set, which is usually generated by executing a
        // statement that queries the database.
        ResultSet results = stmt.executeQuery("SELECT * FROM items");
        //as long are there is a next then load the values with the info that is in the table
        while (results.next()) {
            int id = results.getInt("id");
            String name = results.getString("name");
            int quantity = results.getInt("quantity");
            double price = results.getDouble("price");
            int orderId = results.getInt("order_Id");
            items.add(new Item(id, name,quantity,price,orderId));
        }
        return items;
    }

    public static void createItemId(Connection conn, User user ) throws SQLException {
        int id = User.selectId(getConnection(),user.name, user.email );
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders values (NULL, ?)");
        stmt.setInt(1, id );
        stmt.execute();
    }

    private static User getUserById(Integer id) throws SQLException {
        User user = null;
        if (id != null){
            PreparedStatement stmt = getConnection().prepareStatement("select *from users where id = ?");
            stmt.setInt(1,id);
            ResultSet results = stmt.executeQuery();

            if (results.next()){
                user = new User(id,results.getString("name"), results.getString("email"));
                user.setOrders(getOrdersForUser(id));
            }
        }
        return user;
    }

    private static Integer getUserByEmail(String email) throws SQLException {
        Integer userId = null;

        if (email != null) {
            PreparedStatement stmt = getConnection().prepareStatement("select * from users where email = ?");
            stmt.setString(1, email);

            ResultSet results = stmt.executeQuery();

            if (results.next()) {
                userId = results.getInt("id");
            }
        }
        return userId;
    }

    public static void main(String[] args) throws SQLException {
        HashMap<String, User> users = new HashMap<>();

        Server.createWebServer().start();
        User.creatTable(getConnection());
        Order.creatTable(getConnection());
        Item.creatTable(getConnection());

        Spark.init();
        Spark.get("/",
                ((request, response) -> {
                    HashMap m = new HashMap();

                    Session session = request.session();
                    String userName = session.attribute("name");

                    User current = getUserById(session.attribute("user_id"));
                    User user = users.get(userName);

                 if (user == null) {
                       // return new ModelAndView(m, "login.html");
                        return new ModelAndView(m, "login.html");
                    } else {
                        User.insertUser(getConnection(),request.queryParams("name"),request.queryParams("email"));
                        return new ModelAndView(m, "order.html");
                    }
                }), new MustacheTemplateEngine()
        );

        Spark.post("/login",
                ((request, response) -> {
                    String name = request.queryParams("name");
                    User user = users.get(name);
                    if (user == null) {
                        User.insertUser(getConnection(), request.queryParams("name"),
                                request.queryParams("email"));

                       // User.createItemId(conn,request.queryParams("id"));

                    }
                    Session session = request.session();
                    session.attribute("name", name);
                    response.redirect("/order");
                    return "";

                })
        );

        int id = User.selectId(getConnection(),"name", "email" );
        System.out.println(id);

        Spark.post("/order",
                ((request, response) -> {
                    Item.createItem(getConnection(),
                            request.queryParams("name"),
                            Integer.valueOf(request.queryParams("quantity")),
                            Double.valueOf(request.queryParams("price")));

                    return "";
                }));

        Spark.get("/order",
                ((request, response) -> {
            HashMap m = new HashMap();

            m.put("items", selectRestaurant(getConnection()));

            return new ModelAndView(m,"order.html");

        }),
                new MustacheTemplateEngine()

        );

        Spark.get("/home",
                ((request, response) -> {
            HashMap m = new HashMap();
            return new ModelAndView(m,"Home.html");

        }),
                new MustacheTemplateEngine());



    }
}




