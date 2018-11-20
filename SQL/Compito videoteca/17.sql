/*recupero il numero di film che ciascun regista ha fatto*/
select regista.Nome, COUNT(Id)
from regista_film
join regista
on regista.IdRegista = regista_film.IdRegista
group by regista.IdRegista;
