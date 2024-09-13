-- Drop database if exists and create new
drop database if exists MyDiet;
create database MyDiet;
use MyDiet;

-- INTEGRATORI table
create table INTEGRATORI (
                             IdIntegratore varchar(255) not null,
                             Nome varchar(255) not null,
                             Scopo varchar(255) not null,
                             constraint ID_INTEGRATORI_ID primary key (IdIntegratore)
);

-- NUTRIZIONISTA table
create table NUTRIZIONISTA (
                               Specializzazione varchar(255) not null,
                               Numero_di_telefono int not null,
                               Mail varchar(255) not null,
                               Username varchar(255) not null,
                               Nome_ varchar(255) not null,
                               Cognome char(255) not null,
                               Password varchar(255) not null,
                               Sesso char(1) not null,
                               Percentuale_soddisfatti int not null,
                               Media_stelle int not null,
                               constraint ID_NUTRIZIONISTA_ID primary key (Username)
);

-- CLIENTE table
create table CLIENTE (
                         Numero_di_telefono int,
                         Mail varchar(255),
                         Eta int not null,
                         Username varchar(255) not null,
                         Nome_ varchar(255) not null,
                         Cognome varchar(255) not null,
                         Password varchar(255) not null,
                         Sesso char(1) not null,
                         constraint ID_CLIENTE_ID primary key (Username)
);

-- OBBIETTIVO table
create table OBBIETTIVO (
                            Username varchar(255) not null,
                            Data_raggiungimento date not null,
                            Raggiunto char not null,
                            Descrizione varchar(255) not null,
                            Peso int not null,
                            Circ_punto_vita int not null,
                            Circ_braccio int not null,
                            Circ_gambe int not null,
                            constraint ID_OBBIETTIVO_ID primary key (Username, Data_raggiungimento),
                            foreign key (Username) references CLIENTE (Username)
);


-- TARIFFA table
create table TARIFFA (
                         Username varchar(255) not null,
                         Durata_mesi int not null,
                         Prezzo int not null,
                         constraint ID_TARIFFA_ID primary key (Username, Durata_mesi),
                         foreign key (Username) references NUTRIZIONISTA (Username)
);

-- ABBONAMENTO table
create table ABBONAMENTO (
                             SOT_Username varchar(255) not null,
                             SOT_Durata_mesi int not null,
                             Username varchar(255) not null,
                             Data_Acquisto date not null,
                             Sconto int,
                             constraint ID_ABBONAMENTO_ID primary key (Username, SOT_Username, SOT_Durata_mesi, Data_Acquisto),
                             foreign key (Username) references CLIENTE (Username),
                             foreign key (SOT_Username, SOT_Durata_mesi) references TARIFFA (Username, Durata_mesi)
);

-- RECENSIONE table
create table RECENSIONE (
                            Id_recensione varchar(10) not null,
                            Username varchar(255) not null,
                            R_C_Username varchar(255) not null,
                            Testo varchar(255),
                            Data_recensione date not null,
                            Numero_stelle int not null,
                            constraint ID_RECENSIONE_ID primary key (Id_recensione, Username, R_C_Username),
                            foreign key (Username) references NUTRIZIONISTA (Username),
                            foreign key (R_C_Username) references CLIENTE (Username)
);

-- DIETA table
create table DIETA (
                       Username varchar(255) not null,
                       Prezzo int not null,
                       Data_fine date not null,
                       Data_inizio date not null,
                       SVI_Username varchar(255) not null,
                       constraint ID_DIETA_ID primary key (Username, Data_inizio),
                       foreign key (Username) references CLIENTE (Username),
                       foreign key (SVI_Username) references NUTRIZIONISTA (Username)
);

-- GIORNO table
create table GIORNO (
                        Username varchar(255) not null,
                        Data_inizio date not null,
                        Nome varchar(255) not null,
                        constraint ID_GIORNO_ID primary key (Username, Data_inizio, Nome),
                        foreign key (Username, Data_inizio) references DIETA (Username, Data_inizio)
);


-- PERCORSO_DI_FORMAZIONE table
create table PERCORSO_DI_FORMAZIONE (
                                        Username varchar(255) not null,
                                        Nome_percorso varchar(255) not null,
                                        Data_inizio date not null,
                                        Data_fine date not null,
                                        Voto_conseguito int not null,
                                        constraint ID_PERCORSO_DI_FORMAZIONE_ID primary key (Username, Nome_percorso),
                                        foreign key (Username) references NUTRIZIONISTA (Username)
);

-- PASTO table
create table PASTO (
                       COM_Username varchar(255) not null,
                       COM_Data_inizio date not null,
                       COM_Nome varchar(255) not null,
                       Nome varchar(255) not null,
                       Totale_calorie int not null,
                       constraint ID_PASTO_ID primary key (COM_Username, COM_Data_inizio, COM_Nome, Nome),
                       foreign key (COM_Username, COM_Data_inizio, COM_Nome) references GIORNO (Username, Data_inizio, Nome)
);
-- RICETTA table
create table RICETTA (
                         INC_COM_Username varchar(30) not null,
                         INC_COM_Data_inizio date not null,
                         INC_COM_Nome varchar(30) not null,
                         INC_Nome varchar(255) not null,
                         Nome varchar(255) not null,
                         Difficolta varchar(255) not null,
                         Descrizione varchar(255) not null,
                         Tempo_Preparazione varchar(255) not null,
                         constraint ID_RICETTA_ID primary key (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome),
                         foreign key (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome) references PASTO (COM_Username, COM_Data_inizio, COM_Nome, Nome)
);

-- ALIMENTO table
create table ALIMENTO (
                          IdAlimento varchar(255) not null,
                          Nome varchar(255) not null,
                          Grammi_proteine int not null,
                          Grammi_carboidrati int not null,
                          Grammi_grassi int not null,
                          Calorie_totali int not null,
                          constraint ID_ALIMENTO_ID primary key (IdAlimento)
);

-- CREAZIONE table
create table CREAZIONE (
                           INC_COM_Username varchar(30) not null,
                           INC_COM_Data_inizio date not null,
                           INC_COM_Nome varchar(30) not null,
                           INC_Nome varchar(30) not null,
                           Nome varchar(255) not null,
                           IdAlimento varchar(255) not null,
                           Peso int not null,
                           constraint ID_CREAZIONE_ID primary key (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento),
                           foreign key (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome) references RICETTA (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome),
                           foreign key (IdAlimento) references ALIMENTO (IdAlimento)
);


-- ASSUNZIONE table
create table ASSUNZIONE (
                            Username varchar(255) not null,
                            Data_inizio date not null,
                            Nome varchar(255) not null,
                            IdIntegratore varchar(255) not null,
                            Quantita int not null,
                            constraint ID_ASSUNZIONE_ID primary key (IdIntegratore, Username, Data_inizio),
                            foreign key (IdIntegratore) references INTEGRATORI (IdIntegratore),
                            foreign key (Username, Data_inizio, Nome) references GIORNO (Username, Data_inizio, Nome)
);

-- AGGIORNAMENTO table
create table AGGIORNAMENTO (
                               Username varchar(255) not null,
                               Data date not null,
                               Descrizione varchar(255),
                               Peso int not null,
                               Circ_punto_vita int not null,
                               Circ_braccio int not null,
                               Circ_gambe int not null,
                               constraint ID_AGGIORNAMENTO_ID primary key (Username, Data),
                               foreign key (Username) references CLIENTE (Username)
);

-- INTEGRAZIONE table
create table INTEGRAZIONE (
                              Username varchar(255) not null,
                              Data_inizio date not null,
                              IdIntegratore varchar(255) not null,
                              constraint ID_INTEGRAZIONE_ID primary key (IdIntegratore, Username, Data_inizio),
                              foreign key (IdIntegratore) references INTEGRATORI (IdIntegratore),
                              foreign key (Username, Data_inizio) references DIETA (Username, Data_inizio)
);

-- Indexes creation

-- NUTRIZIONISTA index
create unique index ID_NUTRIZIONISTA_IND
    on NUTRIZIONISTA (Username);
    
