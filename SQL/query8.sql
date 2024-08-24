-- 8. Visualizzare i 10 migliori nutrizionisti (quelli con la media di stelle più alta)

SELECT NUTRIZIONISTA.Username, NUTRIZIONISTA.Nome_, NUTRIZIONISTA.Cognome, NUTRIZIONISTA.Media_Stelle
FROM NUTRIZIONISTA
ORDER BY Media_Stelle DESC
LIMIT 10;
