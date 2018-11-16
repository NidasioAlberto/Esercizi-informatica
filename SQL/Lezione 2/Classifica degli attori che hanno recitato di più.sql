/*per ogni attore in quanti film ha recitato e la classificha deve essere in ordine decrescente di conteggio*/

select count(film_attore.IdAttore) as Numero_di_film, attore.Nome as Nome_attore
from attore
join film_attore
on attore.IdAttore = film_attore.IdAttore
group by film_attore.IdAttore
order by Numero_di_film desc;