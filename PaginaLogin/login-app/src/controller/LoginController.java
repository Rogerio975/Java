package controller;

import model.User;

public class LoginController {
    private User user;

    public LoginController(User user) {
        this.user = user;
    }

    public boolean authenticateUser(String username, String password) {
        return user.getUsername().equals(username) && user.getPassword().equals(password);
    }
}