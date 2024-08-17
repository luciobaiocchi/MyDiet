-- *********************************************
-- * Standard SQL generation                   
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Mon Aug 12 11:17:02 2024 
-- * LUN file: C:\Users\Luca\Desktop\FootStats\docs\ER\FootStats.lun 
-- * Schema: FootStats/SQL 
-- ********************************************* 


-- Database Section
-- ________________ 
drop database if exists FootStats;
create database FootStats;
use FootStats;

-- DBSpace Section
-- _______________


-- Tables Section
-- _____________ 

create table ACCOUNT (
     Nome varchar(64) not null,
     Cognome varchar(64) not null,
     Username varchar(64) not null,
     Password varchar(256) not null,
     constraint ID_ACCOUNT_ID primary key (Username));

create table AMMINISTRATORE (
     Username varchar(64) not null,
     constraint ID_AMMIN_ACCOU_ID primary key (Username));

create table CALCIATORE (
     Nome varchar(64) not null,
     Cognome varchar(64) not null,
     CF char(32) not null,
     Data_di_nascita date not null,
     Altezza numeric(64) not null,
     Luogo_di_nascita varchar(64) not null,
     Piede_preferito varchar(64) not null,
     constraint ID_CALCIATORE_ID primary key (CF));

create table COMPETIZIONE (
     TipoCompetizione varchar(64) not null,
     AnnoCalcistico varchar(64) not null,
     CodiceCompetizione varchar(128) not null,
     constraint ID_COMPETIZIONE_ID primary key (AnnoCalcistico, TipoCompetizione, CodiceCompetizione));

create table CONTRATTO (
     CF_Calciatore char(32) not null,
     DataFirma date not null,
     Durata varchar(64) not null,
     Valore numeric(64) not null,
     constraint ID_CONTRATTO_ID primary key (CF_Calciatore, DataFirma));

create table GOL (
     AnnoCalcistico varchar(64) not null,
     TipoCompetizione varchar(64) not null,
     CodiceCompetizione varchar(128) not null,
     CodicePartita int not null,
     Marcatore varchar(64) not null,
     Minuto numeric(32) not null,
     Assistman varchar(64) not null,
     constraint ID_GOL_ID primary key (AnnoCalcistico, TipoCompetizione, CodiceCompetizione, CodicePartita, Marcatore, Minuto, Assistman));

create table PALMARES_SQUAD (
     NomeSquadra varchar(64) not null,
     NomeTrofeo varchar(64) not null,
     constraint ID_PALMARES_SQUAD_ID primary key (NomeSquadra, NomeTrofeo));

create table PALMARES_STAGIONE_GIOCATORE (
     AnnoCalcistico varchar(64) not null,
     CF_Calciatore char(32) not null,
     CodiceStatsStagionale int not null,
     NomeTrofeo varchar(64) not null,
     constraint ID_PALMARES_STAGIONE_GIOCATORE_ID primary key (AnnoCalcistico, CF_Calciatore, CodiceStatsStagionale, NomeTrofeo));

create table PARTECIPAZIONE (
     NomeSquadra varchar(64) not null,
     AnnoCalcistico varchar(64) not null,
     TipoCompetizione varchar(64) not null,
     CodiceCompetizione varchar(128) not null,
     Piazzamento varchar(32) not null,
     constraint ID_PARTECIPAZIONE_ID primary key (NomeSquadra, AnnoCalcistico, TipoCompetizione, CodiceCompetizione));

create table PARTITA (
     AnnoCalcistico varchar(64) not null,
     TipoCompetizione varchar(64) not null,
     CodiceCompetizione varchar(128) not null,
     CodicePartita int not null,
     Data date not null,
     Stadio varchar(64) not null,
     Ora numeric(64) not null,
     SquadraCasa varchar(64) not null,
     SquadraOspite varchar(64) not null,
     constraint ID_PARTITA_ID primary key (AnnoCalcistico, TipoCompetizione, CodiceCompetizione, CodicePartita));

create table RICHIESTE (
     Username varchar(64) not null,
     CodiceRichiesta int not null,
     Tipologia varchar(64) not null,
     Descrizione varchar(500) not null,
     constraint ID_RICHIESTE_ID primary key (Username, CodiceRichiesta));

create table SQUADRA (
     Nome varchar(64) not null,
     Nazione varchar(64) not null,
     TipoSquadra varchar(64) not null,
     Record_goal numeric(32) not null,
     Record_Presenze numeric(32) not null,
     constraint ID_SQUADRA_ID primary key (Nome));

create table STAGIONE (
     AnnoCalcistico varchar(64) not null,
     constraint ID_STAGIONE_ID primary key (AnnoCalcistico));

CREATE TABLE STATS_GIOCATORE_PARTITA (
    CF_Calciatore CHAR(32) NOT NULL,
    AnnoCalcistico VARCHAR(64) NOT NULL,
    TipoCompetizione VARCHAR(64) NOT NULL,
    CodiceCompetizione VARCHAR(128) NOT NULL,
    CodicePartita INT NOT NULL,
    CodiceStatsPartita INT NOT NULL,
    Goal DECIMAL(10, 2) NOT NULL,  
    Assist DECIMAL(10, 2) NOT NULL,  
    Cartellini DECIMAL(10, 2) NOT NULL,  
    PRIMARY KEY (AnnoCalcistico, TipoCompetizione, CodiceCompetizione, CodicePartita, CF_Calciatore, CodiceStatsPartita));


