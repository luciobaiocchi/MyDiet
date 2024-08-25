-- 9. Ottenere una lista con i nutrizionisti che hanno portato all’obiettivo almeno il 50% dei clienti

SELECT SVI_Username, COUNT(*) AS Numero_Clienti, SUM(CASE WHEN Raggiunto = 'S' THEN 1 ELSE 0 END) AS Clienti_Raggiunti_Obiettivo,
       (SUM(CASE WHEN Raggiunto = 'S' THEN 1 ELSE 0 END) / COUNT(*)) * 100 AS Percentuale
FROM DIETA
JOIN OBBIETTIVO ON DIETA.Username = OBBIETTIVO.Username
GROUP BY SVI_Username
HAVING Percentuale >= 50;

-- utilizzando l'attibuto ridondante all'interno di nutrizionista
SELECT *
FROM NUTRIZIONISTA
WHERE Percentuale_soddisfatti >= 50;