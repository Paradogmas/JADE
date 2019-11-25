package view;

import agents.AgenteMedico;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class cadastro_medico_screen {
    private AgenteMedico myAgente;
    private JPanel panel;
    private JTextField especialidade;
    private JTextField preco;
    private JLabel titulo_especialidade;
    private JLabel titulo_preco;
    private JButton button_add;
    private JFrame tela;

    public cadastro_medico_screen(AgenteMedico a){
        //super(a.getLocalName());
        tela = new JFrame("Atendimento médico");
        tela.setLocation(450, 250);
        tela.setContentPane(panel);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.pack();
        tela.setVisible(true);
        tela.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                myAgente.doDelete();
            }
        });
        myAgente = a;
        panel = new JPanel();
        //button_add = new JButton();
        button_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    AgenteMedico medico;
                    medico = new AgenteMedico();
                    String esp = especialidade.getText().trim();
                    System.out.println(esp);
                    String price = preco.getText().trim();
                    myAgente.AtualizarCatalogo(esp, Integer.parseInt(price));
                    preco.setText("");
                    especialidade.setText("");
                    medico.CreateMedico("Nome");
                }
                catch (Exception er){
                    System.out.println("Valor invalido");
                }
            }
        });
    }


}
