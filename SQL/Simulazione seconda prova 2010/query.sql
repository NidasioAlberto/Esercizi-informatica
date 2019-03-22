-- Elenco cronologico degli interventi di cellulari del tipo
-- sostituzione display effettuati nell'ultimo mese
select <Attributi della tabella richieste>
from Richieste
join Dispositivo
on Richieste.idDispositivo = Dispositivo.id
where Dispositivo.tipo = "cellulare"
and descrizioneIntervento = "sostituzione display"
and dataRichiesta > "<data da controllare>";

-- Dati dei clienti che dopo 30 giorni non hanno ancora avuto un
-- articolo consegnato
select *
from Richieste
where
    datediff(day, dataRichiesta, dataChiusura) or
    (dataChiusura is null and datediff(day, dataRichiesta, getdate()));

-- Data marca e modello visualizzare la durata media degli interventi
select avg(tempoStimato)
from Richieste
join Dispositivo
on Richieste.idDispositivo = Dispositivo.id
where
    Dispositivo.marca = "<marca>" and
    Dispositivo.modello = "<modello>" and
    dataChiusura is not null -- Escludo gli interventi non conclusi

-- Numero di riparazioni positive in un dato anno divisi per marca
select Dispositivo.marca, count(Dispositivo.marca)
from Richieste
join Dispositivo
on Richieste.idDispositivo = Dispositivo.id
where
    year(Richieste.dataRichiesta) and
    Richieste.dataChiusura is not null
group by Dispositivo.marca

-- Numero di ticket creati da ciascun PDA
select PDA.nome, count(PDA.nomne), avg(Richiesta.tempoStimato)
from Richieste
join PDA
on Richieste.idPDA = PDA.id
group by PDA.nome;

-- stato di un intervento
select statoTicket
from Richieste
where id = <id ticket>;

