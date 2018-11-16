/*select cliente.nominativo, contratto.dal
from campeggio.cliente
join campeggio.contratto on cliente.id = contratto.idcliente;*/

/*recupero i contratti di ogni utente*/
select cliente.nominativo, contratto.dal
from campeggio.cliente
join campeggio.contratto on cliente.id = contratto.idcliente;