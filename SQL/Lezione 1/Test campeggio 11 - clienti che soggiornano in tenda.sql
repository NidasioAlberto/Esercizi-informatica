/*trovare il nome dei clienti che soggiornano in tenda*/

/*recupero l'id del tipo di piazzola tenda*/
/*select id from campeggio.tipopiazzola where descrizione like "tenda";*/

/*recupero le piazzole con il tipo piazzola tenda*/
/*select id
from campeggio.piazzola
where idtipo = (
    select id
    from campeggio.tipopiazzola
    where descrizione like "tenda"
);*/

/*recupero tutti i contratti con queste piazzole*/
/*select idcontratto
from campeggio.elementi
join (
    select id
    from campeggio.piazzola
    where idtipo = (
        select id
        from campeggio.tipopiazzola
        where descrizione like "tenda"
    )
) as piazzolatenda
on elementi.idpiazzola = piazzolatenda.id;*/

/*recupero gli id dei clienti dei contratti che utilizzano delle piazzole di tipo tenda*/
/*select idcliente
from campeggio.contratto
join (
    select idcontratto
    from campeggio.elementi
    join (
        select id
        from campeggio.piazzola
        where idtipo = (
            select id
            from campeggio.tipopiazzola
            where descrizione like "tenda"
        )
    ) as piazzolatenda
    on elementi.idpiazzola = piazzolatenda.id
) as contrattipiazzola
on contratto.id = contrattipiazzola.idcontratto;*/

/*recuper il nominativo degli utenti che hanno soggiornato in tenda*/
select nominativo
from campeggio.cliente
join (
    select idcliente
    from campeggio.contratto
    join (
        select idcontratto
        from campeggio.elementi
        join (
            select id
            from campeggio.piazzola
            where idtipo = (
                select id
                from campeggio.tipopiazzola
                where descrizione like "tenda"
            )
        ) as piazzolatenda
        on elementi.idpiazzola = piazzolatenda.id
    ) as contrattipiazzola
    on contratto.id = contrattipiazzola.idcontratto
) as idclientitenda
on cliente.id = idclientitenda.idcliente;