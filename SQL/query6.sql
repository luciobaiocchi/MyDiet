-- 6. Visualizzare storico dei progressi di un cliente


SELECT Data, Descrizione, Peso, Circ_punto_vita, Circ_braccio, Cir_gambe
FROM AGGIORNAMENTO
WHERE Username = 'cliente4' -- Sostituire 'cliente4' con il cliente desiderato
ORDER BY Data DESC;
