package unibo.mydiet.model;

public class UserLogged {
    private final User user;

    public UserLogged(final User user) {
        this.user = user;
    }

    public UserType getType() {
        return user.getType();
    }

    public User getUser() {
        return user;
    }
}
