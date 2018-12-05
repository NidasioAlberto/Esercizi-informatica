package quintaib;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    //elementi grafici
    JTable table;

    JLabel labelTitolo;
    JTextField titolo;

    JLabel labelAnno;
    JTextField anno;

    JLabel lableGenere;
    JTextField genere;

    JButton applicaFiltri;
    JButton annullaFiltri;

    FilterActionListener filterActionListener;

    public MainPanel(FilterActionListener filterActionListener2) {
        filterActionListener = filterActionListener2;


        table = new JTable();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        String[] nomiColonne = {"Titolo", "Anno", "Durata", "Nazione", "Genere"};
        tableModel.setColumnIdentifiers(nomiColonne);

        labelTitolo = new JLabel("Titolo");
        titolo = new JTextField();

        labelAnno = new JLabel("Anno");
        anno = new JTextField();

        lableGenere = new JLabel("Genere");
        genere = new JTextField();

        applicaFiltri = new JButton("Applica");
        applicaFiltri.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                filterActionListener.applicaFiltri(titolo.getText(), Integer.parseInt((anno.getText().isEmpty() ? "0" : anno.getText())), genere.getText());
            }
        });

        annullaFiltri = new JButton("Annulla");
        annullaFiltri.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //pulisco tutte le jtextfield
                titolo.setText("");
                anno.setText("");
                genere.setText("");

                filterActionListener.annullaFiltri();
            }
        });

        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.CENTER)
            .addComponent(table)
            .addComponent(labelTitolo)
            .addComponent(titolo)
            .addComponent(labelAnno)
            .addComponent(anno)
            .addComponent(lableGenere)
            .addComponent(genere)
            .addComponent(applicaFiltri)
            .addComponent(annullaFiltri));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(table)
            .addComponent(labelTitolo)
            .addComponent(titolo)
            .addComponent(labelAnno)
            .addComponent(anno)
            .addComponent(lableGenere)
            .addComponent(genere)
            .addComponent(applicaFiltri)
            .addComponent(annullaFiltri));

        this.setLayout(layout);
    }

    public void aggiornaTabella(Film[] film) {
        if(film != null) {
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0);

            //String[] nomiColonne = {"Titolo", "Anno", "Durata", "Nazione", "Genere"};
            //tableModel.setColumnIdentifiers(nomiColonne);

            for(int i = 0; i < film.length; i++) {
                String[] data = new String[5];

                data[0] = film[i].titolo;
                data[1] = Integer.toString(film[i].anno);
                data[2] = Integer.toString(film[i].durata);
                data[3] = film[i].nazione;
                data[4] = film[i].genere;

                tableModel.addRow(data);
            }

            table.setModel(tableModel);
            tableModel.fireTableDataChanged();
            table.repaint();
        }
    }
}