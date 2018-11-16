Risassunto del progetto

Bisogna creare un programma per gestire delle elezioni.
Sarà necessaria una parte client con interfaccia grafica
che mostri la lista di opzioni da votare e un programma server
che ha in memoria i dati e con il quale il client si interfaccia.

# Analisi:

## Server
Il server offrirà i servizi necessari all'utente per votare (vedi in dettaglio i metodi forniti sotto).

## Client
Il cliente chiederà di specificare nome e cognome per identificare l'utente e mostrerà la lista di candidati. L'utente potrà poi selezionare un candidato e somministrare il voto, non sarà possibile annullarlo ne modificarlo (per motivi di test un utente potrà somministrare più voti). Se l'utente ha già votato verrà mostrato un messaggio di errore. Si potrà inoltre visulalizzare il vincitore tramite un bottone.

# Metodi da implementare:

## void vota(int nCandidato, int nUtente) throws Exception
Questo metodo permette al client di votare un candidato. I dati da fornire sono il numero del candidato (ho preferito mettere un numero per semplificare il sistema di conteggio, questo potrebbe essere utile per implementare un databsse sql dove i candidati hanno un id univoco) e un numero che identifica l'utente che sta votando (stesso discorso). Può esserci un'eccezione se il candidato o l'utente non esistono e se l'utente ha già votato.

## Quota[] classifica() throws Exception
Questo metodo permette di ottenere la classificsa di tutti i candidati. L'oggetto restituito dalla funizone è un array della classe Quota (vedi sotto).

## Candidato[] ottieniElencoCandidati()
Permette di ottenere tutti i candidati che è possibile votare. I dati restituiti sono un array di oggetti Candidato (vedi sotto).

## int ottieniIdUtente(String nome, String cognome)
Permette di ottenere l'id utente da utilizzare nel metodo vota. Se l'utente non viene ricpnosciuto si ritorna -1. Per semplificare il programma in caso l'utente non sia presente verrà creato e aggiunto nella lista di utenti e non è previsto un sistema di accesso tramite password, sarà un miglioramento futuro.

# Classi Quota e Candidato:

La classe Candidato contiene i dati di un singolo candidato, per ora sono nome, cognome e id (è molto semplice in questo modo aggiungerne nuovi attributi).

La classe Quota invece contiene il candidato interessato, il numero dei suoi voti e il numero di voti totali.

# Miglioramenti futuri

1. Funzione di backup su file per la lista di utenti, dei candidati e dello stato delle votazioni.
2. L'utente ha la possibilità di visulizzare il voto che ha già somministrato nel caso lo abbia già fatto.
3. Sistema di autenticazio e con password.
4. Visualizzare lo stato delle votazioni con il numero di voti per ciascun candidato.