-- ABBONAMENTO indexes
create unique index ID_ABBONAMENTO_IND
    on ABBONAMENTO (Username, SOT_Username, SOT_Durata_mesi, Data_Acquisto);

create index FKSOTTOSCRIZIONE_IND
    on ABBONAMENTO (SOT_Username, SOT_Durata_mesi);

-- RECENSIONE indexes
create unique index ID_RECENSIONE_IND
    on RECENSIONE (Id_recensione);

create index FKREC_NUT_IND
    on RECENSIONE (Username);

create index FKREC_CLI_IND
    on RECENSIONE (R_C_Username);

-- GIORNO index
create unique index ID_GIORNO_IND
    on GIORNO (Username, Data_inizio, Nome);

-- DIETA indexes
create unique index ID_DIETA_IND
    on DIETA (Username, Data_inizio);

create index FKSVILUPPO_IND
    on DIETA (SVI_Username);

-- PERCORSO_DI_FORMAZIONE index
create unique index ID_PERCORSO_DI_FORMAZIONE_IND
    on PERCORSO_DI_FORMAZIONE (Username, Nome_percorso);


-- PASTO index
create unique index ID_PASTO_IND
    on PASTO (COM_Username, COM_Data_inizio, COM_Nome, Nome);
    
    
-- RICETTA index
create unique index ID_RICETTA_IND
    on RICETTA (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome);



-- CREAZIONE indexes
create unique index ID_CREAZIONE_IND
    on CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento);

create index FKCRE_ALI_IND
    on CREAZIONE (IdAlimento);

-- INTEGRAZIONE indexes
create unique index ID_INTEGRAZIONE_IND
    on INTEGRAZIONE (IdIntegratore, Username, Data_inizio);

create index FKINT_DIE_IND
    on INTEGRAZIONE (Username, Data_inizio);

-- ASSUNZIONE indexes
create unique index ID_ASSUNZIONE_IND
    on ASSUNZIONE (IdIntegratore, Username, Data_inizio, Nome);

create index FKASS_GIO_IND
    on ASSUNZIONE (Username, Data_inizio, Nome);

-- AGGIORNAMENTO index
create unique index ID_AGGIORNAMENTO_IND
    on AGGIORNAMENTO (Username, Data);

-- ALIMENTO index
create unique index ID_ALIMENTO_IND
    on ALIMENTO (IdAlimento);

-- INTEGRATORI index
create unique index ID_INTEGRATORI_IND
    on INTEGRATORI (IdIntegratore);



-- TARIFFA index
create unique index ID_TARIFFA_IND
    on TARIFFA (Username, Durata_mesi);
-- DataEntry.sql
-- -------------
-- Script to populate the database with initial data

-- Use the created database
USE MyDiet;

-- Inserimento dati di esempio per CLIENTE
-- Inserimento dati di esempio per NUTRIZIONISTA
INSERT INTO NUTRIZIONISTA (Specializzazione, Numero_di_telefono, Mail, Username, Nome_, Cognome, Password, Sesso, Percentuale_soddisfatti, Media_stelle)
VALUES
	('specializzazione',0, 'admin.com','admin', 'nome', 'cognome', '1234', 'M', 0, 0),
	('Dietetica', 987654324, 'nutrizionista4@example.com', 'nutrizionista4', 'Francesca', 'Gialli', '123', 'F', 80, 4),
	('Nutrizione sportiva', 987654325, 'nutrizionista5@example.com', 'nutrizionista5', 'Paolo', 'Verdi', 'password456', 'M', 55, 5),
	('Nutrizione clinica', 987654326, 'nutrizionista6@example.com', 'nutrizionista6', 'Chiara', 'Rossi', 'password789', 'F', 0, 4),
	('Dietetica', 987654327, 'nutrizionista7@example.com', 'nutrizionista7', 'Fabio', 'Bianchi', 'password123', 'M', 0, 4),
	('Nutrizione sportiva', 987654328, 'nutrizionista8@example.com', 'nutrizionista8', 'Alessia', 'Verdi', 'password456', 'F', 0, 5),
	('Nutrizione clinica', 987654329, 'nutrizionista9@example.com', 'nutrizionista9', 'Luigi', 'Rossi', 'password789', 'M', 0, 4),
	('Dietetica', 987654330, 'nutrizionista10@example.com', 'nutrizionista10', 'Elena', 'Bianchi', 'password123', 'F', 0, 4),
	('Nutrizione sportiva', 987654331, 'nutrizionista11@example.com', 'nutrizionista11', 'Giovanni', 'Verdi', 'password456', 'M', 0, 5),
	('Nutrizione clinica', 987654332, 'nutrizionista12@example.com', 'nutrizionista12', 'Valentina', 'Rossi', 'password789', 'F', 0, 4),
	('Dietetica', 987654333, 'nutrizionista13@example.com', 'nutrizionista13', 'Riccardo', 'Bianchi', 'password123', 'M', 0, 4),
	('Nutrizione sportiva', 987654334, 'nutrizionista14@example.com', 'nutrizionista14', 'Sara', 'Verdi', 'password456', 'F', 0, 5),
	('Nutrizione clinica', 987654335, 'nutrizionista15@example.com', 'nutrizionista15', 'Andrea', 'Rossi', 'password789', 'M', 0, 4),
	('Dietetica', 987654336, 'nutrizionista16@example.com', 'nutrizionista16', 'Martina', 'Bianchi', 'password123', 'F', 0, 4),
	('Nutrizione sportiva', 987654337, 'nutrizionista17@example.com', 'nutrizionista17', 'Matteo', 'Verdi', 'password456', 'M', 0, 5),
	('Nutrizione clinica', 987654338, 'nutrizionista18@example.com', 'nutrizionista18', 'Giulia', 'Rossi', 'password789', 'F', 0, 4),
	('Dietetica', 987654339, 'nutrizionista19@example.com', 'nutrizionista19', 'Simone', 'Bianchi', 'password123', 'M', 0, 4),
	('Nutrizione sportiva', 987654340, 'nutrizionista20@example.com', 'nutrizionista20', 'Chiara', 'Verdi', 'password456', 'F', 0, 5),
	('Nutrizione clinica', 987654341, 'nutrizionista21@example.com', 'nutrizionista21', 'Pietro', 'Rossi', 'password789', 'M', 0, 4),
	('Dietetica', 987654342, 'nutrizionista22@example.com', 'nutrizionista22', 'Elisa', 'Bianchi', 'password123', 'F', 0, 4),
	('Nutrizione sportiva', 987654343, 'nutrizionista23@example.com', 'nutrizionista23', 'Marco', 'Verdi', 'password456', 'M', 0, 5),
	('Nutrizione clinica', 987654344, 'nutrizionista24@example.com', 'nutrizionista24', 'Alessandra', 'Rossi', 'password789', 'F', 0, 4),
	('Dietetica', 987654345, 'nutrizionista25@example.com', 'nutrizionista25', 'Davide', 'Bianchi', 'password123', 'M', 0, 4),
	('Nutrizione sportiva', 987654346, 'nutrizionista26@example.com', 'nutrizionista26', 'Federica', 'Verdi', 'password456', 'F', 0, 5),
	('Nutrizione clinica', 987654347, 'nutrizionista27@example.com', 'nutrizionista27', 'Giacomo', 'Rossi', 'password789', 'M', 0, 4),
	('Dietetica', 987654348, 'nutrizionista28@example.com', 'nutrizionista28', 'Silvia', 'Bianchi', 'password123', 'F', 0, 4),
	('Nutrizione sportiva', 987654349, 'nutrizionista29@example.com', 'nutrizionista29', 'Emanuele', 'Verdi', 'password456', 'M', 0, 5),
	('Nutrizione clinica', 987654350, 'nutrizionista30@example.com', 'nutrizionista30', 'Cristina', 'Rossi', 'password789', 'F', 0, 4),
	('Dietetica', 987654351, 'nutrizionista31@example.com', 'nutrizionista31', 'Lorenzo', 'Bianchi', 'password123', 'M', 0, 4),
	('Nutrizione sportiva', 987654352, 'nutrizionista32@example.com', 'nutrizionista32', 'Valeria', 'Verdi', 'password456', 'F', 0, 5),
	('Nutrizione clinica', 987654353, 'nutrizionista33@example.com', 'nutrizionista33', 'Nicola', 'Rossi', 'password789', 'M', 0, 4),
	('Dietetica', 987654354, 'nutrizionista34@example.com', 'nutrizionista34', 'Eleonora', 'Bianchi', 'password123', 'F', 0, 4),
	('Nutrizione sportiva', 987654355, 'nutrizionista35@example.com', 'nutrizionista35', 'Federico', 'Verdi', 'password456', 'M', 0, 5),
	('Nutrizione clinica', 987654356, 'nutrizionista36@example.com', 'nutrizionista36', 'Marta', 'Rossi', 'password789', 'F', 0, 4),
	('Dietetica', 987654357, 'nutrizionista37@example.com', 'nutrizionista37', 'Giovanna', 'Bianchi', 'password123', 'M', 0, 4),
	('Nutrizione sportiva', 987654358, 'nutrizionista38@example.com', 'nutrizionista38', 'Daniele', 'Verdi', 'password456', 'F', 0, 5),
	('Nutrizione clinica', 987654359, 'nutrizionista39@example.com', 'nutrizionista39', 'Roberta', 'Rossi', 'password789', 'M', 0, 4),
	('Dietetica', 987654360, 'nutrizionista40@example.com', 'nutrizionista40', 'Riccarda', 'Bianchi', 'password123', 'F', 0, 4),
	('Nutrizione sportiva', 987654361, 'nutrizionista41@example.com', 'nutrizionista41', 'Simone', 'Verdi', 'password456', 'M', 0, 5),
	('Nutrizione clinica', 987654362, 'nutrizionista42@example.com', 'nutrizionista42', 'Giovanni', 'Rossi', 'password789', 'F', 0, 4),
	('Dietetica', 987654363, 'nutrizionista43@example.com', 'nutrizionista43', 'Chiara', 'Bianchi', 'password123', 'M', 0, 4),
	('Nutrizione sportiva', 987654364, 'nutrizionista44@example.com', 'nutrizionista44', 'Valentina', 'Verdi', 'password456', 'F', 0, 5),
	('Nutrizione clinica', 987654365, 'nutrizionista45@example.com', 'nutrizionista45', 'Marco', 'Rossi', 'password789', 'M', 0, 4),
	('Dietetica', 987654366, 'nutrizionista46@example.com', 'nutrizionista46', 'Alessia', 'Bianchi', 'password123', 'F', 0, 4),
	('Nutrizione sportiva', 987654367, 'nutrizionista47@example.com', 'nutrizionista47', 'Luigi', 'Verdi', 'password456', 'M', 0, 5),
	('Nutrizione clinica', 987654368, 'nutrizionista48@example.com', 'nutrizionista48', 'Elena', 'Rossi', 'password789', 'F', 0, 4),
	('Dietetica', 987654369, 'nutrizionista49@example.com', 'nutrizionista49', 'Sara', 'Bianchi', 'password123', 'M', 0, 4),
	('Nutrizione sportiva', 987654370, 'nutrizionista50@example.com', 'nutrizionista50', 'Andrea', 'Verdi', 'password456', 'F', 0, 5),
	('Nutrizione clinica', 987654371, 'nutrizionista51@example.com', 'nutrizionista51', 'Pietro', 'Rossi', 'password789', 'M', 0, 4);


