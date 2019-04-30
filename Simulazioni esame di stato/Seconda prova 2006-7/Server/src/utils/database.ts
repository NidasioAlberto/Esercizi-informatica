import { Connection, createConnection, ConnectionConfig } from 'mysql'
import { Reparto, Libro } from './tipi';

export class Database {
    connection: Connection

    constructor(configurazione?: ConnectionConfig) {
        try {
            if(configurazione != undefined) this.connetti(configurazione)
        } catch(e) {
            this.connection = undefined
        }
    }

    /**
     * Apre la connessione con il database grazie ai parametri forniti
     * @param configurazione parametri per la connessione
     */
    connetti(configurazione: ConnectionConfig) {
        this.connection = createConnection(configurazione)

        return new Promise<any>((resolve, reject) => {
            this.connection.connect(err => {
                if(err) return reject(err);
                resolve();
            })
        })
    }

    /**
     * Esegue la query con i parametri specificati
     * @param sql query da eseguire
     * @param args parametri della query
     */
    query(sql: string, args?: any) {
        return new Promise<any>((resolve, reject) => {
            this.connection.query(sql, args, (err, rows) => {
                if(err) return reject(err);
                resolve(rows);
            });
        });
    }

    /**
     * Conclude la connessione con il database
     */
    chiudi() {
        return new Promise((resolve, reject) => {
            this.connection.end(err => {
                if(err) return reject(err);
                resolve();
            });
        });
    }

    /**
     * Permette di ottenere la lista dei repari
     */
    async ottieniReparti(): Promise<Reparto[]> {
        try {
            let query = 'SELECT * FROM reparti;'

            let result: Reparto[] = await this.query(query)

            for(let reparto in result) {
                result[reparto].numeroLibri = await this.ottieniNumeroLibriInReparo(result[reparto].id)
            }

            return result
        } catch(error) {
            throw Error('Imposssibile recuperare i dati dei repari')
        }
    }

    /**
     * Permette di ottenere il numero di libri in tutta la libreiria o in un solo reparto
     * @param idReparto id reparto da filtrare
     */
    async ottieniNumeroLibriInReparo(idReparto: number): Promise<number> {
        try {
            let query = 'SELECT count(id) numeroLibri FROM libri WHERE idReparto = ' + idReparto + ';'

            let result: number = await this.query(query)

            return result[0]['numeroLibri']
        } catch(error) {
            throw Error('Imposssibile recuperare il numero di libri nel reparto specificato')
        }
    }

    /**
     * Permette di ottenere tutti i libri
     */
    async ottieniLibri(idReparto: number): Promise<Libro[]> {
        try {
            let query = 'SELECT * FROM libri'
            if(idReparto != undefined) query += ' WHERE idReparto = ' + idReparto
            query += ';'

            let result: Libro[] = await this.query(query)
            return result
        } catch(error) {
            throw Error('Imposssibile recuperare i dati dei libri')
        }
    }

    /**
     * Permette di ottenere le statistiche di tutta la libreria o di un solo reparto
     * @param idReparto id repaerto da filtrare
     */
    async ottieniStatistiche(idReparto: number) {
        try {
            let query = 'SELECT count(libri.id) numeroLibri, libri.idCategoria, categorie.nome FROM libri JOIN categorie ON libri.idCategoria = categorie.id'
            if(idReparto != undefined) query += ' WHERE idReparto = ' + idReparto
            query += ' GROUP BY categorie.id;'

            return await this.query(query)
        } catch(error) {
            throw Error('Imposssibile recuperare i dati dei libri')
        }
    }

    /**
     * Permette di ottenere la lista di tutti gli ordini
     */
    async ottieniOrdini(): Promise<any> {
        try {
            let query = 'SELECT * FROM acquisti;'

            return await this.query(query)
        } catch(error) {
            throw Error('Imposssibile recuperare i dati degli ordini')
        }
    }
}