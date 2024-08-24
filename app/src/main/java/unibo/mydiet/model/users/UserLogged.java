package unibo.mydiet.model.users;

import java.util.Optional;

public class UserLogged {
    private  User user;
    private final boolean isLogged;

    public UserLogged(final User user) {
        this.user = user;
        isLogged = true;
    }

    public UserType getType() {
        return user.getType();
    }

    public Client getCli() {
        return (Client) user;
    }

    public Nutrizionist getNut() {
        return (Nutrizionist) user;
    }

    public boolean isLogged() {
        return isLogged;
    }

}
