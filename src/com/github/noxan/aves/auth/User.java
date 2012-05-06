package com.github.noxan.aves.auth;

public class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User) {
            User user = (User)obj;
            return getUsername().equals(user.getUsername());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "User(" + username + ")";
    }
}
