package view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private static MenuPrincipal instance;

    public MenuPrincipal() {
        instance = this;
        setTitle("Sistema Escolar");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add Menu Panel (the initial buttons)
        mainPanel.add(criarMenuPanel(), "Menu");

        add(mainPanel);
        setVisible(true);
    }

    public static MenuPrincipal getInstance() {
        return instance;
    }

    private JPanel criarMenuPanel() {
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Menu Principal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(173, 216, 230));
        menuPanel.add(lblTitulo);

        JButton btnAluno = new JButton("Cadastrar Aluno");
        btnAluno.addActionListener(e -> {
            mostrarTelaCadastroAluno();
        });

        JButton btnTurma = new JButton("Cadastrar Turma");
        btnTurma.addActionListener(e -> {
            mostrarTelaCadastroTurma();
        });

        JButton btnProfessor = new JButton("Selecionar Professor (Fluxo Principal)");
        btnProfessor.addActionListener(e -> {
            mostrarTelaProfessor();
        });

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> System.exit(0));

        menuPanel.add(btnAluno);
        menuPanel.add(btnTurma);
        menuPanel.add(btnProfessor);
        menuPanel.add(btnSair);

        return menuPanel;
    }

    public void mostrarMenu() {
        cardLayout.show(mainPanel, "Menu");
    }

    public void mostrarTelaProfessor() {
        TelaProfessor panel = new TelaProfessor(); // Now a JPanel
        mainPanel.add(panel, "TelaProfessor");
        cardLayout.show(mainPanel, "TelaProfessor");
    }

    public void mostrarTelaCadastroAluno() {
        TelaAluno panel = new TelaAluno();
        mainPanel.add(panel, "TelaAluno");
        cardLayout.show(mainPanel, "TelaAluno");
    }

    public void mostrarTelaCadastroTurma() {
        TelaTurma panel = new TelaTurma();
        mainPanel.add(panel, "TelaTurma");
        cardLayout.show(mainPanel, "TelaTurma");
    }

    public void mostrarTelaTurmas(java.util.List<model.Professor> professores) {
        TelaListagemTurma panel = new TelaListagemTurma(professores); // Now a JPanel
        mainPanel.add(panel, "TelaListagemTurma");
        cardLayout.show(mainPanel, "TelaListagemTurma");
    }

    public void mostrarTelaAlunos(model.Turma turma) {
        TelaListagemAluno panel = new TelaListagemAluno(turma); // Now a JPanel
        mainPanel.add(panel, "TelaListagemAluno");
        cardLayout.show(mainPanel, "TelaListagemAluno");
    }

    public void mostrarTelaNotas(model.Turma turma, model.Aluno aluno) {
        TelaCadastroNota panel = new TelaCadastroNota(turma, aluno); // Now a JPanel
        mainPanel.add(panel, "TelaCadastroNota");
        cardLayout.show(mainPanel, "TelaCadastroNota");
    }
}
