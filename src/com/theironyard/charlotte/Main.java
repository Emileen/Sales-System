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
import java.util.HashMap;

public class Main {
    private static void initializeDatabase() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();
        stmt.execute("create table if not exists users  (id identity, name varchar, email varchar)");
        stmt.execute("create table if not exists orders (id identity, user_id int)");
        stmt.execute("create table if not exists items  (id identity, name varchar, quantity int, price double, order_id int)");
    }

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();

        Spark.get("/", (request, response) -> {
            HashMap model = new HashMap();
            Session session = request.session();

            Integer userId = session.attribute("user_id");
            if (userId != null) {
                // look up user details by id
                // pass user into model
                return new ModelAndView(model, "home.html");
            } else {
                return new ModelAndView(model, "login.html");
            }
        }, new MustacheTemplateEngine());

        Spark.post("/login", (request, response) -> {
            String email = request.queryParams("email");

            // look up the user by email address
            // if the user exists, save the id in session.

            response.redirect("/");
            return "";
        });

        initializeDatabase();
    }
}
