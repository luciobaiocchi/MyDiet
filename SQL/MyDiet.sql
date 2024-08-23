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
                               Cir_gambe int not null,
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
