select Titolo
from film
join (
	select IdGenere
	from genere
where Nome in ("COMMEDIA", "FANTASY", "ANIMAZIONE")
) as generi
on film.IdGenere = generi.IdGenere;