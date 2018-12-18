package quintaib;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Questa è l'interfaccia grafica per la pagina di login
 */
public class LoginPanel extends JPanel {

    //elementi grafici
    JTextField id;
    JTextField nome;
    JTextField cognome;
    JTextField password;
    JButton login;
    JButton registrati;

    //callback per il login
    LoginActionListener loginActionListener;

    //modalità
    int modalità = 1; //1 per login, 2 per registrazione

    public LoginPanel(LoginActionListener loginActionListener2) {
        super();

        loginActionListener = loginActionListener2;

        //inizializzo i componenti della pagina
        id = new JTextField("Id");
        password = new JTextField("Password");
        login = new JButton("Login");
        registrati = new JButton("Registrati");
        
        nome = new JTextField("Nome");
        nome.setVisible(false);
        cognome = new JTextField("Cognome");
        cognome.setVisible(false);

        //imposto l'azione del bottone login
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                switch(modalità) {
                    case 1:
                        //provo a ottenere l'id
                        try {
                            int idUtente = Integer.parseInt(id.getText());

                            //controllo i parametri
                            if(idUtente >= 0 && !password.getText().isEmpty()) {
                                //eseguo il callback
                                loginActionListener.onLoginEvent(idUtente, password.getText());
                            } else {
                                //altrimenti mostro un avviso
                                mostraAvviso();
                            }
                        } catch(Exception e) {
                            //altrimenti mostro un avviso
                            mostraAvviso();
                        }
                        break;
                    case 2:
                        nascondiRegistrazione();
                        modalità = 1;
                        break;
                }
            }
        });

        //imposto l'azione del bottone registrati
        registrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                switch(modalità) {
                    case 1:
                        mostraRegistrazione();
                        modalità = 2;
                        break;
                    case 2:
                        //valido i dati
                        if(!nome.getText().isEmpty() && !cognome.getText().isEmpty() && !password.getText().isEmpty()) {
                            //eseguo il callback
                            loginActionListener.onSigninEvent(nome.getText(), cognome.getText(), password.getText());
                        } else {
                            //altrimenti mostro un messaggio di errore
                            mostraAvviso();
                        }
                        break;
                }
            }
        });;

        //imposto l'interfaccia
        GroupLayout layout = new GroupLayout(this);
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(id)
            .addComponent(nome)
            .addComponent(cognome)
            .addComponent(password)
            .addComponent(login)
            .addComponent(registrati));
        layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(id)
            .addComponent(nome)
            .addComponent(cognome)
            .addComponent(password)
            .addComponent(login)
            .addComponent(registrati));

        this.setLayout(layout);
    }

    //permetto di mostrare i campi nome e cognome se l'utente vuole registrarsi
    public void mostraRegistrazione() {
        nome.setVisible(true);
        cognome.setVisible(true);
        id.setVisible(false);

        this.revalidate();
        this.repaint();

        loginActionListener.refresh();
    }

    //permetto di nascondere i campi nome e cognome se l'utente vuole registrarsi
    public void nascondiRegistrazione() {
        nome.setVisible(false);
        cognome.setVisible(false);
        id.setVisible(true);

        this.revalidate();
        this.repaint();

        loginActionListener.refresh();
    }

    public void mostraAvviso() {
        JOptionPane.showMessageDialog(getParent(), "Inserisci correttamente le credenziali !", "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public void mostraAvviso(String messaggio) {
        JOptionPane.showMessageDialog(getParent(), messaggio, "Warning", JOptionPane.WARNING_MESSAGE);
    }
}