package com.github.noxan.aves.auth.accessor;

public interface UsernamePasswordAccessor {
    public String getUsername();

    public boolean checkPassword(String password);
}
