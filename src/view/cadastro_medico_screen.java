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
import java.util.Hashtable;


public class cadastro_medico_screen extends Agent {
    ContainerController Container;
    AgentController Agent = null;
    private JPanel panel;
    private JComboBox especialidade_combo_box;
    private JTextField preco_text_box;
    private JButton button_add;
    private JTextField nome_medico_text_box;
    private JFrame tela;

    public cadastro_medico_screen(ContainerController c) {
        Container = c;
        tela = new JFrame("Atendimento médico");
        tela.setLocation(450, 250);
        tela.setContentPane(panel);
        tela.pack();
        tela.setVisible(true);
        panel = new JPanel();

        button_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nome_medico_text_box.getText().trim();
                    System.out.println(nome);

                    String especialidade = especialidade_combo_box.getSelectedItem().toString();
                    Integer preco = Integer.parseInt(preco_text_box.getText().trim());
                    Hashtable h = new Hashtable<String ,Integer>();

                    System.out.println(c);
                    h.put(especialidade, preco);

                    Object arrList[] = new Object[1];
                    arrList[0] = h;
                    Agent = Container.createNewAgent(nome, "agents.AgenteMedico", arrList);
                    Agent.start();
                    tela.dispose();
                } catch (Exception er) {
                    System.out.println("Valor invalido cadastro medico screen");
                }
            }
        });
        tela.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                tela.dispose();
            }
        });
    }

}
