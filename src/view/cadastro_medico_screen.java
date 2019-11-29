package view;

import agents.AgenteMedico;
import jade.core.Agent;
import jade.core.MicroRuntime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import jade.wrapper.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

class ClientContainer {
    private static ContainerController client;
    private ClientContainer(){
        Profile pClient=new ProfileImpl();
        pClient.setParameter(Profile.CONTAINER_NAME, "Clients");
        jade.core.Runtime rtClient=jade.core.Runtime.instance();
        client=rtClient.createAgentContainer(pClient);
    }
    public static ContainerController getClient() {
        if(client==null){
            ClientContainer cc=new ClientContainer();
        }
        return client;
    }
    public static AgentContainer getAgentCont() {
        if(client==null){
            ClientContainer cc2=new ClientContainer();
            return ( AgentContainer)client;
        }
        else
            return (AgentContainer) client;
    }
}

public class cadastro_medico_screen extends Agent {
    ContainerController Container;
    ContainerController client;
    private AgenteMedico myAgente;
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
                    Integer preco = Integer.parseInt(preco_text_box.getText().trim());
                    Hashtable h = new Hashtable<String, Integer>();

                    System.out.println(c);
                    h.put(especialidade, preco);

                    Object arrList[] = new Object[1];
                    arrList[0] = h;

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
                            MicroRuntime.startAgent(nome, "agents.AgenteMedico", arrList);
                        } catch (Exception er) {
                            er.printStackTrace();
                        }
                    }


                    /*
                    AgentController Agent = null;
                    Agent = Container.createNewAgent(nome, "agents.AgenteMedico", arrList);
                    Agent.start();*/
                } catch (Exception er) {
                    System.out.println("Valor invalido cadastro medico screen");
                }
            }
        });
    }

}
