-- 5. Modificare pasti e diete di ogni cliente

UPDATE PASTO
SET Totale_calorie = 500 -- Sostituire con la nuova quantità di calorie o altri campi da modificare
WHERE COM_Username = 'cliente4' AND COM_Data_inizio = '2024-10-01' AND COM_Nome = 'Lunedì' AND Nome = 'Colazione'; -- Modificare i criteri di selezione in base al pasto da aggiornare
