export interface Reparto {
    id: number
    nome: string
    numeroLibri?: number
}

export interface Libro {
    id: number
    titolo: string
    autore: string
    prezzoDiCopertina: string
    sconto: number
    annoDiPubblicazione: number
    editore: string
    collana: string
    immagineDiCopertina: string
    dataDiArchiviazione: string
    pagine: number
    rilegatura: boolean
    disponibilita: boolean
    idCategoria: number
    idReparto: number
}