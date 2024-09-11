package unibo.mydiet.model.users;

public class Nutrizionist implements User {
    private String specializzazione;
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private String numeroTelefono;
    private String mail;
    private String sesso;
    private String percentualeSoddisfatti;
    private String mediaStelle;

    public Nutrizionist(String specializzazione, String nome, String cognome, String username, String password, String numeroTelefono, String mail, String sesso, String percentualeSoddisfatti, String mediaStelle) {
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
        this.specializzazione = specializzazione;
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.numeroTelefono = numeroTelefono;
        this.mail = mail;
        this.sesso = sesso;
        this.percentualeSoddisfatti = percentualeSoddisfatti;
        this.mediaStelle = mediaStelle;
    }

    public String getSpecializzazione() {
        return specializzazione;
    }

    public void setSpecializzazione(String specializzazione) {
        if (specializzazione == null || specializzazione.isBlank()) {
            throw new IllegalArgumentException("Specializzazione cannot be null or empty");
        }
        this.specializzazione = specializzazione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome cannot be null or empty");
        }
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        if (cognome == null || cognome.isBlank()) {
            throw new IllegalArgumentException("Cognome cannot be null or empty");
        }
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        if (mail == null || mail.isBlank()) {
            throw new IllegalArgumentException("Mail cannot be null or empty");
        }
        this.mail = mail;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getPercentualeSoddisfatti() {
        return percentualeSoddisfatti;
    }

    public void setPercentualeSoddisfatti(String percentualeSoddisfatti) {
        this.percentualeSoddisfatti = percentualeSoddisfatti;
    }

    public String getMediaStelle() {
        return mediaStelle;
    }

    public void setMediaStelle(String mediaStelle) {
        this.mediaStelle = mediaStelle;
    }

    @Override
    public UserType getType() {
        return UserType.NUTRIZIONIST;
    }
}