select *
from attore
join (
	select idAttore
	from film_attore
	where Oscar != 0
) as film_attore_oscar
on attore.idAttore = film_attore_oscar.idAttore;