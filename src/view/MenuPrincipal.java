package view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Menu Principal");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---------- CABEÇALHO ----------
        JLabel lblTitulo = new JLabel("Menu Principal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(173, 216, 230));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        add(lblTitulo, BorderLayout.NORTH);

        // ---------- BOTÕES ----------
        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 10, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        painelBotoes.setBackground(Color.WHITE);

        JButton btnAluno = new JButton("Cadastrar Aluno");
        JButton btnTurma = new JButton("Cadastrar Turma");
        JButton btnProfessor = new JButton("Selecionar Professor");

        btnAluno.addActionListener(e -> new TelaAluno());
        btnTurma.addActionListener(e -> new TelaTurma());
        btnProfessor.addActionListener(e -> new TelaProfessor());

        painelBotoes.add(btnAluno);
        painelBotoes.add(btnTurma);
        painelBotoes.add(btnProfessor);

        add(painelBotoes, BorderLayout.CENTER);

        // ---------- RODAPÉ ----------
        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> System.exit(0));
        add(btnSair, BorderLayout.SOUTH);

        setVisible(true);
    }
}
