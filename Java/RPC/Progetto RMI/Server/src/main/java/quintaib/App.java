package quintaib;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class App extends UnicastRemoteObject implements CommunicationProtocol {
    //per ora memorizzo solo il nome degli utenti in un array list e utilizzerò l'index come id utente
    String[] utenti = new String[2];

    //utilizzo sempre un array list come lista candadiati
    //ArrayList<Candidato> candidati = new ArrayList<>();
    Candidato[] candidati;

    //utilizzo un array list per memorizzare i voti
    int[] voti = new int[2];
    Integer votiTotali = 0;

    //memorizzo se gli utenti hanno già votato
    Boolean[] utenteVoto = new Boolean[2];

    //creo degli utenti e dei voti di prova
    public App() throws RemoteException {
        super();

        //aggiungo due utenti
        utenti[0] = "Alberto";
        utenti[1] = "Andrea";

        //aggiungo qualche candidato 
        /*Candidato candidato = new Candidato();
        candidato.nome = "Peppino";
        candidato.cognome = "Ino";
        candidato.id = 3;
        candidati.add(candidato);
        candidato.nome = "Ginetta";
        candidato.cognome = "Etta";
        candidato.id = 4;
        candidati.add(candidato);*/

        candidati = new Candidato[2];
        candidati[0] = new Candidato();
        candidati[0].nome = "Peppino";
        candidati[0].cognome = "Ino";
        candidati[0].id = 0;
        candidati[1] = new Candidato();
        candidati[1].nome = "Ginetta";
        candidati[1].cognome = "Etta";
        candidati[1].id = 1;

        //imposto due voti per due candidati
        voti[0] = 0;
        voti[1] = 0;
        

        //imposto che gli utenti non hanno votato
        utenteVoto[0] = false;
        utenteVoto[1] = false;
    }

    public static void main( String[] args ) {
        try {
            App app = new App();

            // Bind the remote object's stub in the registry
            /*Registry registry = LocateRegistry.createRegistry(1234);
            registry.bind("sistema-votazioni", app);*/

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

            //ricordo che l'utente ha già votato
            //utenteVoto[nUtente] = true;
        } //altrimenti per ora non succede niente
    }

    @Override
    public Quota[] classifica() throws Exception {
        System.out.println("Richiesta classifica");
        Quota[] classifica = new Quota[candidati.length];
        for(int i = 0; i < candidati.length; i++) {
            System.out.println("Richiesta quota candidato " + i);
            if(i < 0 || i >= voti.length) return(null);
            Quota quota = new Quota();
            quota.candidato = candidati[i];
            quota.votiCandidato = voti[i];
            quota.votiTotali = votiTotali;
            classifica[i] = quota;
        }
        return(classifica);
    }

    @Override
    public Candidato[] ottieniElencoCandidati() throws Exception {
        System.out.println("Richiesta elenco candidati");
        return(candidati);
    }

    @Override
    public int ottieniIdUtente(String nome, String cognome) throws Exception {
        System.out.println("Richiesta id utente " + nome + " " + cognome);
        //cerco l'utente nella lista, se non lo trovo c'è un errore !
        for(int i = 0; i < utenti.length; i++) {
            if(utenti[i].equals(nome)) return(i);
        }
        return(-1);
    }
}
