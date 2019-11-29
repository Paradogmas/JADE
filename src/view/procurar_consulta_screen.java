package view;

import agents.AgenteMedico;
import jade.core.Agent;
import jade.core.MicroRuntime;
import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import jade.wrapper.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class procurar_consulta_screen extends Agent {
    ContainerController Container;
    private AgenteMedico myAgente;
    private JPanel panel;
    private JFrame tela;
    private JTextField nome_paciente_text_box;
    private JComboBox especialidade_combo_box;
    private JButton procurar_button;

    public procurar_consulta_screen(ContainerController c) {
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

        procurar_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nome_paciente_text_box.getText().trim();
                    System.out.println(nome);

                    String especialidade = especialidade_combo_box.getSelectedItem().toString();
                    System.out.println(especialidade);
                    Object arrList[] = new Object[2];
                    arrList[0] = especialidade;


                    Properties p = new Properties();
                    p.setProperty(Profile.MAIN_HOST, "192.168.43.101");
                    p.setProperty(Profile.MAIN_PORT, "1099");
                    p.setProperty(Profile.CONTAINER_NAME, "Hospital");

                    MicroRuntime.startJADE(p, null);
                    MicroRuntime.getContainerName();
                    if (!MicroRuntime.isRunning()) {
                        System.out.println("Jade MicroRuntime Start Failed");
                    } else {
                        try {
                            MicroRuntime.startAgent(nome, "agents.AgentePaciente", arrList);
                        } catch (Exception er) {
                            er.printStackTrace();
                        }
                    }
                    /*
                    AgentController Agent = null;
                    Agent = Container.createNewAgent(nome, "agents.AgentePaciente", arrList);
                    Agent.start();*/
                } catch (Exception er) {
                    System.out.println("Valor invalido procurar consulta screen");
                }
            }
        });
    }

}
