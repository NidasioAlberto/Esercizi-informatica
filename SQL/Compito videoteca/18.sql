/*Elenco in ordine cronologico tutti i film che hanno vinto un oscar*/
select Titolo, Anno
from film
join (
	/*recupero i film con oscar, si intende se il regista del film ha vinto un oscar*/
	select Id
	from regista_film
	where Oscar > 0
) as film_oscar
on film.Id = film_oscar.Id
order by Anno;