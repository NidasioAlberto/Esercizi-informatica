/*recupero i nomi dei registi di matrix*/
select regista.Nome
from regista
join (
	/*recupero tutti i registi di matrix*/
	select distinct IdRegista
	from regista_film
	join (
		/*recupero tutti i film di matrix*/
		select Id
		from film
		where Titolo like 'MATRIX %'
	) as film_matrix
	on regista_film.Id = film_matrix.Id
) as registi_matrix
on regista.IdRegista = registi_matrix.IdRegista;