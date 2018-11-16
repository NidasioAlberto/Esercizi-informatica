/*quanti film per ciascun genere ho in videoteca*/ 

select count(film.Id) as Numero_di_film, genere.Nome
from film
join genere
on film.IdGenere = genere.IdGenere
group by film.IdGenere;