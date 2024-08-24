package unibo.mydiet.model.users;

public record Client(String numeroTelefono,
                     String mail,
                     String eta,
                     String username,
                     String nome,
                     String  cognome,
                     String password,
                     String sesso) implements User {
    public Client {
        if (numeroTelefono == null || numeroTelefono.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (mail == null || mail.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (eta == null || eta.isBlank()) {
            throw new IllegalArgumentException("Age cannot be null or empty");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (cognome == null || cognome.isBlank()) {
            throw new IllegalArgumentException("Surname cannot be null or empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }

    @Override
    public UserType getType() {
        return UserType.CLIENT;
    }
}