create table STATS_GIOCATORE_STAGIONE (
     CF_Calciatore char(32) not null,
     AnnoCalcistico varchar(64) not null,
     CodiceStatsStagionale int not null,
     Goal_stagionali numeric(32) not null,
     Assist_stagionali numeric(32) not null,
     Valore_di_mercato numeric(64) not null,
     Presenze numeric(64) not null,
     Numero_maglia numeric(32),
     Cartellini_stagionali numeric(32) not null,
     Ruolo varchar(64),
     constraint ID_STATS_GIOCATORE_STAGIONE_ID primary key (AnnoCalcistico, CF_Calciatore, CodiceStatsStagionale));

create table STORICO_PARTECIPAZIONI (
     CF_Calciatore char(32) not null,
     AnnoCalcistico varchar(64) not null,
     CodiceStorico varchar(128) not null,
     NomeSquadra varchar(64),
     constraint ID_STORICO_PARTECIPAZIONI_ID primary key (CF_Calciatore, AnnoCalcistico, CodiceStorico));

create table TIPO_COMPETIZIONE (
     Nome varchar(64) not null,
     Descrizione_ varchar(256) not null,
     Numero_partecipanti numeric(10) not null,
     constraint ID_TIPO_COMPETIZIONE_ID primary key (Nome));

create table Trofeo (
     NomeTrofeo varchar(64) not null,
     Quantita numeric(16) not null,
     constraint ID_Trofeo_ID primary key (NomeTrofeo));

create table UTENTE (
     Username varchar(64) not null,
     Targhetta varchar(64),
     constraint ID_UTENT_ACCOU_ID primary key (Username));


-- Constraints Section
-- ___________________ 

alter table AMMINISTRATORE
    add constraint ID_AMMIN_ACCOU_FK foreign key (Username) references ACCOUNT(Username);

alter table COMPETIZIONE add constraint REF_COMPE_STAGI
     foreign key (AnnoCalcistico)
     references STAGIONE(AnnoCalcistico);

alter table COMPETIZIONE add constraint REF_COMPE_TIPO__FK
     foreign key (TipoCompetizione)
     references TIPO_COMPETIZIONE(Nome);

alter table CONTRATTO add constraint REF_CONTR_CALCI
     foreign key (CF_Calciatore)
     references CALCIATORE(CF);

alter table GOL add constraint REF_GOL_PARTI
     foreign key (AnnoCalcistico, TipoCompetizione, CodiceCompetizione, CodicePartita)
     references PARTITA(AnnoCalcistico, TipoCompetizione, CodiceCompetizione, CodicePartita);

alter table PALMARES_SQUAD add constraint REF_PALMA_Trofe_1_FK
     foreign key (NomeTrofeo)
     references Trofeo(NomeTrofeo);

alter table PALMARES_SQUAD add constraint REF_PALMA_SQUAD
     foreign key (NomeSquadra)
     references SQUADRA(Nome);

alter table PALMARES_STAGIONE_GIOCATORE add constraint REF_PALMA_Trofe_FK
     foreign key (NomeTrofeo)
     references Trofeo(NomeTrofeo);

alter table PALMARES_STAGIONE_GIOCATORE add constraint REF_PALMA_STATS
     foreign key (AnnoCalcistico, CF_Calciatore, CodiceStatsStagionale)
     references STATS_GIOCATORE_STAGIONE(AnnoCalcistico, CF_Calciatore, CodiceStatsStagionale);

alter table PARTECIPAZIONE add constraint REF_PARTE_COMPE_FK
     foreign key (AnnoCalcistico, TipoCompetizione, CodiceCompetizione)
     references COMPETIZIONE(AnnoCalcistico, TipoCompetizione, CodiceCompetizione);

alter table PARTECIPAZIONE add constraint REF_PARTE_SQUAD
     foreign key (NomeSquadra)
     references SQUADRA(Nome);

alter table PARTITA add constraint REF_PARTI_SQUAD_1_FK
     foreign key (SquadraCasa)
     references SQUADRA(Nome);

alter table PARTITA add constraint REF_PARTI_SQUAD_FK
     foreign key (SquadraOspite)
     references SQUADRA(Nome);

alter table PARTITA add constraint EQU_PARTI_COMPE
     foreign key (AnnoCalcistico, TipoCompetizione, CodiceCompetizione)
     references COMPETIZIONE(AnnoCalcistico, TipoCompetizione, CodiceCompetizione);

alter table RICHIESTE add constraint REF_RICHI_UTENT
     foreign key (Username)
     references UTENTE(Username);

alter table STATS_GIOCATORE_PARTITA add constraint REF_STATS_PARTI
     foreign key (AnnoCalcistico, TipoCompetizione, CodiceCompetizione, CodicePartita)
     references PARTITA(AnnoCalcistico, TipoCompetizione, CodiceCompetizione, CodicePartita);

