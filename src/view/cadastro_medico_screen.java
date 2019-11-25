package view;

import agents.AgenteMedico;
import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class cadastro_medico_screen extends Agent {
    ContainerController Container;
    private AgenteMedico myAgente;
    private JPanel panel;
    private JComboBox especialidade_combo_box;
    private JTextField preco_text_box;
    private JButton button_add;
    private JTextField nome_medico_text_box;
    private JFrame tela;

    public cadastro_medico_screen(ContainerController c) {
        Container = c;
        //super(a.getLocalName());
        tela = new JFrame("Atendimento médico");
        tela.setLocation(450, 250);
        tela.setContentPane(panel);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.pack();
        tela.setVisible(true);
        tela.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                myAgente.doDelete();
            }
        });
        panel = new JPanel();

        button_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nome_medico_text_box.getText().trim();
                    System.out.println(nome);

                    String especialidade = especialidade_combo_box.getSelectedItem().toString();
                    String preco = preco_text_box.getText().trim();

                    Object arrList[] = new Object[3];
                    arrList[0] = especialidade;
                    arrList[1] = preco;


                    AgentController Agent = null;
                    Agent = Container.createNewAgent(nome, "agents.AgenteMedico", arrList);
                    Agent.start();
                } catch (Exception er) {
                    System.out.println("Valor invalido cadastro medico screen");
                }
            }
        });
    }

}
