package quintaib;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Questa è l'interfaccia grafica per votare
 */
public class VotePanel extends JPanel {

    //dati
    Candidato[] listaCandidati;

    //elementi grafici
    JComboBox candidati;
    JButton vota;
    JButton vincitore;

    //callback per la votazione
    VoteActionListener voteActionListener;

    public VotePanel(Candidato[] listaCandidati2, VoteActionListener voteActionListener2) {
        super();

        voteActionListener = voteActionListener2;
        listaCandidati = listaCandidati2;

        //preparo i dati per la combobox
        ArrayList<String> opzioni = new ArrayList<>();
        if(listaCandidati != null) for(int i = 0; i < listaCandidati.length; i++) {
            opzioni.add(listaCandidati[i].nome + " " + listaCandidati[i].cognome);
        }

        //inizializzo l'interfaccia
        candidati = new JComboBox(opzioni.toArray());
        if(!opzioni.isEmpty()) candidati.setSelectedIndex(0);
        vota = new JButton("Vota");
        vincitore = new JButton("Vincitore");

        //imposto l'azione del bottone vota
        vota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //eseguo il callback
                voteActionListener.onVoteAction(listaCandidati[candidati.getSelectedIndex()].id);
            }
        });

        vincitore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //eseguo il callback
                voteActionListener.mostraVincitore();
            }
        });

        //imposto l'interfaccia
        GroupLayout layout = new GroupLayout(this);
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(candidati)
            .addComponent(vota)
            .addComponent(vincitore));
        layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(candidati)
            .addComponent(vota)
            .addComponent(vincitore));

        this.setLayout(layout);
    }
}