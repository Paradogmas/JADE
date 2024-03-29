package view;

import agents.*;
import jade.wrapper.ContainerController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class main_screen {
    private JButton cadastrar_medico_button;
    private JButton procurar_consulta;
    private JLabel title_text;
    private JPanel main_panel;
    private JFrame tela;
    public ContainerController Container;

    public main_screen(ContainerController c) {
        Container = c;
        tela = new JFrame("Atendimento médico");
        tela.setLocation(450, 250);
        tela.setContentPane(main_panel);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.pack();
        tela.setVisible(true);
        main_panel = new JPanel();
        //button_add = new JButton();
        cadastrar_medico_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastro_medico_screen screen = new cadastro_medico_screen(Container);
            }
        });
        procurar_consulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procurar_consulta_screen screen = new procurar_consulta_screen(Container);
            }
        });
    }
}
