package unibo.mydiet.DB;

import unibo.mydiet.model.Aggiornamento;
import unibo.mydiet.model.Goal;
import unibo.mydiet.model.diet.*;
import unibo.mydiet.model.users.Client;
import unibo.mydiet.model.users.Nutrizionist;
import unibo.mydiet.model.users.PercorsoFormazione;
import unibo.mydiet.model.users.Tariffa;

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
            stmt.setString(1, nutrizionist.getSpecializzazione());
            stmt.setString(2, String.valueOf(nutrizionist.getNumeroTelefono()));
            stmt.setString(3, nutrizionist.getMail());
            stmt.setString(4, nutrizionist.getUsername());
            stmt.setString(5, nutrizionist.getNome());
            stmt.setString(6, nutrizionist.getCognome());
            stmt.setString(7, nutrizionist.getPassword());
            stmt.setString(8, String.valueOf(nutrizionist.getSesso()));
            stmt.setString(9, String.valueOf(nutrizionist.getPercentualeSoddisfatti()));
            stmt.setString(10, String.valueOf(nutrizionist.getMediaStelle()));

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

    public List<Tariffa> getNutTarif(final String username) throws SQLException {
        final String query = "SELECT Durata_mesi, Prezzo FROM MyDiet.TARIFFA WHERE Username = ?";
        List<Tariffa> tariffe = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int durata = rs.getInt("Durata_mesi");
                    double prezzo = rs.getDouble("Prezzo");
                    tariffe.add(new Tariffa(durata, prezzo));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return tariffe;
    }
    // Client Info
    public String getNutPsw(final String username) throws SQLException {
        final String query = "SELECT Password FROM MyDiet.NUTRIZIONISTA\n" +
                "WHERE Username = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Password");
                }
            }
        }
        catch (SQLException e){
            return null;
        }
        return null;
    }
    // Client Info
    public String getCliPsw(final String username) throws SQLException {
        final String query = "SELECT Password FROM MyDiet.CLIENTE\n" +
                "WHERE Username = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Password");
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

    // Client Info
    public List<PercorsoFormazione> getNutFormation(final String username) throws SQLException {
        final String query = "SELECT * FROM MyDiet.PERCORSO_DI_FORMAZIONE\n" +
                "WHERE Username = ?;";
        List<PercorsoFormazione> percorsoFormaziones = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    percorsoFormaziones.add(new PercorsoFormazione(
                            rs.getString("Nome_percorso"),
                            rs.getString("Data_inizio"),
                            rs.getString("Data_fine"),
                            rs.getString("Voto_conseguito")
                    ));
                }
            }
        }
        catch (SQLException e){
            return null;
        }
        return percorsoFormaziones;
    }

    public boolean setNutPsw(final String password, final String username) throws SQLException {
        final String query = "UPDATE NUTRIZIONISTA SET Password = ? WHERE Username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, password);
            stmt.setString(2, username);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateNutTariff(final String username, final double prezzo, final int durata) {
    final String query = "UPDATE MyDiet.TARIFFA SET Prezzo = ? WHERE Username = ? AND Durata_mesi = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setDouble(1, prezzo);
        stmt.setString(2, username);
        stmt.setInt(3, durata);
        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
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

    public boolean addPercorsoFormazione(PercorsoFormazione percorso, String username) {
        final String query = "INSERT INTO MyDiet.PERCORSO_DI_FORMAZIONE (Nome_percorso, Data_inizio, Data_fine, Voto_conseguito, Username) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, percorso.nome());
            stmt.setString(2, percorso.dataInizio());
            stmt.setString(3, percorso.dataFine());
            stmt.setString(4, percorso.voto());
            stmt.setString(5, username);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {;
            return false;
        }
    }

    public List<String> getClientUsernames(String nutritionistUsername) throws SQLException {
        final String query = "SELECT DISTINCT Username FROM MyDiet.DIETA WHERE SVI_Username = ?";
        List<String> clientUsernames = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nutritionistUsername);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clientUsernames.add(rs.getString("Username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return clientUsernames;
    }

    public List<Aggiornamento> getUserAggiornamento(String username) throws SQLException {
        final String query = "SELECT * FROM MyDiet.AGGIORNAMENTO WHERE Username = ?";
        List<Aggiornamento> aggiornamenti = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Aggiornamento aggiornamento = new Aggiornamento(
                            rs.getString("Data"),
                            rs.getString("Descrizione"),
                            rs.getString("Peso"),
                            rs.getString("Circ_punto_vita"),
                            rs.getString("Circ_braccio"),
                            rs.getString("Circ_gambe")
                    );
                    aggiornamenti.add(aggiornamento);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return aggiornamenti;
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

    public void updateRicetta(final String clientUsername, final NomeGiorno giorno, final NomePasto pasto, final Ricetta ricetta) {
        removeCreazione(clientUsername, giorno.toString(), pasto.toString());
        removeRicetta(clientUsername, giorno.toString(), pasto.toString());

        final String insertRicettaQuery = """
        INSERT INTO RICETTA (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, Difficolta, Descrizione, Tempo_Preparazione)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?);
    """;

        try (PreparedStatement stmtRicetta = connection.prepareStatement(insertRicettaQuery)) {
            // Inserimento in RICETTA
            stmtRicetta.setString(1, clientUsername);
            stmtRicetta.setString(2, getDietStartDate(clientUsername)); // Data di esempio, sostituire con la data corretta
            stmtRicetta.setString(3, giorno.toString());
            stmtRicetta.setString(4, pasto.toString());
            stmtRicetta.setString(5, ricetta.getNome());
            stmtRicetta.setString(6, ricetta.getDifficolta());
            stmtRicetta.setString(7, ricetta.getProcedimento());
            stmtRicetta.setString(8, ricetta.getTempoPreparazione());
            stmtRicetta.executeUpdate();
            addAlimentiToCreazione(clientUsername, getDietStartDate(clientUsername), giorno.toString(), pasto.toString(), ricetta.getNome(), ricetta.getIngredienti());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addAlimentiToCreazione(String username, String dataInizio, String giorno, String pasto, String nome, List<Alimento> alimenti) {
    final String insertCreazioneQuery = """
        INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
        VALUES (?, ?, ?, ?, ?, ?, ?);
    """;

    try (PreparedStatement stmt = connection.prepareStatement(insertCreazioneQuery)) {
        for (Alimento alimento : alimenti) {
            stmt.setString(1, username);
            stmt.setString(2, dataInizio);
            stmt.setString(3, giorno);
            stmt.setString(4, pasto);
            stmt.setString(5, nome);
            stmt.setString(6, alimento.getIdAlimento());
            stmt.setInt(7, alimento.getPeso());
            stmt.addBatch();
        }
        stmt.executeBatch();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    private void removeCreazione(String username, String giorno, String pasto) {
        final String deleteQuery = """
        DELETE FROM MyDiet.CREAZIONE 
        WHERE INC_COM_Username = ? 
        AND INC_COM_Nome = ? 
        AND INC_Nome = ? 
        AND INC_COM_Data_inizio = (
            SELECT MAX(INC_COM_Data_inizio) 
            FROM MyDiet.RICETTA 
            WHERE INC_COM_Username = ?
        );
    """;

        try (PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setString(1, username);
            stmt.setString(2, giorno);
            stmt.setString(3, pasto);
            stmt.setString(4, username);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void removeRicetta(String username, String giorno, String pasto) {
        final String deleteQuery = """
            DELETE FROM MyDiet.RICETTA
            WHERE INC_COM_Username = ?
            AND INC_COM_Nome = ?
            AND INC_Nome = ?
            ORDER BY INC_COM_Data_inizio DESC;
        """;

        try (PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setString(1, username);
            stmt.setString(2, giorno);
            stmt.setString(3, pasto);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getDietStartDate(final String clientUsername) {
        final String query = """
            SELECT Data_inizio FROM MyDiet.DIETA
            WHERE Username = ?
            ORDER BY Data_inizio desc
            LIMIT 1;
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, clientUsername);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Data_inizio");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Alimento> getAlimList() throws SQLException {
        List<Alimento> alimenti = new ArrayList<>();
        String query = "SELECT * FROM MyDiet.ALIMENTO";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String idAlimento = resultSet.getString("IdAlimento");
                String nome = resultSet.getString("Nome");
                ValoriNutrizionali valoriNutrizionali = new ValoriNutrizionali(
                        resultSet.getInt("Grammi_proteine"),
                        resultSet.getInt("Grammi_grassi"),
                        resultSet.getInt("Grammi_carboidrati"),
                        resultSet.getInt("Calorie_totali")
                );

                Alimento alimento = new Alimento(idAlimento, 0, nome, valoriNutrizionali);
                alimenti.add(alimento);
            }
        }

    return alimenti;
}

    public Alimento getAlimentoById(String id) {
        final String query = "SELECT * FROM MyDiet.ALIMENTO WHERE IdAlimento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String idAlimento = rs.getString("IdAlimento");
                    String nome = rs.getString("Nome");
                    ValoriNutrizionali valoriNutrizionali = new ValoriNutrizionali(
                            rs.getInt("Grammi_proteine"),
                            rs.getInt("Grammi_grassi"),
                            rs.getInt("Grammi_carboidrati"),
                            rs.getInt("Calorie_totali")
                    );
                    return new Alimento(idAlimento, 0, nome, valoriNutrizionali);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveClientUpdate(String username, String data, String descrizione, int peso, int circPuntoVita, int circBraccio, int circGambe) {
    final String insertUpdateQuery = """
        INSERT INTO AGGIORNAMENTO (Username, Data, Descrizione, Peso, Circ_punto_vita, Circ_braccio, Circ_gambe)
        VALUES (?, ?, ?, ?, ?, ?, ?);
    """;

    try (PreparedStatement stmt = connection.prepareStatement(insertUpdateQuery)) {
        stmt.setString(1, username);
        stmt.setString(2, data);
        stmt.setString(3, descrizione);
        stmt.setInt(4, peso);
        stmt.setInt(5, circPuntoVita);
        stmt.setInt(6, circBraccio);
        stmt.setInt(7, circGambe);
        stmt.executeUpdate();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}


