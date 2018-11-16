--volgio sapere quanto paga ogni utente per tutti i suoi contratti
select cliente.nominativo, contratto.dal
from campeggio.cliente
join campeggio.contratto on cliente.id = contratto.idcliente;