-- Inserimento dati di esempio per CLIENTE
INSERT INTO CLIENTE (Numero_di_telefono, Mail, Eta, Username, Nome_, Cognome, Password, Sesso)
VALUES
	(0, 'admin.com', 0, 'admin', 'nome', 'cognome', '1234', 'M'),
	(456782012, 'elisascalabrini@gmail.com', 21, 'ellister03', 'Elisa', 'Scalabrini', 'ellister123', 'F'),
    (456789012, 'cliente4@example.com', 32, 'cliente4', 'Giacomo', 'Verdi', '1', 'M'),
    (567890123, 'cliente5@example.com', 27, 'cliente5', 'Elisa', 'Rossi', 'password456', 'F'),
    (678901234, 'cliente6@example.com', 31, 'cliente6', 'Roberto', 'Bianchi', 'password789', 'M'),
    (789012345, 'cliente7@example.com', 29, 'cliente7', 'Giulia', 'Verdi', 'password123', 'F'),
    (890123456, 'cliente8@example.com', 34, 'cliente8', 'Anna', 'Rossi', 'password456', 'F'),
    (901234567, 'cliente9@example.com', 30, 'cliente9', 'Matteo', 'Bianchi', 'password789', 'M'),
    (123456780, 'cliente10@example.com', 33, 'cliente10', 'Lorenzo', 'Verdi', 'password123', 'M'),
    (234567891, 'cliente11@example.com', 28, 'cliente11', 'Sofia', 'Rossi', 'password456', 'F'),
    (345678902, 'cliente12@example.com', 29, 'cliente12', 'Alessandro', 'Bianchi', 'password789', 'M'),
    (456789013, 'cliente13@example.com', 31, 'cliente13', 'Emma', 'Verdi', 'password123', 'F'),
    (567890124, 'cliente14@example.com', 26, 'cliente14', 'Giovanni', 'Rossi', 'password456', 'M'),
    (678901235, 'cliente15@example.com', 30, 'cliente15', 'Chiara', 'Bianchi', 'password789', 'F'),
    (789012346, 'cliente16@example.com', 32, 'cliente16', 'Marco', 'Verdi', 'password123', 'M'),
    (890123457, 'cliente17@example.com', 27, 'cliente17', 'Elena', 'Rossi', 'password456', 'F'),
    (901234568, 'cliente18@example.com', 29, 'cliente18', 'Federico', 'Bianchi', 'password789', 'M'),
    (123456781, 'cliente19@example.com', 33, 'cliente19', 'Valentina', 'Verdi', 'password123', 'F'),
    (234567892, 'cliente20@example.com', 28, 'cliente20', 'Davide', 'Rossi', 'password456', 'M'),
    (345678903, 'cliente21@example.com', 29, 'cliente21', 'Eleonora', 'Bianchi', 'password789', 'F'),
    (456789014, 'cliente22@example.com', 31, 'cliente22', 'Roberto', 'Verdi', 'password123', 'M'),
    (567890125, 'cliente23@example.com', 26, 'cliente23', 'Silvia', 'Rossi', 'password456', 'F'),
    (678901236, 'cliente24@example.com', 30, 'cliente24', 'Giacomo', 'Bianchi', 'password789', 'M'),
    (789012347, 'cliente25@example.com', 32, 'cliente25', 'Elisa', 'Verdi', 'password123', 'F'),
    (890123458, 'cliente26@example.com', 27, 'cliente26', 'Roberto', 'Rossi', 'password456', 'M'),
    (901234569, 'cliente27@example.com', 29, 'cliente27', 'Giulia', 'Bianchi', 'password789', 'F'),
    (123456782, 'cliente28@example.com', 33, 'cliente28', 'Anna', 'Verdi', 'password123', 'F'),
    (234567893, 'cliente29@example.com', 28, 'cliente29', 'Matteo', 'Rossi', 'password456', 'M'),
    (345678904, 'cliente30@example.com', 29, 'cliente30', 'Sofia', 'Bianchi', 'password789', 'F'),
    (456789015, 'cliente31@example.com', 31, 'cliente31', 'Alessandro', 'Verdi', 'password123', 'M'),
    (567890126, 'cliente32@example.com', 26, 'cliente32', 'Emma', 'Rossi', 'password456', 'F'),
    (678901237, 'cliente33@example.com', 30, 'cliente33', 'Giovanni', 'Bianchi', 'password789', 'M'),
    (789012348, 'cliente34@example.com', 32, 'cliente34', 'Chiara', 'Verdi', 'password123', 'F'),
    (890123459, 'cliente35@example.com', 27, 'cliente35', 'Marco', 'Rossi', 'password456', 'M'),
    (901234570, 'cliente36@example.com', 29, 'cliente36', 'Elena', 'Bianchi', 'password789', 'F'),
    (123456783, 'cliente37@example.com', 33, 'cliente37', 'Federico', 'Verdi', 'password123', 'M'),
    (234567894, 'cliente38@example.com', 28, 'cliente38', 'Valentina', 'Rossi', 'password456', 'F'),
    (345678905, 'cliente39@example.com', 29, 'cliente39', 'Davide', 'Bianchi', 'password789', 'M'),
    (456789016, 'cliente40@example.com', 31, 'cliente40', 'Eleonora', 'Verdi', 'password123', 'F'),
    (567890127, 'cliente41@example.com', 26, 'cliente41', 'Roberto', 'Rossi', 'password456', 'M'),
    (678901238, 'cliente42@example.com', 30, 'cliente42', 'Silvia', 'Bianchi', 'password789', 'F'),
    (789012349, 'cliente43@example.com', 32, 'cliente43', 'Giacomo', 'Verdi', 'password123', 'M'),
    (890123460, 'cliente44@example.com', 27, 'cliente44', 'Elisa', 'Rossi', 'password456', 'F'),
    (901234571, 'cliente45@example.com', 29, 'cliente45', 'Roberto', 'Bianchi', 'password789', 'M'),
    (123456784, 'cliente46@example.com', 33, 'cliente46', 'Giulia', 'Verdi', 'password123', 'F'),
    (234567895, 'cliente47@example.com', 28, 'cliente47', 'Anna', 'Rossi', 'password456', 'F'),
    (345678906, 'cliente48@example.com', 29, 'cliente48', 'Matteo', 'Bianchi', 'password789', 'M'),
    (456789017, 'cliente49@example.com', 31, 'cliente49', 'Lorenzo', 'Verdi', 'password123', 'M'),
    (567890128, 'cliente50@example.com', 26, 'cliente50', 'Sofia', 'Rossi', 'password456', 'F'),
    (678901239, 'cliente51@example.com', 30, 'cliente51', 'Alessandro', 'Bianchi', 'password789', 'M');


