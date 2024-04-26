# Progetto-DB
## Programma per la gestione di diete
L’obiettivo del progetto è realizzare un programma che permette il salvataggio e organizzazione di
piani alimentari personalizzati.

Ogni utente cliente è registrato con nome utente e password. Può scegliere di affidarsi ad un
nutrizionista fra quelli disponibili e iniziare un piano alimentare, dopodiché potrà visualizzare la
propria dieta in uno spazio a lui riservato e caricare aggiornamenti sulla propria condizione,
composti dal peso attuale circonferenza del punto vita braccia e gambe e un messaggio opzionale.

Ogni dieta associata ad un singolo cliente avrà una quota calorica e il totale dei macro-nutrienti
(proteine, carboidrati, grassi). Una dieta è composta da una scheda per ogni giorno della settimana,
nella quale sono salvati i pasti del giorno che possono variare di giorno in giorno. Un pasto è
composto da un insieme di alimenti con relativo peso.

Nel DB vengono anche salvati gli utenti nutrizionisti, che possono essere specializzati in un
ambito preciso: dimagrimento, alimentazione sportiva, aumento di massa muscolare, etc… Ognuno
dei quali ha anche associato nel proprio profilo pubblico delle recensioni lasciate dai clienti (voto
composto da 1 a 5 stelle, e una valutazione scritta opzionale).

Esiste anche una parte riservata, visibile solo all’utente nutrizionista, nella quale quest’ultimo può
visualizzare tutti i suoi clienti associati al loro obbiettivo, alla dieta personalizzata e ai vari
aggiornamenti datati. In questa parte potrà anche aggiornare una dieta in base all’andamento dei
progressi e in funzione dell’obbiettivo del cliente.

### Le funzionalità offerte dalla programma sono le seguenti:

1. Visualizzazione della dieta da parte di un cliente
2. Visualizzazione dell’elenco dei nutrizionisti da parte di un utente cliente
3. Inserire i vari aggiornamenti da parte di un utente cliente con relativa data e messaggio descrittivo
4. Visualizzare tutti i clienti di un nutrizionista
5. Modificare pasti e diete di ogni cliente
6. Visualizzare storico dei progressi di un cliente
7. Inserire nuovi alimenti nel catalogo per rendere più varia una dieta

Sarà possibile consultare informazioni aggregate:

1. Visualizzare i 10 migliori nutrizionisti (quelli con la media di stelle più alta)
2. Ottenere una lista con i nutrizionisti che hanno portato all’obiettivo almeno il 50% dei clienti
