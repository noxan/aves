package com.github.noxan.aves.auth.accessor;

public class UsernamePassword implements UsernamePasswordAccessor {
    private String username;
    private String password;

    public UsernamePassword(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
