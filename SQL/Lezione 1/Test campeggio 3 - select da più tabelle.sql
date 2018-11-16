select cliente.nominativo, contratto.fino_al
from campeggio.cliente, campeggio.contratto
where cliente.id = contratto.idcliente;