INSERT INTO OBBIETTIVO (Username, Data_raggiungimento, Raggiunto, Descrizione, Peso, Circ_punto_vita, Circ_braccio, Circ_gambe)
VALUES
    ('cliente4', '2024-01-15', 'S', 'Perdita di peso iniziale', 80, 90, 30, 50),
    ('cliente5', '2024-02-20', 'N', 'Aumento della massa muscolare', 70, 85, 35, 55),
    ('cliente6', '2024-03-10', 'S', 'Riduzione della circonferenza vita', 75, 88, 32, 52),
    ('cliente7', '2024-04-05', 'S', 'Miglioramento della forma fisica', 72, 87, 31, 51),
    ('cliente8', '2024-05-12', 'S', 'Aumento della forza muscolare', 78, 90, 33, 53),
    ('cliente9', '2024-06-18', 'S', 'Perdita di peso generale', 76, 89, 34, 54),
    ('cliente10', '2024-07-22', 'N', 'Incremento della resistenza', 74, 86, 30, 50),
    ('cliente11', '2024-08-30', 'S', 'Riduzione del grasso corporeo', 73, 84, 29, 49),
    ('cliente12', '2024-09-14', 'S', 'Miglioramento della postura', 77, 85, 32, 51),
    ('cliente13', '2024-10-20', 'N', 'Aumento della flessibilità', 79, 87, 33, 52),
    ('admin', '2024-10-20', 'N', 'Aumento della flessibilità', 79, 87, 34, 52);


-- Tariffe per nutrizionista4
INSERT INTO TARIFFA (Username, Durata_mesi, Prezzo)
VALUES
    ('nutrizionista4', 1, 30),
    ('nutrizionista4', 3, 85),
    ('nutrizionista4', 6, 150),
    ('nutrizionista4', 12, 280);
    
-- Tariffe per nutrizionista5
INSERT INTO TARIFFA (Username, Durata_mesi, Prezzo)
VALUES
    ('nutrizionista5', 1, 35),
    ('nutrizionista5', 3, 95),
    ('nutrizionista5', 6, 180),
    ('nutrizionista5', 12, 330);

-- Tariffe per nutrizionista6
INSERT INTO TARIFFA (Username, Durata_mesi, Prezzo)
VALUES
    ('nutrizionista6', 1, 25),
    ('nutrizionista6', 3, 70),
    ('nutrizionista6', 6, 125),
    ('nutrizionista6', 12, 240);
-- Tariffe per nutrizionista7
INSERT INTO TARIFFA (Username, Durata_mesi, Prezzo)
VALUES
    ('nutrizionista7', 1, 32),
    ('nutrizionista7', 3, 85),
    ('nutrizionista7', 6, 155),
    ('nutrizionista7', 12, 300);

-- Tariffe per nutrizionista8
INSERT INTO TARIFFA (Username, Durata_mesi, Prezzo)
VALUES
    ('nutrizionista8', 1, 38),
    ('nutrizionista8', 3, 105),
    ('nutrizionista8', 6, 195),
    ('nutrizionista8', 12, 350);

-- Tariffe per nutrizionista9
INSERT INTO TARIFFA (Username, Durata_mesi, Prezzo)
VALUES
    ('nutrizionista9', 1, 28),
    ('nutrizionista9', 3, 75),
    ('nutrizionista9', 6, 140),
    ('nutrizionista9', 12, 270);

-- Tariffe per nutrizionista10
INSERT INTO TARIFFA (Username, Durata_mesi, Prezzo)
VALUES
    ('nutrizionista10', 1, 30),
    ('nutrizionista10', 3, 80),
    ('nutrizionista10', 6, 145),
    ('nutrizionista10', 12, 280);

-- Tariffe per nutrizionista11
INSERT INTO TARIFFA (Username, Durata_mesi, Prezzo)
VALUES
    ('nutrizionista11', 1, 40),
    ('nutrizionista11', 3, 110),
    ('nutrizionista11', 6, 200),
    ('nutrizionista11', 12, 370);

-- Tariffe per nutrizionista12
INSERT INTO TARIFFA (Username, Durata_mesi, Prezzo)
VALUES
    ('nutrizionista12', 1, 27),
    ('nutrizionista12', 3, 75),
    ('nutrizionista12', 6, 130),
    ('nutrizionista12', 12, 260);


-- Inserimento nella tabella INTEGRATORI senza la colonna Quantita
INSERT INTO INTEGRATORI (IdIntegratore, Nome, Scopo) VALUES
    ('INTG001', 'Vitamina C', 'Supporto Immunitario'),
    ('INTG002', 'Proteine del Siero', 'Costruzione Muscolare'),
    ('INTG003', 'Omega 3', 'Salute Cardiovascolare'),
    ('INTG004', 'Multivitaminico', 'Salute Generale'),
    ('INTG005', 'Creatina', 'Aumento della Forza'),
    ('INTG006', 'Calcio', 'Salute delle Ossa'),
    ('INTG007', 'Magnesio', 'Recupero Muscolare'),
    ('INTG008', 'Ferro', 'Energia e Vitalità'),
    ('INTG009', 'Zinco', 'Salute della Pelle'),
    ('INTG010', 'Vitamina D', 'Assorbimento del Calcio'),
    ('INTG011', 'L-Carnitina', 'Metabolismo dei Grassi'),
    ('INTG012', 'Glutammina', 'Recupero Muscolare'),
    ('INTG013', 'Beta-Alanina', 'Resistenza e Performance'),
    ('INTG014', 'Collagene', 'Salute delle Articolazioni'),
    ('INTG015', 'Ginseng', 'Energia e Vitalità'),
    ('INTG016', 'Coenzima Q10', 'Energia Cellulare'),
    ('INTG017', 'Acido Alfa Lipoico', 'Antiossidante'),
    ('INTG018', 'Vitamina B12', 'Metabolismo Energetico'),
    ('INTG019', 'Kreatina Monoidrato', 'Aumento della Massa Muscolare'),
    ('INTG020', 'Selenio', 'Antiossidante');


