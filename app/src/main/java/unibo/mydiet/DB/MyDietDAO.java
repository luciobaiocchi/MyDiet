package unibo.mydiet.DB;

import unibo.mydiet.model.Goal;
import unibo.mydiet.model.diet.Alimento;
import unibo.mydiet.model.diet.Dieta;
import unibo.mydiet.model.diet.Ricetta;
import unibo.mydiet.model.diet.ValoriNutrizionali;
import unibo.mydiet.model.users.Client;
import unibo.mydiet.model.users.Nutrizionist;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyDietDAO implements AutoCloseable {
    private final Connection connection;

    public MyDietDAO() {
        try {
            // Carica il driver JDBC (necessario solo per versioni precedenti a JDBC 4.0)

            String DB_URL = "jdbc:mysql://localhost:3306/MyDiet?serverTimezone=UTC";
            String USER = "root";
            String PASS = "123456";
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }


    // 1. Registrazione di un nuovo utente
    public boolean registerClient(final String nome,
                                    final String cognome,
                                    final String username,
                                    final String password,
                                    final int numeroTelefono,
                                    final int eta,
                                    final String mail,
                                    final char sesso){

        String query = "INSERT INTO CLIENTE (Numero_di_telefono, Mail, Eta, Username, " +
                "Nome_, Cognome, Password, Sesso) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(numeroTelefono));
            stmt.setString(2, mail);
            stmt.setString(3, String.valueOf(eta));
            stmt.setString(4, username);
            stmt.setString(5, nome);
            stmt.setString(6, cognome);
            stmt.setString(7, password);
            stmt.setString(8, String.valueOf(sesso));

            stmt.executeUpdate();  // Esegui l'operazione di inserimento
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 1. Registrazione di un nuovo utente
    public boolean registerNutrizionist(final Nutrizionist nutrizionist) throws SQLException {
        String query =
                "INSERT INTO NUTRIZIONISTA (Specializzazione, Numero_di_telefono, Mail, Username, Nome_, " +
                "Cognome, Password, Sesso, Percentuale_soddisfatti, Media_stelle) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nutrizionist.specializzazione());
            stmt.setString(2, String.valueOf(nutrizionist.numeroTelefono()));
            stmt.setString(3, nutrizionist.mail());
            stmt.setString(4, nutrizionist.username());
            stmt.setString(5, nutrizionist.nome());
            stmt.setString(6, nutrizionist.cognome());
            stmt.setString(7, nutrizionist.password());
            stmt.setString(8, String.valueOf(nutrizionist.sesso()));
            stmt.setString(9, String.valueOf(nutrizionist.percentualeSoddisfatti()));
            stmt.setString(10, String.valueOf(nutrizionist.mediaStelle()));

            stmt.executeUpdate();  // Esegui l'operazione di inserimento
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 2. Accesso di un utente
    public boolean loginClient(final String username, final String password) throws SQLException {
        String query = "SELECT Password FROM CLIENTE WHERE Username = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getString("Password").equals(password);
            }catch (SQLException e){
                return false;
            }
        }
    }

    // 2. Accesso di un utente
    public boolean loginNut(final String username, final String password) throws SQLException {
        String query = "SELECT Password FROM NUTRIZIONISTA WHERE Username = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getString("Password").equals(password);
            }catch (SQLException e){
                return false;
            }
        }
    }

    // Client Info
    public Client getClientInfo(final String username) throws SQLException {
        final String query = "SELECT * FROM CLIENTE WHERE Username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                            rs.getString("Numero_di_telefono"),
                            rs.getString("Mail"),
                            rs.getString("Eta"),
                            rs.getString("Username"),
                            rs.getString("Nome_"),
                            rs.getString("Cognome"),
                            rs.getString("Password"),
                            rs.getString("Sesso")
                    );
                }
            }
        }
        catch (SQLException e){
            return null;
        }
        return null;
    }
    // Raw Diet
    public Dieta getRowDiet(final String username) throws SQLException {
        final String query = """
                SELECT * FROM MyDiet.DIETA where username = ?
                ORDER by Data_inizio DESC
                LIMIT 1;""";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Dieta(
                            rs.getString("Username"),
                            rs.getString("SVI_Username"),
                            rs.getString("Prezzo"),
                            rs.getString("Data_fine"),
                            rs.getString("Data_inizio"),
                            new HashMap<>()
                    );
                }
            }
        }
        catch (SQLException e){
            return null;
        }
        return null;
    }


    public Ricetta getRecipe(final String username, final String dataInizio, final String giorno, final String pasto) throws SQLException {
        final String query = """
                SELECT * FROM MyDiet.RICETTA
                where INC_COM_Username = ?
                and INC_COM_Data_inizio= ?
                and INC_COM_Nome=?
                and INC_Nome=?;""";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, dataInizio);
            stmt.setString(3, giorno);
            stmt.setString(4, pasto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Ricetta(null,
                            rs.getString("Nome"),
                            rs.getString("Difficolta"),
                            rs.getString("Descrizione"),
                            rs.getString("Tempo_Preparazione")
                    );
                }
            }
        }
        catch (SQLException e){
            return null;
        }
        return null;
    }

    public List<Alimento> getFoodInRecipe(final String username, final String dataInizio, final String giorno, final String pasto) throws SQLException {
        final String query1 = """
        SELECT
            c.IdAlimento,
            c.Peso,
            a.Nome AS NomeAlimento,
            a.Grammi_proteine,
            a.Grammi_carboidrati,
            a.Grammi_grassi,
            a.Calorie_totali
        FROM
            MyDiet.CREAZIONE c
        JOIN
            MyDiet.ALIMENTO a
        ON
            c.IdAlimento = a.IdAlimento
        WHERE
            c.INC_COM_Username = ?
            AND c.INC_COM_Data_inizio = ?
            AND c.INC_COM_Nome = ?
            AND c.INC_Nome = ? ;
    """;

        try (PreparedStatement stmt = connection.prepareStatement(query1)) {
            List<Alimento> aliments = new ArrayList<>();
            stmt.setString(1, username);
            stmt.setString(2, dataInizio);
            stmt.setString(3, giorno);
            stmt.setString(4, pasto);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    aliments.add(new Alimento(
                            rs.getString("IdAlimento"), // Corretto da "IdAlimeno"
                            rs.getInt("Peso"), // Cambiato a int
                            rs.getString("NomeAlimento"),
                            new ValoriNutrizionali(
                                    rs.getInt("Grammi_proteine"), // Cambiato a int
                                    rs.getInt("Grammi_carboidrati"), // Cambiato a int
                                    rs.getInt("Grammi_grassi"), // Cambiato a int
                                    rs.getInt("Calorie_totali") // Cambiato a int
                            )
                    ));
                }
                return aliments;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    // Client Info
    public Nutrizionist getNutInfo(final String username) throws SQLException {
        final String query = "SELECT * FROM NUTRIZIONISTA WHERE Username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return buildNutrizionist(rs);
                }
            }
        }
        catch (SQLException e){
            return null;
        }
        return null;
    }

    // Client Goal
    public Goal getCliGoal(final String username) throws SQLException {
        final String query = "SELECT * FROM OBBIETTIVO WHERE Username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Goal(rs.getString("Data_raggiungimento"),
                            rs.getString("Raggiunto"),
                            rs.getString("Descrizione"),
                            rs.getString("Peso"),
                            rs.getString("Circ_braccio"),
                            rs.getString("Circ_gambe"),
                            rs.getString("Circ_punto_vita")
                    );
                }
            }
        }
        catch (SQLException e){
            return null;
        }
        return null;
    }

    // Client Info
    public List<Nutrizionist> getNutList() throws SQLException {
        final String query = "SELECT * FROM NUTRIZIONISTA";
        List<Nutrizionist> nutrizionists = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {;
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    nutrizionists.add(buildNutrizionist(rs));
                }
            }
        }
        catch (SQLException e){
            return null;
        }
        return nutrizionists;
    }


    //8. Visualizzare i 10 migliori nutrizionisti (quelli con la media di stelle più alta)


    public List<Nutrizionist> getHigerRating() throws SQLException {
        final String query = "SELECT * FROM NUTRIZIONISTA\n" +
                "ORDER BY Media_Stelle DESC\n" +
                "LIMIT 10;";
        List<Nutrizionist> nutrizionists = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {;
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    nutrizionists.add(buildNutrizionist(rs));
                }
            }
        }
        catch (SQLException e){
            return null;
        }
        return nutrizionists;
    }

//9. Ottenere una lista con i nutrizionisti che hanno portato all’obiettivo almeno il 50% dei clienti

    public List<Nutrizionist> getMoreSatisfied() throws SQLException {
        final String query = "SELECT *\n" +
                "FROM NUTRIZIONISTA\n" +
                "WHERE Percentuale_soddisfatti >= 50;";
        List<Nutrizionist> nutrizionists = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {;
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    nutrizionists.add(buildNutrizionist(rs));
                }
            }
        }
        catch (SQLException e){
            return null;
        }
        return nutrizionists;
    }



    private Nutrizionist buildNutrizionist(ResultSet rs) throws SQLException {
        return new Nutrizionist(
                rs.getString("Specializzazione"),
                rs.getString("Nome_"),
                rs.getString("Cognome"),
                rs.getString("Username"),
                rs.getString("Password"),
                rs.getString("Numero_di_telefono"),
                rs.getString("Mail"),
                rs.getString("Sesso"),
                rs.getString("Percentuale_soddisfatti"),
                rs.getString("Media_stelle")
        );
    }

}


