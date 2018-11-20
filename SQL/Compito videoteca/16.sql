/*recupero i nomi dei film noleggiati*/
select Titolo
from film
join (
	select distinct idfilm
	from prestito
) as film_noleggiati
on film.Id = film_noleggiati.idfilm;