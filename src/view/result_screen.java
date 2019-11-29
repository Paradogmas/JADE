package view;

import javax.swing.*;
import java.awt.Dimension;

public class result_screen {
    private JPanel result_frame;
    private JLabel paciente_label;
    private JLabel nome_paciente_label;
    private JLabel result_label;
    private JLabel medico_label;
    private JLabel valor_label;
    private JFrame tela;

    public result_screen(String paciente, Integer preco, String especialidadeMedica, String medico){
        tela = new JFrame("Atendimento médico");
        tela.setLocation(650, 450);
        tela.setContentPane(result_frame);
        tela.pack();
        tela.setVisible(true);
        nome_paciente_label.setText("     "+paciente);
        medico_label.setText("     Medico: " + medico);
        valor_label.setText("     Valor: " + preco);
        result_frame = new JPanel();
        tela.setPreferredSize(new Dimension(640, 480));
    }
}
