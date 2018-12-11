Tris 
comunicazione Client-server.
Creare il gioco del tris in modo da attuare la comunicazione Client-Server.
Lo scopo del gioco è quello di mettere o in orizzontale o in verticale o in diagonale una serie di tre simboli uguali(o le X o le O).
Client si collega al server trammite la porta 2000. 
I messaggi si basano sul protocollo di trasporto tcp.
Il client mandando la scelta della posizione, inizia a giocare con il server.
Una volta che il server legge il messaggio, risponde segnando una x sulla casella da lui scelta.
Il client, ricevuto il messaggio sceglie la casella successiva.
Il simbolo del client è il cerchio.
il gioco termina quando:
  il server o il client termina la comunicazione;
  Uno dei due vince;
  in caso di pareggio;
  
client:
 addentry,1,2 dove il due sta ad indicare la posizione delle colonne, mentre il 3 delle righe.

server
riceva la posizione del server e ne manda un'altra
addentry,2,2

client
addentry....
