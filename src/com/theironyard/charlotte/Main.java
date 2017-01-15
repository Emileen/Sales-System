package com.theironyard.charlotte;

import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static User user;

    public static void main(String[] args) throws SQLException {
        HashMap<String, User> users = new HashMap<>();



        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        User.creatTable(conn);
        Order.creatTable(conn);
        Item.creatTable(conn);
        //ArrayList<User> user = new ArrayList<>();


        Spark.init();
        Spark.get("/",
                ((request, response) -> {
                    HashMap m = new HashMap();

                    //Session session = request.session();
                    //String userName = session.attribute("userName");

                    //User user = users.get(userName);

                    if (user == null) {
                        return new ModelAndView(m, "login.html");
                    } else {
                        User.insertUser(conn,request.queryParams("name"),request.queryParams("email"));
                        return new ModelAndView(m, "order.html");
                    }
                }), new MustacheTemplateEngine()
        );

        Spark.post("/login",
                ((request, response) -> {
                    String name = request.queryParams("name");
                    User user = users.get(name);
                    if (user == null) {
                        User.insertUser(conn, request.queryParams("name"),
                                request.queryParams("email"));
                       // User.createItemId(conn,request.queryParams("id"));

                    }
                    Session session = request.session();
                    session.attribute("name", name);
                    response.redirect("/order");
                    return "";

                })
        );

        Spark.get("/order",
                ((request, response) -> {
            HashMap m = new HashMap();

            return new ModelAndView(m,"order.html");

        }));

        Spark.post("/order",
                ((request, response) -> {
                    Item.createItem(conn,
                            request.queryParams("name"),
                            Integer.valueOf(request.queryParams("quantity")),
                            Double.valueOf(request.queryParams("price")));

            return "";
        }));

    }
}




