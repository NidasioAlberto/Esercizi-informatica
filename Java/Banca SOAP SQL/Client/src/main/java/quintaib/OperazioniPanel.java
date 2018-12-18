package quintaib;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperazioniPanel extends JPanel {
    JLabel lable1;
    JTable tabellaOperazioni;
    JButton refresh;
    JButton back;

    OperazioniListener operazioniListener;

    Operazione[] operazioni;

    public OperazioniPanel(OperazioniListener operazioniListener1) {
        operazioniListener = operazioniListener1;

        lable1 = new JLabel("Operazioni");

        refresh = new JButton("Refresh");
        back = new JButton("Back");

        refresh.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                operazioniListener.refresh();
            }
        });

        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                operazioniListener.back();
            }
        });

        //inizializzo la tabella
        tabellaOperazioni = new JTable();
        
        //preparo la struttura
        DefaultTableModel tableModel = (DefaultTableModel) tabellaOperazioni.getModel();
        String[] nomiColonne = {"Data", "Importo", "causale", "corretta", "Tipo operazione"};
        tableModel.setColumnIdentifiers(nomiColonne);

        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.CENTER)
            .addComponent(lable1)
            .addComponent(tabellaOperazioni)
            .addGroup(layout.createSequentialGroup()
                .addComponent(refresh)
                .addComponent(back)));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(lable1)
            .addComponent(tabellaOperazioni)
            .addGroup(layout.createParallelGroup()
                .addComponent(refresh)
                .addComponent(back)));

        this.setLayout(layout);
    }

    public void mostraAvviso(String messaggio) {
        JOptionPane.showMessageDialog(getParent(), messaggio, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public void aggiornaTabella(Operazione[] operazioni) {
        if(operazioni != null) {
            this.operazioni = operazioni;

            DefaultTableModel tableModel = (DefaultTableModel) tabellaOperazioni.getModel();
            tableModel.setRowCount(0);

            for(int i = 0; i < operazioni.length; i++) {
                String[] data = new String[4];

                System.out.println(operazioni[i].toString());

                data[0] = operazioni[i].data;
                data[1] = Float.toString(operazioni[i].importo);
                data[2] = operazioni[i].causale;
                data[3] = Boolean.toString(operazioni[i].corretta);
                data[3] = operazioni[i].tipoOperazione;

                tableModel.addRow(data);
            }

            tabellaOperazioni.setModel(tableModel);
            tableModel.fireTableDataChanged();
            tabellaOperazioni.repaint();
        }
    }
}