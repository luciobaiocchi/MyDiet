package unibo.mydiet.model;

public record Client(String phoneNumber,
                     String email,
                     String age,
                     String Username,
                     String name,
                     String surname,
                     String password,
                     String sex) {
    public Client {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (age == null || age.isBlank()) {
            throw new IllegalArgumentException("Age cannot be null or empty");
        }
        if (Username == null || Username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (surname == null || surname.isBlank()) {
            throw new IllegalArgumentException("Surname cannot be null or empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }

}
