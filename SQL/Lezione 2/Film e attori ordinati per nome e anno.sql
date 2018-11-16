/*voglio i film e attore associati e ordinati per nome e data di uscita dei film*/

select film.Titolo, attori_film.Nome, film.Anno
from film
join (
	select attore.Nome, film_attore.Id
    from attore
    join film_attore
    on attore.IdAttore = film_attore.IdAttore
) as attori_film
on film.Id = attori_film.Id
order by attori_film.Nome, film.Anno;