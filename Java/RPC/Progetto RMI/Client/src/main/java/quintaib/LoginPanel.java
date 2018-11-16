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
    JTextField nome;
    JTextField cognome;
    JButton login;

    //callback per il login
    LoginActionListener loginActionListener;

    public LoginPanel(LoginActionListener loginActionListener2) {
        super();

        loginActionListener = loginActionListener2;

        //inizializzo i componenti della pagina
        nome = new JTextField();
        cognome = new JTextField();
        login = new JButton("Login");

        //imposto l'azione del bottone login
        login.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //controllo i parametri
                if(!nome.getText().isBlank() && !cognome.getText().isEmpty()) {
                    //eseguo il callback
                    loginActionListener.onLoginEvent(nome.getText(), cognome.getText());
                } else {
                    //altrimenti mostro un avviso
                    JOptionPane.showMessageDialog(getParent(), "Inserisci correttamente le credenziali !", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        //imposto l'interfaccia
        GroupLayout layout = new GroupLayout(this);
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(nome)
            .addComponent(cognome)
            .addComponent(login));
        layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(nome)
            .addComponent(cognome)
            .addComponent(login));

        this.setLayout(layout);
    }
}