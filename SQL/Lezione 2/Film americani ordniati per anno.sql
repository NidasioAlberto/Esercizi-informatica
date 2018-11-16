/*Voglio sapere quali film americani ordinati in ordine croniologico*/

select Titolo, anno
from film
where Nazione like "usa"
order by anno;

/*Voglio sapere quali film americani ordinati in oprdine alfabetico per regista*/

select Titolo, anno, registi.Nome
from film
join (
	select Id, Nome
    from regista
	join regista_film
    on regista_film.IdRegista = regista.IdRegista
) as registi
on registi.Id = film.Id
where Nazione like "usa"
order by registi.Nome;