-- Inserimento dati di esempio per RECENSIONE
INSERT INTO RECENSIONE (Id_recensione, Username, R_C_Username, Testo, Data_recensione, Numero_stelle)
VALUES
	('rec1', 'nutrizionista4', 'cliente4', 'Ottimo servizio!', '2024-08-21', 5),
    ('rec2', 'nutrizionista4', 'cliente5', 'Molto soddisfatto.', '2024-08-22', 4),
    ('rec3', 'nutrizionista5', 'cliente6', 'Consigliato!', '2024-08-23', 5),
    ('rec4', 'nutrizionista5', 'cliente7', 'Non male, ma migliorabile.', '2024-08-24', 3),
    ('rec5', 'nutrizionista4', 'cliente6', 'Un altro ottimo feedback!', '2024-08-25', 5),
    ('rec6', 'nutrizionista4', 'cliente6', 'Buona esperienza, ma potrebbe migliorare.', '2024-08-23', 4),
    ('rec7', 'nutrizionista4', 'cliente5', 'Servizio eccellente!', '2024-08-22', 5);

-- Inserisci dati nella tabella DIETA
INSERT INTO DIETA (Username, Prezzo, Data_fine, Data_inizio, SVI_Username)
VALUES
    ('cliente4', 100, '2024-09-30', '2024-09-01', 'nutrizionista4'),
    ('cliente4', 110, '2024-10-31', '2024-10-01', 'nutrizionista4'),
    ('cliente5', 120, '2024-11-30', '2024-11-01', 'nutrizionista4'),
    ('cliente5', 130, '2024-12-31', '2024-12-01', 'nutrizionista4'),
    ('cliente6', 140, '2025-01-31', '2025-01-01', 'nutrizionista4'),
    ('cliente6', 150, '2025-02-28', '2025-02-01', 'nutrizionista4'),
    ('cliente7', 160, '2025-03-31', '2025-03-01', 'nutrizionista4'),
    ('cliente7', 170, '2025-04-30', '2025-04-01', 'nutrizionista4'),
    ('cliente8', 180, '2025-05-31', '2025-05-01', 'nutrizionista4'),
    ('cliente9', 190, '2025-06-30', '2025-06-01', 'nutrizionista4');

-- Ripopolazione della tabella GIORNO con 7 giorni per ciascuna dieta
INSERT INTO GIORNO (Username, Data_inizio, Nome)
VALUES
    -- Dieta di cliente4 con inizio il 2024-09-01
    ('cliente4', '2024-09-01', 'Lunedì'),
    ('cliente4', '2024-09-01', 'Martedì'),
    ('cliente4', '2024-09-01', 'Mercoledì'),
    ('cliente4', '2024-09-01', 'Giovedì'),
    ('cliente4', '2024-09-01', 'Venerdì'),
    ('cliente4', '2024-09-01', 'Sabato'),
    ('cliente4', '2024-09-01', 'Domenica'),

    -- Dieta di cliente4 con inizio il 2024-10-01
    ('cliente4', '2024-10-01', 'Lunedì'),
    ('cliente4', '2024-10-01', 'Martedì'),
    ('cliente4', '2024-10-01', 'Mercoledì'),
    ('cliente4', '2024-10-01', 'Giovedì'),
    ('cliente4', '2024-10-01', 'Venerdì'),
    ('cliente4', '2024-10-01', 'Sabato'),
    ('cliente4', '2024-10-01', 'Domenica'),

    -- Dieta di cliente5 con inizio il 2024-11-01
    ('cliente5', '2024-11-01', 'Lunedì'),
    ('cliente5', '2024-11-01', 'Martedì'),
    ('cliente5', '2024-11-01', 'Mercoledì'),
    ('cliente5', '2024-11-01', 'Giovedì'),
    ('cliente5', '2024-11-01', 'Venerdì'),
    ('cliente5', '2024-11-01', 'Sabato'),
    ('cliente5', '2024-11-01', 'Domenica'),

    -- Dieta di cliente5 con inizio il 2024-12-01
    ('cliente5', '2024-12-01', 'Lunedì'),
    ('cliente5', '2024-12-01', 'Martedì'),
    ('cliente5', '2024-12-01', 'Mercoledì'),
    ('cliente5', '2024-12-01', 'Giovedì'),
    ('cliente5', '2024-12-01', 'Venerdì'),
    ('cliente5', '2024-12-01', 'Sabato'),
    ('cliente5', '2024-12-01', 'Domenica'),

    -- Dieta di cliente6 con inizio il 2025-01-01
    ('cliente6', '2025-01-01', 'Lunedì'),
    ('cliente6', '2025-01-01', 'Martedì'),
    ('cliente6', '2025-01-01', 'Mercoledì'),
    ('cliente6', '2025-01-01', 'Giovedì'),
    ('cliente6', '2025-01-01', 'Venerdì'),
    ('cliente6', '2025-01-01', 'Sabato'),
    ('cliente6', '2025-01-01', 'Domenica'),

    -- Dieta di cliente6 con inizio il 2025-02-01
    ('cliente6', '2025-02-01', 'Lunedì'),
    ('cliente6', '2025-02-01', 'Martedì'),
    ('cliente6', '2025-02-01', 'Mercoledì'),
    ('cliente6', '2025-02-01', 'Giovedì'),
    ('cliente6', '2025-02-01', 'Venerdì'),
    ('cliente6', '2025-02-01', 'Sabato'),
    ('cliente6', '2025-02-01', 'Domenica'),

    -- Dieta di cliente7 con inizio il 2025-03-01
    ('cliente7', '2025-03-01', 'Lunedì'),
    ('cliente7', '2025-03-01', 'Martedì'),
    ('cliente7', '2025-03-01', 'Mercoledì'),
    ('cliente7', '2025-03-01', 'Giovedì'),
    ('cliente7', '2025-03-01', 'Venerdì'),
    ('cliente7', '2025-03-01', 'Sabato'),
    ('cliente7', '2025-03-01', 'Domenica'),

    -- Dieta di cliente7 con inizio il 2025-04-01
    ('cliente7', '2025-04-01', 'Lunedì'),
    ('cliente7', '2025-04-01', 'Martedì'),
    ('cliente7', '2025-04-01', 'Mercoledì'),
    ('cliente7', '2025-04-01', 'Giovedì'),
    ('cliente7', '2025-04-01', 'Venerdì'),
    ('cliente7', '2025-04-01', 'Sabato'),
    ('cliente7', '2025-04-01', 'Domenica'),

    -- Dieta di cliente8 con inizio il 2025-05-01
    ('cliente8', '2025-05-01', 'Lunedì'),
    ('cliente8', '2025-05-01', 'Martedì'),
    ('cliente8', '2025-05-01', 'Mercoledì'),
    ('cliente8', '2025-05-01', 'Giovedì'),
    ('cliente8', '2025-05-01', 'Venerdì'),
    ('cliente8', '2025-05-01', 'Sabato'),
    ('cliente8', '2025-05-01', 'Domenica'),

    -- Dieta di cliente9 con inizio il 2025-06-01
    ('cliente9', '2025-06-01', 'Lunedì'),
    ('cliente9', '2025-06-01', 'Martedì'),
    ('cliente9', '2025-06-01', 'Mercoledì'),
    ('cliente9', '2025-06-01', 'Giovedì'),
    ('cliente9', '2025-06-01', 'Venerdì'),
    ('cliente9', '2025-06-01', 'Sabato'),
    ('cliente9', '2025-06-01', 'Domenica');


