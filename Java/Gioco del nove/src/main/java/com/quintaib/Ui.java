package com.quintaib;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;;

class Ui extends JFrame {
    JButton[] bottoni;
    Integer[] posizioni = new Integer[9];

    //struttura delle celle
    //0 1 2
    //3 4 5
    //6 7 8

    public Ui() {
        bottoni = new JButton[9];

        for(int i = 0; i < 9; i++) {
            bottoni[i] = new JButton();
            bottoni[i].setMinimumSize(new Dimension(100, 100));
        };

        JPanel panel = new JPanel();

        GroupLayout layout = new GroupLayout(panel);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup()
                .addComponent(bottoni[0])
                .addComponent(bottoni[3])
                .addComponent(bottoni[6]))
            .addGroup(layout.createParallelGroup()
                .addComponent(bottoni[1])
                .addComponent(bottoni[4])
                .addComponent(bottoni[7]))
            .addGroup(layout.createParallelGroup()
                .addComponent(bottoni[2])
                .addComponent(bottoni[5])
                .addComponent(bottoni[8])));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup()
                .addComponent(bottoni[0])
                .addComponent(bottoni[1])
                .addComponent(bottoni[2]))
            .addGroup(layout.createParallelGroup()
                .addComponent(bottoni[3])
                .addComponent(bottoni[4])
                .addComponent(bottoni[5]))
            .addGroup(layout.createParallelGroup()
                .addComponent(bottoni[6])
                .addComponent(bottoni[7])
                .addComponent(bottoni[8])));
        panel.setLayout(layout);

        this.add(panel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);

        //genero le posizioni
        generaPosizioni();

        //imposto i callback per i bottoni
        for(int i = 0; i < 9; i++) {
            final int tmp = i;
            bottoni[i].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    gestisciBottone(tmp);
                }
            });;
        }

        //imposto i valori nei bottoni
        aggiornaTestiBottoni();
    }

    void generaPosizioni() {
        posizioni[0] = 1;
        posizioni[1] = 2;
        posizioni[2] = 3;
        posizioni[3] = 4;
        posizioni[4] = 5;
        posizioni[5] = 6;
        posizioni[6] = 7;
        posizioni[7] = -1;
        posizioni[8] = 8;
    }

    void gestisciBottone(int posizione) {
        //controllo se nelle vicinanze c'è la cella vuota in cui spostare il numero selezionato altrimenti non possiamo fare niente
        boolean mossaValida = false;

        if(posizione == 0) {
            //controllo la cella a destra e quella sotto
            mossaValida = posizioni[1] == -1 || posizioni[3] == -1;
        } else if(posizione == 1) {
            //controllo nelle cella a fianco e in quella sotto
            mossaValida = posizioni[0] == -1 || posizioni[2] == -1 || posizioni[4] == -1;
        } else if(posizione == 2) {
            //controllo nelle cella a fianco e in quella sotto
            mossaValida = posizioni[1] == -1 || posizioni[5] == -1;
        } else if(posizione == 3) {
            //controllo nelle cella a fianco e in quella sotto
            mossaValida = posizioni[0] == -1 || posizioni[4] == -1 || posizioni[6] == -1;
        } else if(posizione == 4) {
            //controllo nelle cella a fianco e in quella sotto
            mossaValida = posizioni[1] == -1 || posizioni[3] == -1 || posizioni[5] == -1 || posizioni[7] == -1;
        } else if(posizione == 5) {
            //controllo nelle cella a fianco e in quella sotto
            mossaValida = posizioni[2] == -1 || posizioni[4] == -1 || posizioni[8] == -1;
        } else if(posizione == 6) {
            //controllo nelle cella a fianco e in quella sotto
            mossaValida = posizioni[3] == -1 || posizioni[7] == -1;
        } else if(posizione == 7) {
            //controllo nelle cella a fianco e in quella sotto
            mossaValida = posizioni[4] == -1 || posizioni[6] == -1 || posizioni[8] == -1;
        } else if(posizione == 8) {
            //controllo nelle cella a fianco e in quella sotto
            mossaValida = posizioni[5] == -1 || posizioni[7] == -1;
        }

        if(mossaValida) System.out.println("mossa valida");
        else System.out.println("mossa non valida");

        //se la mossa è valida scambio le posizioni
        if(mossaValida) {
            if(posizione == 0) {
                if(posizioni[1] == -1) {
                    //scambio 0 con 1
                    posizioni[1] = posizioni[0];
                } else if(posizioni[3] == -1) {
                    //scambio 0 con 3
                    posizioni[3] = posizioni[0];
                }
                posizioni[0] = -1;
            } else if(posizione == 1) {
                if(posizioni[0] == -1) {
                    //scambio 1 con 0
                    posizioni[0] = posizioni[1];
                } else if(posizioni[2] == -1) {
                    //scambio 1 con 2
                    posizioni[2] = posizioni[1];
                } else if(posizioni[4] == -1) {
                    //scambio 1 con 4
                    posizioni[4] = posizioni[1];
                }
                posizioni[1] = -1;
            } else if(posizione == 2) {
                if(posizioni[1] == -1) {
                    //scambio 2 con 1
                    posizioni[1] = posizioni[2];
                } else if(posizioni[5] == -1) {
                    //scambio 1 con 5
                    posizioni[5] = posizioni[2];
                }
                posizioni[2] = -1;
            } else if(posizione == 3) {
                if(posizioni[0] == -1) {
                    //scambio 3 con 0
                    posizioni[0] = posizioni[3];
                } else if(posizioni[4] == -1) {
                    //scambio 3 con 4
                    posizioni[4] = posizioni[3];
                } else if(posizioni[6] == -1) {
                    //scambio 3 con 6
                    posizioni[6] = posizioni[3];
                }
                posizioni[3] = -1;
            } else if(posizione == 4) {
                if(posizioni[1] == -1) {
                    //scambio 4 con 1
                    posizioni[1] = posizioni[4];
                } else if(posizioni[3] == -1) {
                    //scambio 4 con 3
                    posizioni[3] = posizioni[4];
                } else if(posizioni[5] == -1) {
                    //scambio 4 con 5
                    posizioni[5] = posizioni[4];
                } else if(posizioni[7] == -1) {
                    //scambio 4 con 7
                    posizioni[7] = posizioni[4];
                }
                posizioni[4] = -1;
            } else if(posizione == 5) {
                if(posizioni[2] == -1) {
                    //scambio 5 con 2
                    posizioni[2] = posizioni[5];
                } else if(posizioni[4] == -1) {
                    //scambio 5 con 4
                    posizioni[4] = posizioni[5];
                } else if(posizioni[8] == -1) {
                    //scambio 5 con 8
                    posizioni[8] = posizioni[5];
                }
                posizioni[5] = -1;
            } else if(posizione == 6) {
                if(posizioni[3] == -1) {
                    //scambio 6 con 3
                    posizioni[3] = posizioni[6];
                } else if(posizioni[7] == -1) {
                    //scambio 6 con 7
                    posizioni[7] = posizioni[6];
                }
                posizioni[6] = -1;
            } else if(posizione == 7) {
                if(posizioni[4] == -1) {
                    //scambio 7 con 4
                    posizioni[4] = posizioni[7];
                } else if(posizioni[6] == -1) {
                    //scambio 7 con 6
                    posizioni[6] = posizioni[7];
                } else if(posizioni[8] == -1) {
                    //scambio 7 con 8
                    posizioni[8] = posizioni[7];
                }
                posizioni[7] = -1;
            } else if(posizione == 8) {
                if(posizioni[5] == -1) {
                    //scambio 5 con 5
                    posizioni[5] = posizioni[8];
                } else if(posizioni[7] == -1) {
                    //scambio 7 con 4
                    posizioni[7] = posizioni[8];
                }
                posizioni[8] = -1;
            }
        }

        //aggiorno il testo dei bottoni
        aggiornaTestiBottoni();

        //controlliamo se la tabella è allineata
        if(controllaAllineamento()) {
            System.out.println("Hai finito il gioco !");
            JOptionPane.showConfirmDialog(this, "Hai finito il gioco !");
        }
    }

    void aggiornaTestiBottoni() {
        for(int i = 0; i < 9; i++) {
            if(posizioni[i] != -1) bottoni[i].setText(posizioni[i].toString());
            else bottoni[i].setText("");
        }
    }

    boolean controllaAllineamento() {
        return(posizioni[0] == 1 &&
            posizioni[1] == 2 &&
            posizioni[2] == 3 &&
            posizioni[3] == 4 &&
            posizioni[4] == 5 &&
            posizioni[5] == 6 &&
            posizioni[6] == 7 &&
            posizioni[7] == 8 &&
            posizioni[8] == -1);
    }
}