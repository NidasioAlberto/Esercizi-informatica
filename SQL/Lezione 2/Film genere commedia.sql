select *
from film
join (
	select idgenere
    from genere
    where Nome like "commedia"
) as generecommedia
on film.IdGenere = generecommedia.idgenere;