-- Pasti per cliente4, Lunedì 2024-10-01
INSERT INTO PASTO (COM_Username, COM_Data_inizio, COM_Nome, Nome, Totale_calorie)
VALUES
    ('cliente4', '2024-10-01', 'Lunedì', 'Colazione', 400),
    ('cliente4', '2024-10-01', 'Lunedì', 'Pranzo', 600),
    ('cliente4', '2024-10-01', 'Lunedì', 'Cena', 500),

    -- Pasti per cliente4, Martedì 2024-10-01
    ('cliente4', '2024-10-01', 'Martedì', 'Colazione', 350),
    ('cliente4', '2024-10-01', 'Martedì', 'Pranzo', 650),
    ('cliente4', '2024-10-01', 'Martedì', 'Cena', 450),

    -- Pasti per cliente4, Mercoledì 2024-10-01
    ('cliente4', '2024-10-01', 'Mercoledì', 'Colazione', 400),
    ('cliente4', '2024-10-01', 'Mercoledì', 'Pranzo', 600),
    ('cliente4', '2024-10-01', 'Mercoledì', 'Cena', 500),

    -- Pasti per cliente4, Giovedì 2024-10-01
    ('cliente4', '2024-10-01', 'Giovedì', 'Colazione', 350),
    ('cliente4', '2024-10-01', 'Giovedì', 'Pranzo', 620),
    ('cliente4', '2024-10-01', 'Giovedì', 'Cena', 480),

    -- Pasti per cliente4, Venerdì 2024-10-01
    ('cliente4', '2024-10-01', 'Venerdì', 'Colazione', 360),
    ('cliente4', '2024-10-01', 'Venerdì', 'Pranzo', 640),
    ('cliente4', '2024-10-01', 'Venerdì', 'Cena', 490),

    -- Pasti per cliente4, Sabato 2024-10-01
    ('cliente4', '2024-10-01', 'Sabato', 'Colazione', 370),
    ('cliente4', '2024-10-01', 'Sabato', 'Pranzo', 630),
    ('cliente4', '2024-10-01', 'Sabato', 'Cena', 500),

    -- Pasti per cliente4, Domenica 2024-10-01
    ('cliente4', '2024-10-01', 'Domenica', 'Colazione', 380),
    ('cliente4', '2024-10-01', 'Domenica', 'Pranzo', 620),
    ('cliente4', '2024-10-01', 'Domenica', 'Cena', 510);




-- Popolazione della tabella ALIMENTO con 50 alimenti
INSERT INTO ALIMENTO (IdAlimento, Nome, Grammi_proteine, Grammi_carboidrati, Grammi_grassi, Calorie_totali)
VALUES
    ('A1', 'Pollo', 27, 0, 3, 130),
    ('A2', 'Riso', 2, 28, 0, 130),
    ('A3', 'Mela', 0, 14, 0, 52),
    ('A4', 'Pane integrale', 4, 20, 1, 110),
    ('A5', 'Latte scremato', 3, 5, 0, 40),
    ('A6', 'Mandorle', 21, 22, 49, 575),
    ('A7', 'Tonno', 25, 0, 1, 110),
    ('A8', 'Salmone', 20, 0, 12, 208),
    ('A9', 'Spinaci', 3, 4, 0, 23),
    ('A10', 'Pomodori', 1, 4, 0, 18),
    ('A11', 'Uova', 13, 1, 11, 155),
    ('A12', 'Fagioli neri', 9, 23, 1, 132),
    ('A13', 'Lenticchie', 9, 20, 0, 116),
    ('A14', 'Patate dolci', 2, 20, 0, 86),
    ('A15', 'Quinoa', 4, 21, 2, 120),
    ('A16', 'Broccoli', 3, 6, 0, 31),
    ('A17', 'Carote', 1, 10, 0, 41),
    ('A18', 'Cetrioli', 1, 4, 0, 16),
    ('A19', 'Peperoni', 1, 6, 0, 26),
    ('A20', 'Zucchine', 1, 4, 0, 17),
    ('A21', 'Bistecca di manzo', 25, 0, 20, 271),
    ('A22', 'Pasta integrale', 8, 37, 1, 180),
    ('A23', 'Latte di mandorle', 1, 2, 3, 17),
    ('A24', 'Tofu', 8, 2, 4, 76),
    ('A25', 'Tempeh', 19, 9, 11, 195),
    ('A26', 'Seitan', 25, 14, 1, 150),
    ('A27', 'Fragole', 1, 8, 0, 32),
    ('A28', 'Banane', 1, 23, 0, 89),
    ('A29', 'Avena', 5, 27, 2, 150),
    ('A30', 'Ananas', 1, 13, 0, 50),
    ('A31', 'Pesche', 1, 10, 0, 39),
    ('A32', 'Anguria', 1, 8, 0, 30),
    ('A33', 'Kiwi', 1, 15, 0, 61),
    ('A34', 'Arance', 1, 12, 0, 47),
    ('A35', 'Mango', 1, 15, 0, 60),
    ('A36', 'Cocco', 3, 15, 33, 354),
    ('A37', 'Melanzane', 1, 6, 0, 25),
    ('A38', 'Cavolfiore', 2, 5, 0, 25),
    ('A39', 'Cavolini di Bruxelles', 3, 9, 0, 43),
    ('A40', 'Mais', 3, 19, 1, 86),
    ('A41', 'Barbabietole', 2, 10, 0, 43),
    ('A42', 'Noci', 15, 14, 65, 654),
    ('A43', 'Pistacchi', 20, 28, 45, 562),
    ('A44', 'Semi di zucca', 30, 10, 50, 574),
    ('A45', 'Semi di chia', 17, 42, 31, 486),
    ('A46', 'Semi di lino', 18, 29, 42, 534),
    ('A47', 'Burro di arachidi', 25, 20, 50, 588),
    ('A48', 'Yogurt greco', 10, 6, 0, 59),
    ('A49', 'Formaggio cheddar', 25, 1, 33, 402),
    ('A50', 'Gelato', 4, 23, 11, 207);

-- Inserimento nella tabella ASSUNZIONE
INSERT INTO ASSUNZIONE (Username, Data_inizio, Nome, IdIntegratore, Quantita) VALUES
    ('cliente4', '2024-10-01', 'Lunedì', 'INTG001', 500),
    ('cliente4', '2024-10-01', 'Martedì', 'INTG002', 200),
    ('cliente4', '2024-10-01', 'Mercoledì', 'INTG003', 300),
    ('cliente4', '2024-10-01', 'Giovedì', 'INTG004', 400),
    ('cliente4', '2024-10-01', 'Venerdì', 'INTG005', 150),
    ('cliente4', '2024-10-01', 'Sabato', 'INTG006', 250),
    ('cliente4', '2024-10-01', 'Domenica', 'INTG007', 350);

-- Inserimento nella tabella INTEGRAZIONE
INSERT INTO INTEGRAZIONE (Username, Data_inizio, IdIntegratore) VALUES
    ('cliente4', '2024-10-01', 'INTG001'),
    ('cliente4', '2024-10-01', 'INTG002'),
    ('cliente4', '2024-10-01', 'INTG003');

-- Inserimento nella tabella AGGIORNAMENTO
INSERT INTO AGGIORNAMENTO (Username, Data, Descrizione, Peso, Circ_punto_vita, Circ_braccio, Cir_gambe) VALUES
    ('cliente4', '2024-08-15', 'Controllo mensile', 68, 82, 29, 53),
    ('cliente4', '2024-09-15', 'Controllo mensile', 67, 81, 28, 52),
    ('cliente5', '2024-08-20', 'Controllo settimanale', 72, 88, 30, 57),
    ('cliente5', '2024-09-20', 'Controllo settimanale', 71, 87, 29, 56);



-- Inserimento dei percorsi di formazione per diversi nutrizionisti
-- Nutrizionista4 con un solo titolo
INSERT INTO PERCORSO_DI_FORMAZIONE (Username, Nome_percorso, Data_inizio, Data_fine, Voto_conseguito) VALUES
    ('nutrizionista4', 'Master in Nutrizione e Metabolismo', '2023-01-15', '2023-07-15', 91);

-- Nutrizionista5 con un solo titolo
INSERT INTO PERCORSO_DI_FORMAZIONE (Username, Nome_percorso, Data_inizio, Data_fine, Voto_conseguito) VALUES
    ('nutrizionista5', 'Corso di Nutrizione Sportiva', '2023-03-01', '2023-08-01', 88);

-- Nutrizionista6 con più di un titolo
INSERT INTO PERCORSO_DI_FORMAZIONE (Username, Nome_percorso, Data_inizio, Data_fine, Voto_conseguito) VALUES
    ('nutrizionista6', 'Certificazione in Nutrizione Clinica', '2023-02-01', '2023-07-01', 90),
    ('nutrizionista6', 'Corso di Dietetica Avanzata', '2023-08-01', '2024-01-01', 85);

