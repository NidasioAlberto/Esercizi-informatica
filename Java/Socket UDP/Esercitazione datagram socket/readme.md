## Esercitazione di base: socket Java di tipo datagram (UDP)
È dato il codice allegato di un’applicazione C/S in cui:
* i client inviano al server pacchetti (vuoti) che vengono interpretati dal server come richiesta
della linea corrente di un certo file di testo;
* il server invia a ciascun client un pacchetto contenente una linea (le linee vengono estratte in
modo sequenziale) del file; se non riesce invia data e ora attuali
1. Si chiede di studiare e provare il codice.
2. Modificare il programma in modo che i pacchetti inviati dai client non siano vuoti ma
contengano informazioni, sotto forma di stringa, che il server deve estrarre per svolgere il servizio,
per esempio:
a) numero di righe da estrarre dal file
b) nome del file da cui estrarre la riga, supponendo di averne a disposizione più d’uno; in questo
caso l’apertura del file e l’associazione allo stesso dello stream di lettura deve essere fatto
soltanto dopo la ricezione della richiesta dal client
c) indirizzo del destinatario a cui deve essere inviata la linea (o le linee); diversamente dal caso
delle socket stream (TCP), non è necessario che il destinatario sia connesso o in grado di
accettare una connessione, bastano indirizzo e porta, che anziché essere estratti dai rispettivi
campi del pacchetto dovranno essere ricavati dal messaggio, scritto secondo determinate
convenzioni (es. “host porta numLinee”, ogni informazione separata da uno spazio) 