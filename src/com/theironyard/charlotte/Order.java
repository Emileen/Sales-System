package com.theironyard.charlotte;

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
}