-- Nutrizionista7 con più di un titolo
INSERT INTO PERCORSO_DI_FORMAZIONE (Username, Nome_percorso, Data_inizio, Data_fine, Voto_conseguito) VALUES
    ('nutrizionista7', 'Master in Nutrizione e Dietetica', '2023-01-01', '2023-06-30', 92),
    ('nutrizionista7', 'Specializzazione in Alimentazione Sportiva', '2023-07-01', '2023-12-31', 89);

-- Nutrizionista8 con un solo titolo
INSERT INTO PERCORSO_DI_FORMAZIONE (Username, Nome_percorso, Data_inizio, Data_fine, Voto_conseguito) VALUES
    ('nutrizionista8', 'Diploma in Nutrizione e Benessere', '2023-04-01', '2023-09-01', 87);

-- Nutrizionista9 con più di un titolo
INSERT INTO PERCORSO_DI_FORMAZIONE (Username, Nome_percorso, Data_inizio, Data_fine, Voto_conseguito) VALUES
    ('nutrizionista9', 'Certificazione in Alimentazione e Metabolismo', '2023-03-15', '2023-09-15', 90),
    ('nutrizionista9', 'Corso di Nutrizione Pediatrica', '2023-10-01', '2024-03-01', 86);

-- Nutrizionista10 con un solo titolo
INSERT INTO PERCORSO_DI_FORMAZIONE (Username, Nome_percorso, Data_inizio, Data_fine, Voto_conseguito) VALUES
    ('nutrizionista10', 'Specializzazione in Nutrizione Clinica e Sportiva', '2023-05-01', '2023-10-01', 93);


-- Lunedì 2024-10-01
INSERT INTO RICETTA (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, Difficolta, Descrizione, Tempo_Preparazione)
VALUES
    ('cliente4', '2024-10-01', 'Lunedì', 'Colazione', 'Omelette di Pollo', 'Facile', 'Omelette preparata con pollo e spinaci.', '15 minuti'),
    ('cliente4', '2024-10-01', 'Lunedì', 'Pranzo', 'Insalata di Pollo', 'Media', 'Insalata con pollo grigliato, pomodori e cetrioli.', '20 minuti'),
    ('cliente4', '2024-10-01', 'Lunedì', 'Cena', 'Salmone al Forno', 'Facile', 'Salmone cotto al forno con limone e erbe.', '25 minuti'),

    -- Martedì 2024-10-01
    ('cliente4', '2024-10-01', 'Martedì', 'Colazione', 'Smoothie di Frutta', 'Facile', 'Smoothie con banana, fragole e yogurt.', '10 minuti'),
    ('cliente4', '2024-10-01', 'Martedì', 'Pranzo', 'Riso e Verdure', 'Media', 'Riso basmati con verdure miste e spezie.', '30 minuti'),
    ('cliente4', '2024-10-01', 'Martedì', 'Cena', 'Petto di Pollo con Broccoli', 'Facile', 'Petto di pollo saltato con broccoli e aglio.', '20 minuti'),

    -- Mercoledì 2024-10-01
    ('cliente4', '2024-10-01', 'Mercoledì', 'Colazione', 'Yogurt e Granola', 'Facile', 'Yogurt greco con granola e miele.', '5 minuti'),
    ('cliente4', '2024-10-01', 'Mercoledì', 'Pranzo', 'Quinoa con Verdure', 'Media', 'Quinoa con peperoni, zucchine e cipolla.', '25 minuti'),
    ('cliente4', '2024-10-01', 'Mercoledì', 'Cena', 'Bistecca alla Griglia', 'Facile', 'Bistecca di manzo grigliata con contorno di spinaci.', '30 minuti'),

    -- Giovedì 2024-10-01
    ('cliente4', '2024-10-01', 'Giovedì', 'Colazione', 'Toast con Avocado', 'Facile', 'Toast integrale con avocado e pomodoro.', '10 minuti'),
    ('cliente4', '2024-10-01', 'Giovedì', 'Pranzo', 'Pasta con Sugo di Pomodoro', 'Facile', 'Pasta integrale con sugo di pomodoro fresco.', '20 minuti'),
    ('cliente4', '2024-10-01', 'Giovedì', 'Cena', 'Tofu Saltato con Verdure', 'Media', 'Tofu saltato con broccoli, peperoni e salsa di soia.', '25 minuti'),

    -- Venerdì 2024-10-01
    ('cliente4', '2024-10-01', 'Venerdì', 'Colazione', 'Pancake con Frutta', 'Facile', 'Pancake serviti con fragole e sciroppo d’acero.', '20 minuti'),
    ('cliente4', '2024-10-01', 'Venerdì', 'Pranzo', 'Frittata di Verdure', 'Facile', 'Frittata con spinaci, peperoni e cipolle.', '15 minuti'),
    ('cliente4', '2024-10-01', 'Venerdì', 'Cena', 'Pesce al Vapore con Patate', 'Facile', 'Pesce al vapore servito con patate lessate.', '25 minuti'),

    -- Sabato 2024-10-01
    ('cliente4', '2024-10-01', 'Sabato', 'Colazione', 'Smoothie Verde', 'Facile', 'Smoothie con spinaci, kiwi e mela.', '10 minuti'),
    ('cliente4', '2024-10-01', 'Sabato', 'Pranzo', 'Hamburger di Tacchino', 'Media', 'Hamburger di tacchino con insalata e pomodori.', '20 minuti'),
    ('cliente4', '2024-10-01', 'Sabato', 'Cena', 'Stufato di Manzo', 'Media', 'Stufato di manzo con carote e patate.', '40 minuti'),

    -- Domenica 2024-10-01
    ('cliente4', '2024-10-01', 'Domenica', 'Colazione', 'Chia Pudding', 'Facile', 'Pudding di semi di chia con latte di mandorle e frutta.', '10 minuti'),
    ('cliente4', '2024-10-01', 'Domenica', 'Pranzo', 'Paella di Verdure', 'Media', 'Paella con riso, peperoni, zucchine e piselli.', '30 minuti'),
    ('cliente4', '2024-10-01', 'Domenica', 'Cena', 'Pollo alla Griglia', 'Facile', 'Pollo grigliato con insalata mista.', '25 minuti');


-- Lunedì 2024-10-01

-- Colazione: Omelette di Pollo
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Lunedì', 'Colazione', 'Omelette di Pollo', 'A1', 150), -- Pollo
    ('cliente4', '2024-10-01', 'Lunedì', 'Colazione', 'Omelette di Pollo', 'A9', 100); -- Spinaci

-- Pranzo: Insalata di Pollo
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Lunedì', 'Pranzo', 'Insalata di Pollo', 'A1', 200), -- Pollo
    ('cliente4', '2024-10-01', 'Lunedì', 'Pranzo', 'Insalata di Pollo', 'A10', 100), -- Pomodori
    ('cliente4', '2024-10-01', 'Lunedì', 'Pranzo', 'Insalata di Pollo', 'A9', 50);  -- Spinaci

-- Cena: Salmone al Forno
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Lunedì', 'Cena', 'Salmone al Forno', 'A8', 250); -- Salmone

-- Martedì 2024-10-01

-- Colazione: Smoothie di Frutta
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Martedì', 'Colazione', 'Smoothie di Frutta', 'A27', 100), -- Fragole
    ('cliente4', '2024-10-01', 'Martedì', 'Colazione', 'Smoothie di Frutta', 'A28', 100), -- Banane
    ('cliente4', '2024-10-01', 'Martedì', 'Colazione', 'Smoothie di Frutta', 'A48', 150); -- Yogurt greco

-- Pranzo: Riso e Verdure
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Martedì', 'Pranzo', 'Riso e Verdure', 'A2', 200), -- Riso
    ('cliente4', '2024-10-01', 'Martedì', 'Pranzo', 'Riso e Verdure', 'A9', 100), -- Spinaci
    ('cliente4', '2024-10-01', 'Martedì', 'Pranzo', 'Riso e Verdure', 'A20', 50);  -- Zucchine

