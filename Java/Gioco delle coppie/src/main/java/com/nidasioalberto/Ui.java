package com.nidasioalberto;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class Ui extends JFrame {

    int[] numeriCarte = new int[3];
    int[] posizioneCarte = new int[6];
    boolean[] bottoniAbilitati = new boolean[6];
    JButton[] bottoniCarte = new JButton[6];

    int punteggio = 100;
    int bottoniVisibili = 0;
    int bottoneVisibile1, bottoneVisibile2;

    public Ui() {
        for(int i = 0; i < 6; i++) bottoniAbilitati[i] = true;
        generaCarteCasuali();

        //creo gli elementi dell'interfaccia
        for(int i = 0; i < 6; i++) bottoniCarte[i] = new JButton();

        //preparo il jPanel
        JPanel panel = new JPanel();
        for(int i = 0; i < 6; i++) panel.add(bottoniCarte[i]);

        //mostro il jFrame
        this.add(panel);
        this.pack();
        this.setVisible(true);

        //assegno i callback ai bottoni
        bottoniCarte[0].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                registraClickBottone(0);
            }
        });
        bottoniCarte[1].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                registraClickBottone(1);
            }
        });
        bottoniCarte[2].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                registraClickBottone(2);
            }
        });
        bottoniCarte[3].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                registraClickBottone(3);
            }
        });
        bottoniCarte[4].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                registraClickBottone(4);
            }
        });
        bottoniCarte[5].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                registraClickBottone(5);
            }
        });
    }

    void registraClickBottone(int nBottone) {
        //controllo se il bottone è abilitato
        if(bottoniAbilitati[nBottone]) {
            //controllo il punteggio
            if(punteggio > 10) {
                //mostro il numero nelm bottone
                //controllo quale bottone è stato cliccato
                if(bottoniVisibili == 0) {
                    bottoneVisibile1 = nBottone;
                    bottoniVisibili++;
                    bottoniCarte[nBottone].setText(String.valueOf(numeriCarte[posizioneCarte[nBottone]]));
                } else if(bottoniVisibili == 1 && nBottone != bottoneVisibile1) {
                    bottoneVisibile2 = nBottone;
                    bottoniVisibili++;
                    bottoniCarte[nBottone].setText(String.valueOf(numeriCarte[posizioneCarte[nBottone]]));
                } else if(bottoniVisibili > 1) {
                    bottoniVisibili = 0;

                    //controllo le due carte
                    if(posizioneCarte[bottoneVisibile1] == posizioneCarte[bottoneVisibile2]) {
                        System.out.println("indovinate");
                        //le carte selezionate sono uguali, mostro i punti e riavvio il gioco
                        //...
                        //imposto i due bottono come non abilitati
                        bottoniAbilitati[bottoneVisibile1] = false;
                        bottoniAbilitati[bottoneVisibile2] = false;
                    } else {
                        System.out.println("sbagliate");
                        //le carte selezionate sono diverse, decremento i punti e continuo il gioco
                        aggiornaPunteggio(-10);
                        //rigiro le due carte selezionate
                        bottoniCarte[bottoneVisibile1].setText("");
                        bottoniCarte[bottoneVisibile2].setText("");
                    }

                    //controllo se ho vinto
                    boolean vinto = true;
                    for(int i = 0; i < 6; i++) if(bottoniAbilitati[i]) {
                        vinto = false;
                        break;
                    }
                    if(vinto) {
                        //mostro un popup con il punteggio
                        JOptionPane.showMessageDialog(this, "Hai vinto con: " + punteggio);

                        //reimposto il gioco
                        reimpostaGioco();
                    }
                }
            } else {
                //l'utente ha finito i tentativi
                JOptionPane.showMessageDialog(this, "Hai finito i tentativi");

                //reimposto il gioco
                reimpostaGioco();
            }
        }
    }

    void aggiornaPunteggio(int punteggioDaAggiungere) {
        punteggio += punteggioDaAggiungere;
    }

    void generaCarteCasuali() {
        //genero i tre numeri per le carte
        for(int i = 0; i < 3; i++) numeriCarte[i] = (new Random()).nextInt(100);

        //genero la posizione delle carte
        for(int i = 0; i < 6; i++) {
            int n = (new Random()).nextInt(6);
            System.out.println("random: " + n);
            if(i == 0) posizioneCarte[i] = n;
            else {
                boolean disponibile = false;
                while(!disponibile) {
                    disponibile = true;
                    for(int j = 0; j < i; j++) {
                        if(posizioneCarte[j] == n) {
                            disponibile = false;
                            break;
                        }
                    }
                    if(!disponibile) {
                        n = (new Random()).nextInt(6);
                    }
                }
                posizioneCarte[i] = n;
            }
        }
        for(int i = 0; i < 6; i++) if(posizioneCarte[i] >= 3) posizioneCarte[i] -= 3;
        for(int i = 0; i < 6; i++) System.out.println(posizioneCarte[i]);
    }

    void reimpostaGioco() {
        //rigiro le carte
        for(int i = 0; i < 6; i++) {
            bottoniCarte[i].setText("");
            bottoniAbilitati[i] = true;
        }
        bottoniVisibili = 0;

        //rigenero le carte
        generaCarteCasuali();
    }
}