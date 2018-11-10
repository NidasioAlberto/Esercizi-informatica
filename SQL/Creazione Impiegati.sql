use AlbertoNidasio;

/*create table Impiegati(matricola int, cognome varchar(20), stipendio int);*/

delete from Impiegati;

insert into Impiegati (matricola, cognome, stipendio) values(0, 'Spaghetti', 10000000, 0);
insert into Impiegati (matricola, cognome, stipendio) values(1, 'Spaghetti', 10000000, 1);
insert into Impiegati (matricola, cognome, stipendio) values(2, 'Pomodori', 20000000, 0);
insert into Impiegati (matricola, cognome, stipendio) values(3, 'Penne', 100000, 1);
insert into Impiegati (matricola, cognome, stipendio) values(4, 'Lasagne', 3000000, 0);
insert into Impiegati (matricola, cognome, stipendio) values(5, 'Canedderli', 4000, 2);
insert into Impiegati (matricola, cognome, stipendio) values(6, 'Strangolapreti', 60000000, 3);
insert into Impiegati (matricola, cognome, stipendio) values(7, 'Polpette', 10000000000, 2);
insert into Impiegati (matricola, cognome, stipendio) values(8, 'Sugo', 100, 3);
insert into Impiegati (matricola, cognome, stipendio) values(9, 'Riso', 7000000, 3);

select * from Impiegati;

/*alter table Impiegati add primary key (matricola);

alter table Impiegati add dipartimento int default null;
alter table Impiegati add constraint foreign key (dipartimento) references Dipartimenti(codice);*/