-- Cena: Petto di Pollo con Broccoli
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Martedì', 'Cena', 'Petto di Pollo con Broccoli', 'A1', 200), -- Pollo
    ('cliente4', '2024-10-01', 'Martedì', 'Cena', 'Petto di Pollo con Broccoli', 'A16', 150); -- Broccoli

-- Mercoledì 2024-10-01

-- Colazione: Yogurt e Granola
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Mercoledì', 'Colazione', 'Yogurt e Granola', 'A48', 200), -- Yogurt greco
    ('cliente4', '2024-10-01', 'Mercoledì', 'Colazione', 'Yogurt e Granola', 'A29', 50);  -- Avena

-- Pranzo: Quinoa con Verdure
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Mercoledì', 'Pranzo', 'Quinoa con Verdure', 'A15', 150), -- Quinoa
    ('cliente4', '2024-10-01', 'Mercoledì', 'Pranzo', 'Quinoa con Verdure', 'A19', 100), -- Peperoni
    ('cliente4', '2024-10-01', 'Mercoledì', 'Pranzo', 'Quinoa con Verdure', 'A20', 50);  -- Zucchine

-- Cena: Bistecca alla Griglia
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Mercoledì', 'Cena', 'Bistecca alla Griglia', 'A21', 250); -- Bistecca di manzo

-- Giovedì 2024-10-01

-- Colazione: Toast con Avocado
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Giovedì', 'Colazione', 'Toast con Avocado', 'A4', 100), -- Pane integrale
    ('cliente4', '2024-10-01', 'Giovedì', 'Colazione', 'Toast con Avocado', 'A1', 50);  -- Avocado (considerato come un ingrediente generico)

-- Pranzo: Pasta con Sugo di Pomodoro
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Giovedì', 'Pranzo', 'Pasta con Sugo di Pomodoro', 'A22', 200), -- Pasta integrale
    ('cliente4', '2024-10-01', 'Giovedì', 'Pranzo', 'Pasta con Sugo di Pomodoro', 'A10', 100); -- Pomodori

-- Cena: Tofu Saltato con Verdure
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Giovedì', 'Cena', 'Tofu Saltato con Verdure', 'A24', 150), -- Tofu
    ('cliente4', '2024-10-01', 'Giovedì', 'Cena', 'Tofu Saltato con Verdure', 'A16', 100); -- Broccoli

-- Venerdì 2024-10-01

-- Colazione: Pancake con Frutta
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Venerdì', 'Colazione', 'Pancake con Frutta', 'A29', 100), -- Avena (per pancake)
    ('cliente4', '2024-10-01', 'Venerdì', 'Colazione', 'Pancake con Frutta', 'A27', 100); -- Fragole

-- Pranzo: Frittata di Verdure
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Venerdì', 'Pranzo', 'Frittata di Verdure', 'A11', 200), -- Uova
    ('cliente4', '2024-10-01', 'Venerdì', 'Pranzo', 'Frittata di Verdure', 'A16', 100); -- Broccoli

-- Cena: Pesce al Vapore con Patate
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Venerdì', 'Cena', 'Pesce al Vapore con Patate', 'A8', 200), -- Salmone
    ('cliente4', '2024-10-01', 'Venerdì', 'Cena', 'Pesce al Vapore con Patate', 'A40', 150); -- Mais

-- Sabato 2024-10-01

-- Colazione: Smoothie Verde
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Sabato', 'Colazione', 'Smoothie Verde', 'A32', 150), -- Anguria
    ('cliente4', '2024-10-01', 'Sabato', 'Colazione', 'Smoothie Verde', 'A33', 100), -- Kiwi
    ('cliente4', '2024-10-01', 'Sabato', 'Colazione', 'Smoothie Verde', 'A48', 150); -- Yogurt greco

-- Pranzo: Hamburger di Tacchino
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Sabato', 'Pranzo', 'Hamburger di Tacchino', 'A1', 200), -- Tacchino (considerato come pollo in questo esempio)
    ('cliente4', '2024-10-01', 'Sabato', 'Pranzo', 'Hamburger di Tacchino', 'A20', 100); -- Zucchine

-- Cena: Stufato di Manzo
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Sabato', 'Cena', 'Stufato di Manzo', 'A21', 250), -- Bistecca di manzo
    ('cliente4', '2024-10-01', 'Sabato', 'Cena', 'Stufato di Manzo', 'A41', 100); -- Barbabietole

-- Domenica 2024-10-01

-- Colazione: Chia Pudding
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Domenica', 'Colazione', 'Chia Pudding', 'A45', 50), -- Semi di chia
    ('cliente4', '2024-10-01', 'Domenica', 'Colazione', 'Chia Pudding', 'A48', 200), -- Yogurt greco
    ('cliente4', '2024-10-01', 'Domenica', 'Colazione', 'Chia Pudding', 'A27', 50); -- Fragole

-- Pranzo: Paella di Verdure
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Domenica', 'Pranzo', 'Paella di Verdure', 'A15', 150), -- Quinoa
    ('cliente4', '2024-10-01', 'Domenica', 'Pranzo', 'Paella di Verdure', 'A19', 100), -- Peperoni
    ('cliente4', '2024-10-01', 'Domenica', 'Pranzo', 'Paella di Verdure', 'A40', 100); -- Mais

-- Cena: Pollo alla Griglia
INSERT INTO CREAZIONE (INC_COM_Username, INC_COM_Data_inizio, INC_COM_Nome, INC_Nome, Nome, IdAlimento, Peso)
VALUES
    ('cliente4', '2024-10-01', 'Domenica', 'Cena', 'Pollo alla Griglia', 'A1', 200), -- Pollo
    ('cliente4', '2024-10-01', 'Domenica', 'Cena', 'Pollo alla Griglia', 'A16', 100); -- Broccoli
    
-- Popolazione della tabella ABBONAMENTO
INSERT INTO ABBONAMENTO (SOT_Username, SOT_Durata_mesi, Username, Data_Acquisto, Sconto)
VALUES
    ('nutrizionista4', 1, 'cliente4', '2024-09-30', NULL),  -- Prezzo 30
    ('nutrizionista4', 1, 'cliente5', '2024-11-01', NULL),  -- Prezzo 30
    ('nutrizionista4', 1, 'cliente6', '2025-01-01', NULL),  -- Prezzo 30
    ('nutrizionista4', 1, 'cliente7', '2025-03-01', NULL),  -- Prezzo 30
    ('nutrizionista4', 1, 'cliente8', '2025-05-01', NULL),  -- Prezzo 30
    ('nutrizionista4', 1, 'cliente9', '2025-06-01', NULL),  -- Prezzo 30

    ('nutrizionista4', 6, 'cliente4', '2024-09-30', NULL),  -- Prezzo 150
    ('nutrizionista4', 6, 'cliente5', '2024-11-01', NULL),  -- Prezzo 150
    ('nutrizionista4', 6, 'cliente6', '2025-01-01', NULL),  -- Prezzo 150
    ('nutrizionista4', 6, 'cliente7', '2025-03-01', NULL),  -- Prezzo 150
    ('nutrizionista4', 6, 'cliente8', '2025-05-01', NULL),  -- Prezzo 150
    ('nutrizionista4', 6, 'cliente9', '2025-06-01', NULL),  -- Prezzo 150

    ('nutrizionista4', 12, 'cliente4', '2024-09-30', NULL),  -- Prezzo 280
    ('nutrizionista4', 12, 'cliente5', '2024-11-01', NULL),  -- Prezzo 280
    ('nutrizionista4', 12, 'cliente6', '2025-01-01', NULL),  -- Prezzo 280
    ('nutrizionista4', 12, 'cliente7', '2025-03-01', NULL),  -- Prezzo 280
    ('nutrizionista4', 12, 'cliente8', '2025-05-01', NULL),  -- Prezzo 280
    ('nutrizionista4', 12, 'cliente9', '2025-06-01', NULL);  -- Prezzo 280


