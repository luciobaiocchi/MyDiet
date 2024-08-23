-- 1. Visualizzazione della dieta da parte di un cliente
SELECT DIETA.Username, DIETA.Data_inizio, DIETA.Data_fine, DIETA.Prezzo, 
       GIORNO.Nome AS Giorno, PASTO.Nome AS Pasto, PASTO.Totale_calorie
FROM DIETA
JOIN GIORNO ON DIETA.Username = GIORNO.Username AND DIETA.Data_inizio = GIORNO.Data_inizio
JOIN PASTO ON GIORNO.Username = PASTO.COM_Username AND GIORNO.Data_inizio = PASTO.COM_Data_inizio AND GIORNO.Nome = PASTO.COM_Nome
WHERE DIETA.Username = 'cliente4'; -- Sostituire 'cliente4' con il cliente desiderato
