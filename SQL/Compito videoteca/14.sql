/*DIVIDO LA RICHIESTA IN DUE QUERY !!!!!
  UNA PER RECUPERARE IL NOME DEL REGISTA
  E UNA PER OTTENERE TUTTO IL CAST*/

/*per recuperare il regista si può utilizzare la query dell’esercizio precedente !!!!!!!!*/

/*recupero il cast*/
select attore.Nome
from attore
join (
	/*recupero tutti i registi di matrix*/
	select IdAttore
	from film_attore
	/*ATTENZIONE !!!!!!! NON UTILIZZO IL JOIN VISTO CHE ABBIAMO UN SOLO ID*/
	where Id = (
	/*recupero l’id del film di spy game*/
		select Id
		from film
		where Titolo = 'SPY GAME'
	)
) as attori_spy_game
on attore.IdAttore = attori_spy_game.IdAttore;
