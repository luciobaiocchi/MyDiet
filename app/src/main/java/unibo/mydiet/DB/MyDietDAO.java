package unibo.mydiet.DB;

import unibo.mydiet.model.users.Client;
import unibo.mydiet.model.users.Nutrizionist;

import java.sql.*;

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

    // Client Info
    public Nutrizionist getNutInfo(final String username) throws SQLException {
        final String query = "SELECT * FROM NUTRIZIONISTA WHERE Username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
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
        }
        catch (SQLException e){
            return null;
        }
        return null;
    }

//    // Account
//    public User getAccount(final String username) throws SQLException {
//        String query = "SELECT Nome, Cognome, Targhetta FROM account "
//                + "LEFT JOIN utente ON account.Username = utente.Username WHERE account.Username = ? ";
//
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, username);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    if (getAccountType(username) == AccountType.ADMIN) {
//                        return new User(rs.getString("Nome"),
//                                rs.getString("Cognome"),
//                                username,
//                                "Amministratore"
//                        );
//                    }else {
//                        return new User(rs.getString("Nome"),
//                                rs.getString("Cognome"),
//                                username,
//                                Objects.requireNonNullElse(rs.getString("Targhetta"), "N/A")
//                        );
//                    }
//                }
//            } catch (SQLException e) {
//                throw new SQLException("Error while getting account");
//            }
//
//        }
//        return null;
//    }

//    public String[] getCompetitions() {
//        final String query = "SELECT Nome FROM TIPO_COMPETIZIONE";
//        final List<String> competitions = new ArrayList<>();
//        competitions.add("Seleziona una competizione...");
//
//        try (PreparedStatement stmt = connection.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                competitions.add(rs.getString("Nome"));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return competitions.toArray(new String[0]);
//    }
//
//    public List<String> searchPlayer(final String name, final String nationality){
//
//        return null;
//    }

    // 4. Aggiunta di un nuovo calciatore
//    public void addPlayer(String nome, String cognome, String cf, Date dataNascita, int altezza, String luogoNascita, String piedePreferito) throws SQLException {
//        //String query = "INSERT INTO CALCIATORE (Nome, Cognome, CF, Data_di_nascita, Altezza, Luogo_di_nascita, Piede_preferito) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, nome);
//            stmt.setString(2, cognome);
//            stmt.setString(3, cf);
//            stmt.setDate(4, dataNascita);
//            stmt.setInt(5, altezza);
//            stmt.setString(6, luogoNascita);
//            stmt.setString(7, piedePreferito);
//            stmt.executeUpdate();
//        }
//    }
//
//    // 5. Invio richieste da parte degli utenti
//    public void sendRequest(final String username,
//                            final String codiceRichiesta,
//                            final String Tipologia,
//                            final String Descrizione) throws SQLException {
//        String query = "INSERT INTO RICHIESTE (Username, CodiceRichiesta,Tipologia, Stato, Descrizione) VALUES (?, ?, ?, ?, ?)";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, username);
//            stmt.setString(2, codiceRichiesta);
//            stmt.setString(3, Tipologia);
//            stmt.setString(4, "In attesa");
//            stmt.setString(5, Descrizione);
//            stmt.executeUpdate();
//        }
//    }
//
//    // 6. Rimozione richieste già visionate da parte dell’amministratore
//    public void removeViewedRequests() throws SQLException {
//        String query = "DELETE FROM RICHIESTE WHERE Stato = 'Visionata'";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.executeUpdate();
//        }
//    }
//
//    // 7. Modifica dell’amministratore di una statistica di un calciatore in una stagione
//    public void updatePlayerStats(String cf, int gol, int assist, String annoCalcistico) throws SQLException {
//        String query = "UPDATE stats_giocatore_stagione SET Goal_stagionali = ?, Assist_stagionali = ? WHERE CF_Calciatore = ? AND AnnoCalcistico = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setInt(1, gol);
//            stmt.setInt(2, assist);
//            stmt.setString(3, cf);
//            stmt.setString(4, annoCalcistico);
//            stmt.executeUpdate();
//        }
//    }
//
//    // 8. Visualizzazione delle statistiche complessive della carriera di un calciatore
//    public void viewCareerStats(String cf) throws SQLException {
//        String query = "SELECT SUM(Goal_stagionali) AS GolTotali, SUM(Goal_stagionali) AS AssistTotali FROM stats_giocatore_stagione WHERE CF_Calciatore = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, cf);
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    System.out.println("GolTotali: " + rs.getInt("GolTotali"));
//                    System.out.println("AssistTotali: " + rs.getInt("AssistTotali"));
//                }
//            }
//        }
//    }
//
//    // 9. Modifica dell’amministratore di una statistica di una squadra
//    public void updateTeamStats(final String nomeSquadra, int nomeStatistica, String annoCalcistico) throws SQLException {
//        String query = "UPDATE squadra SET Record_goal WHERE  password()= ?";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setInt(1, nomeStatistica);
//            stmt.setString(2, nomeSquadra);
//            stmt.setString(3, annoCalcistico);
//            stmt.executeUpdate();
//        }
//    }
//
//    // 10. Assegnazione di una targhetta da parte di un amministratore ad un utente
//    public void assignBadge(String username, String tipoTarghetta) throws SQLException {
//        String query = "ALTER TABLE utente ADD COLUMN tipoTarghetta VARCHAR(255)";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, username);
//            stmt.setString(2, tipoTarghetta);
//            stmt.executeUpdate();
//        }
//    }
//
//    // 11. Visualizzazione delle statistiche di un calciatore in una determinata stagione
//    public void viewPlayerStatsInSeason(String cf, String annoCalcistico) throws SQLException {
//        String query = "SELECT * FROM stats_giocatore_stagione WHERE CF_Calciatore = ? AND AnnoCalcistico = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, cf);
//            stmt.setString(2, annoCalcistico);
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    // Output the statistics
//                }
//            }
//        }
//    }
//
//    // 12. Visualizzazione delle statistiche di un calciatore in una determinata competizione
//    public void viewPlayerStatsInCompetition(String cf, String codiceCompetizione) throws SQLException {
//        String query = "SELECT * FROM STATISTICHE WHERE CF_Calciatore = ? AND CodiceCompetizione = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, cf);
//            stmt.setString(2, codiceCompetizione);
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    // Output the statistics
//                }
//            }
//        }
//    }
//
//    // 13. Visualizzazione classifica giocatori con valore di mercato più alto in una determinata stagione
//    public void viewTopPlayersByMarketValue(String annoCalcistico) throws SQLException {
//        String query = "SELECT CF_Calciatore, ValoreMercato FROM STATISTICHE WHERE AnnoCalcistico = ? ORDER BY ValoreMercato DESC LIMIT 10";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, annoCalcistico);
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    // Output the player information
//                }
//            }
//        }
//    }
//
//    // 14. Visualizzazione della classifica finale di una competizione in una determinata stagione
//    public void viewFinalCompetitionRanking(String codiceCompetizione, String annoCalcistico) throws SQLException {
//        String query = "SELECT NomeSquadra, Posizione FROM CLASSIFICHE WHERE CodiceCompetizione = ? AND AnnoCalcistico = ? ORDER BY Posizione ASC LIMIT 4";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, codiceCompetizione);
//            stmt.setString(2, annoCalcistico);
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    // Output the ranking information
//                }
//            }
//        }
//    }
//
//    // 15. Visualizzazione migliori giocatori della stagione (capocannoniere, assistman, etc.)
//    public void viewTopPlayersOfSeason(String annoCalcistico) throws SQLException {
//        String topScorerQuery = "SELECT CF_Calciatore, Gol FROM STATISTICHE WHERE AnnoCalcistico = ? ORDER BY Gol DESC LIMIT 1";
//        String topAssistmanQuery = "SELECT CF_Calciatore, Assist FROM STATISTICHE WHERE AnnoCalcistico = ? ORDER BY Assist DESC LIMIT 1";
//
//        try (PreparedStatement scorerStmt = connection.prepareStatement(topScorerQuery);
//             PreparedStatement assistmanStmt = connection.prepareStatement(topAssistmanQuery)) {
//            scorerStmt.setString(1, annoCalcistico);
//            assistmanStmt.setString(1, annoCalcistico);
//
//            try (ResultSet rs = scorerStmt.executeQuery()) {
//                while (rs.next()) {
//                    // Output the top scorer
//                }
//            }
//
//            try (ResultSet rs = assistmanStmt.executeQuery()) {
//                while (rs.next()) {
//                    // Output the top assistman
//                }
//            }
//        }
//    }
//
//    // 16. Visualizzazione classifica dei calciatori più pagati
//    public void viewHighestPaidPlayers() throws SQLException {
//        String query = "SELECT CF_Calciatore, Stipendio FROM CONTRATTI ORDER BY Stipendio DESC LIMIT 10";
//        try (PreparedStatement stmt = connection.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                // Output the highest paid players
//            }
//        }
//    }
//
//    // 17. Visualizzazione esito e statistiche di una partita in una determinata stagione e competizione
//    public void viewMatchOutcomeAndStats(String codiceCompetizione, String annoCalcistico, String dataPartita) throws SQLException {
//        String query = "SELECT * FROM PARTITE WHERE CodiceCompetizione = ? AND AnnoCalcistico = ? AND DataPartita = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, codiceCompetizione);
//            stmt.setString(2, annoCalcistico);
//            stmt.setString(3, dataPartita);
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    // Output the match information
//                }
//            }
//        }
//    }
//
//    // 18. Visualizzazione dei calciatori svincolati o non più in attività
//    public void viewFreeAgentsOrRetiredPlayers() throws SQLException {
//        String query = "SELECT Nome, Cognome, CF FROM CALCIATORE WHERE Status = 'Svincolato' OR Status = 'Ritirato'";
//        try (PreparedStatement stmt = connection.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                // Output the player information
//            }
//        }
//    }
//
//    // 19. Visualizzazione informazioni di una competizione
//    public void viewCompetitionInfo(String codiceCompetizione) throws SQLException {
//        String query = "SELECT * FROM COMPETIZIONE WHERE CodiceCompetizione = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, codiceCompetizione);
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    // Output the competition information
//                }
//            }
//        }
//    }
//
//    // 20. Visualizzazione top 5 marcatori e assistman in un'edizione di una competizione
//    public void viewTop5ScorersAndAssistmen(String codiceCompetizione) throws SQLException {
//        String topScorersQuery = "SELECT CF_Calciatore, Gol FROM STATISTICHE WHERE CodiceCompetizione = ? ORDER BY Gol DESC LIMIT 5";
//        String topAssistmenQuery = "SELECT CF_Calciatore, Assist FROM STATISTICHE WHERE CodiceCompetizione = ? ORDER BY Assist DESC LIMIT 5";
//
//        try (PreparedStatement scorerStmt = connection.prepareStatement(topScorersQuery);
//             PreparedStatement assistmanStmt = connection.prepareStatement(topAssistmenQuery)) {
//            scorerStmt.setString(1, codiceCompetizione);
//            assistmanStmt.setString(1, codiceCompetizione);
//
//            try (ResultSet rs = scorerStmt.executeQuery()) {
//                while (rs.next()) {
//                    // Output the top 5 scorers
//                }
//            }
//
//            try (ResultSet rs = assistmanStmt.executeQuery()) {
//                while (rs.next()) {
//                    // Output the top 5 assistmen
//                }
//            }
//        }
//    }
}


