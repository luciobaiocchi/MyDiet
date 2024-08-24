SELECT CLIENTE.Username, CLIENTE.Nome_, CLIENTE.Cognome, CLIENTE.Numero_di_telefono, CLIENTE.Mail
FROM CLIENTE
JOIN DIETA ON CLIENTE.Username = DIETA.Username
WHERE DIETA.SVI_Username = 'nutrizionista4'; -- Sostituire 'nutrizionista4' con il nutrizionista desiderato

