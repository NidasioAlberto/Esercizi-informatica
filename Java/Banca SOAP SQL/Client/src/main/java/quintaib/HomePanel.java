package quintaib;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {
    HomeActionListener homeActionListener;

    JLabel lable1;
    JTable tabellaContiCorrenti;
    JButton refresh;
    JButton logout;
    JTextField numeroContoCorrenteSelezionato;
    JButton visualizzaOperazioni;

    ContoCorrente[] contiCorrenti;

    public HomePanel(HomeActionListener homeActionListener1) {
        homeActionListener = homeActionListener1;

        lable1 = new JLabel("Conti correnti");

        refresh = new JButton("Refresh");
        logout = new JButton("Logout");
        
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //recupero i conti correnti
                homeActionListener.refresh();
            }
        });
        
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //eseguo il logout
                homeActionListener.logout();
            }
        });
        
        numeroContoCorrenteSelezionato = new JTextField();
        visualizzaOperazioni = new JButton("Operazioni");

        visualizzaOperazioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //recupero il conto corrente selezionato
                String selezione = numeroContoCorrenteSelezionato.getText();

                //controllo il valore
                try {
                    int idContoCorrente = Integer.parseInt(selezione);
                } catch(Exception ee) {
                    ee.printStackTrace();
                    mostraAvviso("Inserisci un id conto corrente valido");
                }
            }
        });
        
        //inizializzo la tabella
        tabellaContiCorrenti = new JTable();
        
        //preparo la struttura
        DefaultTableModel tableModel = (DefaultTableModel) tabellaContiCorrenti.getModel();
        String[] nomiColonne = {"Id conto", "Id utente", "bilancio", "descrizione"};
        tableModel.setColumnIdentifiers(nomiColonne);

        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.CENTER)
            .addComponent(lable1)
            .addComponent(tabellaContiCorrenti)
            .addGroup(layout.createSequentialGroup()
                .addComponent(numeroContoCorrenteSelezionato)
                .addComponent(visualizzaOperazioni))
            .addGroup(layout.createSequentialGroup()
                .addComponent(refresh)
                .addComponent(logout)));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(lable1)
            .addComponent(tabellaContiCorrenti)
            .addGroup(layout.createParallelGroup()
                .addComponent(numeroContoCorrenteSelezionato)
                .addComponent(visualizzaOperazioni))
            .addGroup(layout.createParallelGroup()
                .addComponent(refresh)
                .addComponent(logout)));

        this.setLayout(layout);
    }

    public void mostraAvviso(String messaggio) {
        JOptionPane.showMessageDialog(getParent(), messaggio, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public void aggiornaTabella(ContoCorrente[] contiCorrenti) {
        if(contiCorrenti != null) {
            this.contiCorrenti = contiCorrenti;

            DefaultTableModel tableModel = (DefaultTableModel) tabellaContiCorrenti.getModel();
            tableModel.setRowCount(0);

            for(int i = 0; i < contiCorrenti.length; i++) {
                String[] data = new String[4];

                System.out.println(contiCorrenti[i].toString());

                data[0] = Integer.toString(contiCorrenti[i].idContoCorrente);
                data[1] = Integer.toString(contiCorrenti[i].idUtente);
                data[2] = Float.toString(contiCorrenti[i].bilancio);
                data[3] = contiCorrenti[i].descrizione;

                tableModel.addRow(data);
            }

            tabellaContiCorrenti.setModel(tableModel);
            tableModel.fireTableDataChanged();
            tabellaContiCorrenti.repaint();
        }
    }
}