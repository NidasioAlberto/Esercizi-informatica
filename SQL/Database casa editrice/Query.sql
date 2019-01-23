-- 1: Dato il titolo di una pubblicazione, ricercare gli articoli pubblicati in un determinato anno;

select *
from rivista A
where A.rivista = (
    select B.titolo
    from rivista B
    where B.titolo = "titolo pubblicazione"
);

-- 2: Dato il titolo di una pubblicazione, ricercare gli abbonati annuali;

select B.nome, B.cognome, A.dataSottoscrizione
from abbonamento A
join Abbonato B
on A.abbonato = B.codiceFiscale
where A.rivista = "titolo pubblicazione" and A.periodoDiValidità = "annuale";

-- 3: Dato il nominativo di un abbonato, stabilire a quante riviste è abbonato;

select count(idAbbonamento)
from abbonamento A
where A.abbonato = (
    select B.codiceFiscale
    from abbonato B
    where B.nome = "nome abbonato"
);


-- 4: Dato un argomento, elencare le pubblicazioni in cui è trattato;

select A.titolo
from rivista A
where A.argomenti like "%argomento%"

-- 5: Riportare per ogni pubblicazione il numero di abbonamenti;

select A.titolo, count(B.idAbbomaneto)
from rivista A
join abbonamento B
on A.titolo = B.rivista
group by A.titolo

-- 6: Visualizzare i giornali con almeno 5000 abbonati annuali;

select A.titolo, count(B.idAbbonameto)
from rivista A
join abbonamento B
on A.titolo = B.rivista
group by A.titolo
where count(B.idAbbonamento) > 5000;

-- 7: Dati i titoli di due pubblicazioni, visualizzarne gli abbonati comuni;

-- BOH!

-- unisco le query
select E.nome, E.cognome
from (
    -- lista abbonamenti con pubblicazione 1
    select A.abbonato
    from abbonamento A
    where A.rivista = "pubblicazione 1"
) C
join (
    -- lista abbonamenti con pubblicazione 2
    select B.abbonato
    from abbonamento B
    where B.rivista = "pubblicazione 1"
) D
-- unisco le due liste solo quando l'abbonato è lo stesso
on C.abbonato = D.abbonato
-- e prendo i dati dalla tabella abbonato
join abbonato E
on D.abbonato = E.codiceFiscale;

-- oppure

select A.nome, A.cognome
from abbonato A
join abbonamento B
on A.codiceFiscale = B.abbonato
group by B.abbonato
having B.rivista = "pubblicazione 1" and B.rivista = "pubblicazione 2"; -- non sono sicuro di questa parte

-- soluzione in classe !!!

select B.nome, B.cognome
from abbonamento A
join abbonato B
on A.abbonato = B.codiceFiscale
group by A.abbonato
having count(*) >= 2;

-- 8: Dato il titolo di una pubblicazione, elencare le pubblicazioni che trattano i suoi stessi argomenti.

-- Con la mia configurazione attuale non si può fare, bisogna avere l'argomento come entità in una tabella a parte