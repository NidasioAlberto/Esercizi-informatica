select genere.Nome, count(film.Id) as Tot
from film
join genere
on film.IdGenere = genere.IdGenere
group by film.IdGenere
having Tot > 2;