alter table STATS_GIOCATORE_PARTITA add constraint REF_STATS_CALCI_1_FK
     foreign key (CF_Calciatore)
     references CALCIATORE(CF);

alter table STATS_GIOCATORE_STAGIONE add constraint REF_STATS_STAGI
     foreign key (AnnoCalcistico)
     references STAGIONE(AnnoCalcistico);

alter table STATS_GIOCATORE_STAGIONE add constraint REF_STATS_CALCI_FK
     foreign key (CF_Calciatore)
     references CALCIATORE(CF);

alter table STORICO_PARTECIPAZIONI add constraint REF_STORI_STAGI_FK
     foreign key (AnnoCalcistico)
     references STAGIONE(AnnoCalcistico);

alter table STORICO_PARTECIPAZIONI add constraint REF_STORI_SQUAD_FK
     foreign key (NomeSquadra)
     references SQUADRA(Nome);

alter table STORICO_PARTECIPAZIONI add constraint REF_STORI_CALCI
     foreign key (CF_Calciatore)
     references CALCIATORE(CF);

alter table UTENTE add constraint ID_UTENT_ACCOU_FK
     foreign key (Username)
     references ACCOUNT(Username);


-- Index Section
-- _____________ 

create unique index ID_ACCOUNT_IND
     on ACCOUNT (Username);

create unique index ID_AMMIN_ACCOU_IND
     on AMMINISTRATORE (Username);

create unique index ID_CALCIATORE_IND
     on CALCIATORE (CF);

create unique index ID_COMPETIZIONE_IND
     on COMPETIZIONE (AnnoCalcistico, TipoCompetizione, CodiceCompetizione);

create index REF_COMPE_TIPO__IND
     on COMPETIZIONE (TipoCompetizione);

create unique index ID_CONTRATTO_IND
     on CONTRATTO (CF_Calciatore, DataFirma);

create unique index ID_GOL_IND
     on GOL (AnnoCalcistico, TipoCompetizione, CodiceCompetizione, CodicePartita, Marcatore, Minuto, Assistman);

create unique index ID_PALMARES_SQUAD_IND
     on PALMARES_SQUAD (NomeSquadra, NomeTrofeo);

create index REF_PALMA_Trofe_1_IND
     on PALMARES_SQUAD (NomeTrofeo);

create unique index ID_PALMARES_STAGIONE_GIOCATORE_IND
     on PALMARES_STAGIONE_GIOCATORE (AnnoCalcistico, CF_Calciatore, CodiceStatsStagionale, NomeTrofeo);

create index REF_PALMA_Trofe_IND
     on PALMARES_STAGIONE_GIOCATORE (NomeTrofeo);

create unique index ID_PARTECIPAZIONE_IND
     on PARTECIPAZIONE (NomeSquadra, AnnoCalcistico, TipoCompetizione, CodiceCompetizione);

create index REF_PARTE_COMPE_IND
     on PARTECIPAZIONE (AnnoCalcistico, TipoCompetizione, CodiceCompetizione);

create unique index ID_PARTITA_IND
     on PARTITA (AnnoCalcistico, TipoCompetizione, CodiceCompetizione, CodicePartita);

create index REF_PARTI_SQUAD_1_IND
     on PARTITA (SquadraCasa);

create index REF_PARTI_SQUAD_IND
     on PARTITA (SquadraOspite);

create unique index ID_RICHIESTE_IND
     on RICHIESTE (Username, CodiceRichiesta);

create unique index ID_SQUADRA_IND
     on SQUADRA (Nome);

create unique index ID_STAGIONE_IND
     on STAGIONE (AnnoCalcistico);

create unique index ID_STATS_GIOCATORE_PARTITA_IND
     on STATS_GIOCATORE_PARTITA (AnnoCalcistico, TipoCompetizione, CodiceCompetizione, CodicePartita, CF_Calciatore, CodiceStatsPartita);

create index REF_STATS_CALCI_1_IND
     on STATS_GIOCATORE_PARTITA (CF_Calciatore);

create unique index ID_STATS_GIOCATORE_STAGIONE_IND
     on STATS_GIOCATORE_STAGIONE (AnnoCalcistico, CF_Calciatore, CodiceStatsStagionale);

create index REF_STATS_CALCI_IND
     on STATS_GIOCATORE_STAGIONE (CF_Calciatore);

create unique index ID_STORICO_PARTECIPAZIONI_IND
     on STORICO_PARTECIPAZIONI (CF_Calciatore, AnnoCalcistico, CodiceStorico);

create index REF_STORI_STAGI_IND
     on STORICO_PARTECIPAZIONI (AnnoCalcistico);

create index REF_STORI_SQUAD_IND
     on STORICO_PARTECIPAZIONI (NomeSquadra);

create unique index ID_TIPO_COMPETIZIONE_IND
     on TIPO_COMPETIZIONE (Nome);

create unique index ID_Trofeo_IND
     on Trofeo (NomeTrofeo);

create unique index ID_UTENT_ACCOU_IND
     on UTENTE (Username);

