package com.example.controller;

import com.example.model.User;

public class LoginController {
    private User user;

    public LoginController() {
        user = new User();
    }

    public void handleLogin(String username, String password) {
        user.setUsername(username);
        user.setPassword(password);
        // Implement authentication logic here
    }
}