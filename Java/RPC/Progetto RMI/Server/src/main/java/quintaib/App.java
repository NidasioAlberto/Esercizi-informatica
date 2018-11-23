package quintaib;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class App extends UnicastRemoteObject implements CommunicationProtocol {
    //per ora memorizzo solo il nome degli utenti in un array e utilizzerò l'index come id utente
    String[] utenti = new String[2];

    //utilizzo sempre un array come lista candidati
    Candidato[] candidati = new Candidato[2];

    //utilizzo un array per memorizzare i voti
    int[] voti = new int[2];

    //conto anche i voti totali così da non diverli calcolare ogni volta che viene chiesta la quota di un candidato
    Integer votiTotali = 0;

    //memorizzo se gli utenti hanno già votato
    Boolean[] utenteVoto = new Boolean[2];

    //creo degli utenti e dei voti di prova
    public App() throws RemoteException {
        super();

        //aggiungo due utenti
        utenti[0] = "Alberto";
        utenti[1] = "Andrea";

        //aggiungo due candidati
        candidati[0] = new Candidato();
        candidati[0].nome = "Peppino";
        candidati[0].cognome = "Ino";
        candidati[0].id = 0;
        candidati[1] = new Candidato();
        candidati[1].nome = "Ginetta";
        candidati[1].cognome = "Etta";
        candidati[1].id = 1;

        //inizializzo i voti dei candidati a 0
        voti[0] = 0;
        voti[1] = 0;
        
        //imposto che gli utenti non hanno votato
        utenteVoto[0] = false;
        utenteVoto[1] = false;
    }

    public static void main( String[] args ) {
        try {
            App app = new App();

            //inizializzo JAVA RMI
            LocateRegistry.createRegistry(1099);
            Naming.rebind("sistema-votazioni", app);
            System.err.println("Server ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void vota(int nCandidato, int nUtente) throws Exception {
        //controllo se l'utente ha già votato
        if(!utenteVoto[nUtente]) {
            System.out.println("Voto candidato " + nCandidato + " da utente " + nUtente);
            voti[nCandidato] = voti[nCandidato] + 1;
            votiTotali++;

            //ricordo che l'utente ha già votato
            //utenteVoto[nUtente] = true;
        } //altrimenti per ora non succede niente
    }

    @Override
    public Quota[] classifica() throws Exception {
        System.out.println("Classifica richiesta");

        //inizializzo un array di quote
        Quota[] classifica = new Quota[candidati.length];

        //itero tutti i candidati
        for(int i = 0; i < candidati.length; i++) {
            //creo una nuova quota
            Quota quota = new Quota();

            //salvo i suoi dati
            quota.candidato = candidati[i];
            quota.votiCandidato = voti[i];
            quota.votiTotali = votiTotali;

            //la inserisco nell'array
            classifica[i] = quota;
        }

        //ritorno la classifica al client
        return(classifica);
    }

    @Override
    public Candidato[] ottieniElencoCandidati() throws Exception {
        System.out.println("Richiesta elenco candidati");

        //invio al client la lista dei candidati
        return(candidati);
    }

    @Override
    public int ottieniIdUtente(String nome, String cognome) throws Exception {
        System.out.println("Richiesta id utente " + nome + " " + cognome);

        //cerco l'utente nella lista, se non lo trovo c'è un errore ! l'utente non potra ottenere il suo id
        for(int i = 0; i < utenti.length; i++) {
            if(utenti[i].equals(nome)) return(i);
        }
        return(-1);
    }
}
