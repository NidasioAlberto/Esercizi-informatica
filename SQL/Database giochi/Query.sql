-- Elenco di tutti i giochi di tipo azione

select A.nome
from gioco A
join genere B
on A.idGenere = B.id
where B.tipoDiGioco like "azione";

-- Elenco di tutti i giochi prodotti da Nintendo

select A.nome
from gioco A
join produttore B
on A.idProduttore = B.id
where B.nome like "nintendo";

-- Elenco di tutti i giochi usciti nel 2015

select nome
from gioco
where annoDiPubblicazione = 2015;

-- Numero dei giochi usciti nel 2010

select count(id)
from gioco
where annoDiPubblicazione = 2010;

-- Gioco più recente (DA RIVEDERE)

select distinct nome -- utilizzare distinct se ne si vuole sapere solo uno, altrimenti verranno elencati tutti i giochi pubblicati nel'anno più recente
from gioco
where annoDiPubblicazione = (select max(annoDiPubblicazione)
from gioco);

-- numero totale dei giochi di tipo azione e fantasy

select count(A.id)
from gioco A
join genere B
on A.idGenere = B.id
where B.tipoDiGioco like "azione" or B.tipoDiGioco like "fantasy";

-- elenco dei produttori dei giochi usciti nel 2008

select A.nome
from produttori A
join gioco B
on A.id = B.idProduttore
where B.annoDiPubblicazione = 2008;

-- elenco dei giochi di tipo azione con valutazione uguale a 7

select A.nome
from gioco A
join genere B
on A.idGenere = B.id
where B.tipoDiGioco like "azione" and A.valutazione = 7;

-- elenco dei giochi della nintendo con PEGI maggiore di 18

select A.nome
from gioco A
join produttori B
on A.idProduttore = B.id
where B.nome like "nintendo" and A.pegi > 18;