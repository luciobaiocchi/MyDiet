package unibo.mydiet.model;

public record Nutrizionist(String specializzazione,
                           String nome,
                           String cognome,
                           String username,
                           String password,
                           int numeroTelefono,
                           int eta,
                           String mail,
                           char sesso,
                           int percentualeSoddisfatti,
                           int mediaStelle) implements User {
    public Nutrizionist {
        if (specializzazione == null || specializzazione.isBlank()) {
            throw new IllegalArgumentException("Specializzazione cannot be null or empty");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome cannot be null or empty");
        }
        if (cognome == null || cognome.isBlank()) {
            throw new IllegalArgumentException("Cognome cannot be null or empty");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (mail == null || mail.isBlank()) {
            throw new IllegalArgumentException("Mail cannot be null or empty");
        }
    }

    @Override
    public UserType getType() {
        return UserType.NUTRIZIONIST;
    }
}
