--questa query è più efficiente perchè utilizza il join, questo permette al database di utilizzare un sistema di indici per velocizzare l'operazione

select cliente.nominativo, contratto.dal, contratto.fino_al
from campeggio.cliente
join campeggio.contratto
on cliente.id = contratto.idcliente;