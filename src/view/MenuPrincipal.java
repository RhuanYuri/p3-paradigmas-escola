package view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Menu Principal");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        // Cabeçalho
        JLabel lblTitulo = new JLabel("Menu Principal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(173, 216, 230));
        lblTitulo.setPreferredSize(new Dimension(400, 50));
        add(lblTitulo);

        // Botões
        JButton btnAluno = new JButton("Cadastrar Aluno");
        btnAluno.addActionListener(e -> {
            TelaAluno telaAluno = new TelaAluno();
            telaAluno.setVisible(true);
        });

        JButton btnTurma = new JButton("Cadastrar Turma");
        btnTurma.addActionListener(e -> {
            TelaTurma telaTurma = new TelaTurma();
            telaTurma.setVisible(true);
        });

        JButton btnProfessor = new JButton("Selecionar Professor");
        btnProfessor.addActionListener(e -> {
            TelaProfessor telaProfessor = new TelaProfessor();
            telaProfessor.setVisible(true);
        });

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> System.exit(0));

        // Adicionando botões ao frame
        add(btnAluno);
        add(btnTurma);
        add(btnProfessor);
        add(btnSair);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal());
    }
}
